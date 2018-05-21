package ru.alfabank.ecomm.dcreator.render.process

import ru.alfabank.ecomm.dcreator.nodes.*
import ru.alfabank.ecomm.dcreator.render.DocumentGenerator
import kotlin.text.RegexOption.IGNORE_CASE

data class TabNodes(
    val tabs: List<TabNode>
) : Node by NodeIdGen()

data class TabNode(
    val name: String,
    val link: String,
    val selected: Boolean
) : Node by NodeIdGen()

class TabsProcessor(
    private val generateData: (String) -> List<ProcessingData>
) : NodeProcessor {
    override fun process(
        node: Node,
        serviceNodes: List<ServiceNode>,
        relativePath: DocumentGenerator.RelativePath,
        embedded: Boolean
    ): List<ProcessResult> {
        val fileToTabNodes: List<Triple<String, DocumentGenerator.RelativePath, TabNode>> =
            serviceNodes.filterIsInstance<IncludeServiceNode>()
                .mapIndexed { index, (name, file) ->
                    val tabRelativeLink = if (index == 0)
                        relativePath
                    else
                        relativePath.subPath(name.toPreparedName())

                    Triple(file, tabRelativeLink, TabNode(name, tabRelativeLink.toLink(), false))
                }

        return fileToTabNodes.map { (file, relativePath, currentTab) ->
            val processResults: List<ProcessingData> = generateData(file)
            if (processResults.size != 1)
                throw RuntimeException("multiple nested files not supported for this layout")

            val (data, localServiceNodes, _) = processResults.first()

            val preparedTabs: List<TabNode> = prepareTabs(currentTab, fileToTabNodes)
            val (selected, otherTabs) = preparedTabs.partition { it.selected }

            val result = mutableMapOf(
                "data" to HTMLNode(data),
                "selectedTab" to selected.first(),
                "otherTabs" to TabNodes(otherTabs),
                "tabs" to TabNodes(preparedTabs)
            )
            findTitle(localServiceNodes)?.let { result += "title" to TitleServiceNode(it.title) }

            ProcessResult(
                relativePath = relativePath.toRelative(),
                result = result,
                replaceNodes = emptyMap(),
                serviceNodes = localServiceNodes
            )
        }
    }

    private fun String.toPreparedName(): String =
        this.replace(Regex("[^a-zA-Zа-яА-Я0-9]+", IGNORE_CASE), "_")
            .toLowerCase()

    private fun prepareTabs(
        currentTab: TabNode,
        fileToTabNodes: List<Triple<String, DocumentGenerator.RelativePath, TabNode>>
    ): List<TabNode> = fileToTabNodes.map { (_, _, tab) ->
        if (currentTab.nodeId == tab.nodeId)
            tab.copy(selected = true)
        else
            tab
    }
}


