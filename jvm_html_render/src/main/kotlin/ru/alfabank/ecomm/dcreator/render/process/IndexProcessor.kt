package ru.alfabank.ecomm.dcreator.render.process

import kotlinx.coroutines.experimental.async
import kotlinx.coroutines.experimental.newFixedThreadPoolContext
import ru.alfabank.ecomm.dcreator.nodes.*
import ru.alfabank.ecomm.dcreator.render.DocumentGenerator
import java.io.File

data class IncludeFilesInfo(
    val files: List<IncludeFileInfo>
) : Node by NodeIdGen(), BlockNode

data class IncludeFileInfo(
    val link: String,
    val name: String,
    val title: String?
) : Node by NodeIdGen(), BlockNode

class IndexProcessor(
    private val generateData: suspend (String, List<ServiceNode>, DocumentGenerator.RelativePagePath) -> List<FileProcessData>
) : NodeProcessor {
    private val filesProcessingContext = newFixedThreadPoolContext(
        Runtime.getRuntime().availableProcessors(),
        "FileProcessPoll"
    )

    override suspend fun process(
        node: Node,
        serviceNodes: List<ServiceNode>,
        relativePath: DocumentGenerator.RelativePagePath
    ): ProcessResult {
        val includeFiles: List<Pair<IncludeServiceNode, DocumentGenerator.RelativePagePath>> =
            serviceNodes.filterIsInstance<IncludeServiceNode>()
                .map { serviceNode ->
                    val fileName = File(serviceNode.file).nameWithoutExtension

                    val subFileRelative =
                        relativePath.subPath(File(fileName.toPreparedName() + ".${DocumentGenerator.HTML_EXTENSION}"))

                    Pair(serviceNode, subFileRelative)
                }

        val version = serviceNodes.findServiceNode<VersionServiceNode>()
        val backUrl = BackUrlServiceNode("../" + relativePath.toLink(relativePath))
        val title = serviceNodes.findServiceNode<TitleServiceNode>()
        val lastUpdate = serviceNodes.findServiceNode<LastUpdateServiceNode>()

        val nodes = listOfNotNull(
            version,
            backUrl,
            title,
            lastUpdate
        )

        val subFilesResults: Map<IncludeServiceNode, FileProcessData> = includeFiles.map { value ->
            value to async(filesProcessingContext) { generateData(value.first.file, nodes, value.second) }
        }.map { (value, future) ->
            val processResults: List<FileProcessData> = future.await()

            if (processResults.size > 1)
                throw RuntimeException("multiple nested files not supported for this layout")
            else if (processResults.isEmpty())
                throw RuntimeException("processing result is empty for file '${value.first.file}'")

            value.first to processResults.first().copy(relativePath = value.second)
        }.toMap()

        val result = mutableMapOf(
            "data" to node,
            "relativePath" to SimpleServiceNode(relativePath.toRelativePath())
        )
        version?.let { result += "version" to it }
        title?.let { result += "title" to it }
        lastUpdate?.let { result += "lastUpdate" to it }
        result += "files" to IncludeFilesInfo(subFilesResults.map { (serviceNode, result) ->
            IncludeFileInfo(
                link = result.relativePath.toLink(relativePath),
                name = serviceNode.name,
                title = serviceNode.title
            )
        })

        listOf(node).fixRelativeLinkPaths(relativePath)

        return ProcessResult(
            relativePath = relativePath,
            result = result,
            replaceNodes = emptyMap(),
            serviceNodes = serviceNodes,
            childs = subFilesResults.values.toList()
        )
    }
}




