package ru.alfabank.ecomm.dcreator.render.process

import ru.alfabank.ecomm.dcreator.nodes.*

data class TabNodes(
    val tabs: List<TabNode>
) : Node by NodeIdGen()

data class TabNode(
    val name: String,
    val link: String,
    val selected: Boolean
) : Node by NodeIdGen()

class TabsProcessor(
    private val generateData: (String) -> List<ProcessingData>,
    private val generateLink: (String) -> String
) : NodeProcessor {
    override fun process(node: Node, serviceNodes: List<ServiceNode>): List<ProcessResult> {
        val fileToTabNodes: List<Pair<String, TabNode>> = serviceNodes.filterIsInstance<IncludeServiceNode>()
            .mapIndexed { index, (name, file) ->
                val relativeLink = if (index == 0) SELF_LINK else generateLink("${file}_link$index")

                file to TabNode(name, relativeLink, false)
            }

        return fileToTabNodes.map { (file, currentTab) ->
            val processResults = generateData(file)
            if (processResults.size != 1)
                throw RuntimeException("multiple nested files not supported for this layout")

            val (data, localServiceNodes, _) = processResults.first()

            val result = mutableMapOf(
                "data" to HTMLNode(data),
                "tabs" to prepareTabs(currentTab, fileToTabNodes)
            )
            findTitle(localServiceNodes)?.let { result += "title" to it }

            ProcessResult(
                relativeLink = if (currentTab.link == SELF_LINK) "" else currentTab.link,
                result = result,
                replaceNodes = emptyMap(),
                serviceNodes = localServiceNodes
            )
        }
    }

    private fun prepareTabs(currentTab: TabNode, fileToTabNodes: List<Pair<String, TabNode>>): Node {
        return fileToTabNodes.map { (_, tab) ->
            if (currentTab.nodeId == tab.nodeId)
                tab.copy(selected = true)
            else
                tab
        }.let { TabNodes(it) }
    }

    companion object {
        private const val SELF_LINK = "/"
    }

}