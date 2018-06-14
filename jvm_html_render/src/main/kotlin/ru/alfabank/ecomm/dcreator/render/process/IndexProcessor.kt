package ru.alfabank.ecomm.dcreator.render.process

import kotlinx.coroutines.experimental.async
import kotlinx.coroutines.experimental.newFixedThreadPoolContext
import ru.alfabank.ecomm.dcreator.nodes.*
import ru.alfabank.ecomm.dcreator.render.DocumentGenerator
import kotlin.text.RegexOption.IGNORE_CASE

data class IncludeFilesInfo(
    val files: List<IncludeFileInfo>
) : Node by NodeIdGen(), BlockNode

data class IncludeFileInfo(
    val link: String,
    val name: String,
    val title: String?
) : Node by NodeIdGen(), BlockNode

class IndexProcessor(
    private val generateData: suspend (String, List<ServiceNode>) -> List<FileProcessData>
) : NodeProcessor {
    private val filesProcessingContext = newFixedThreadPoolContext(
        Runtime.getRuntime().availableProcessors(),
        "FileProcessPoll"
    )

    override suspend fun process(
        node: Node,
        serviceNodes: List<ServiceNode>,
        relativePath: DocumentGenerator.RelativePath
    ): ProcessResult {
        val includeFiles: List<Pair<IncludeServiceNode, DocumentGenerator.RelativePath>> =
            serviceNodes.filterIsInstance<IncludeServiceNode>()
                .map { serviceNode ->
                    val subFileRelative = relativePath.subPath(serviceNode.name.toPreparedName())

                    Pair(serviceNode, subFileRelative)
                }

        val version = serviceNodes.findServiceNode<VersionServiceNode>()
        val backUrl = BackUrlServiceNode(relativePath.toLink())
        val title = serviceNodes.findServiceNode<TitleServiceNode>()
        val lastUpdate = serviceNodes.findServiceNode<LastUpdateServiceNode>()

        val params = listOfNotNull(
            version,
            backUrl,
            title,
            lastUpdate
        )

        val subFilesResults = includeFiles.map { value ->
            value to async(filesProcessingContext) { generateData(value.first.file, params) }
        }.map { (value, future) ->
            val processResults: List<FileProcessData> = future.await()

            if (processResults.size > 1)
                throw RuntimeException("multiple nested files not supported for this layout")
            else if (processResults.isEmpty())
                throw RuntimeException("processing result is empty for file '${value.first.file}'")

            value.first to processResults.first().copy(relativePath = value.second)
        }.toMap()

        val result = mutableMapOf(
            "data" to node
        )
        version?.let { result += "version" to it }
        title?.let { result += "title" to it }
        lastUpdate?.let { result += "lastUpdate" to it }
        result += "files" to IncludeFilesInfo(subFilesResults.map { (serviceNode, result) ->
            IncludeFileInfo(
                link = result.relativePath.toLink(),
                name = serviceNode.name,
                title = serviceNode.title
            )
        })

        return ProcessResult(
            relativePath = relativePath,
            result = result,
            replaceNodes = emptyMap(),
            serviceNodes = serviceNodes,
            childs = subFilesResults.values.toList()
        )
    }

    private fun String.toPreparedName(): String = this
        .replace(Regex("[^a-zA-Zа-яА-Я0-9]+", IGNORE_CASE), "_")
        .toLowerCase()
}




