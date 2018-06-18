package ru.alfabank.ecomm.dcreator.render.process

import ru.alfabank.ecomm.dcreator.nodes.*
import ru.alfabank.ecomm.dcreator.render.DocumentGenerator
import ru.alfabank.ecomm.dcreator.render.DocumentGenerator.RelativePagePath

const val DEFAULT_TITLE = "default title"

data class ProcessResult(
    val relativePath: RelativePagePath,
    val result: Map<String, Node>,
    val replaceNodes: Map<String, Node>,
    val serviceNodes: List<ServiceNode>,
    val childs: List<FileProcessData> = emptyList()
)

data class FileProcessData(
    val data: String,
    val serviceNodes: List<ServiceNode>,
    val relativePath: RelativePagePath
)

inline fun <reified T : ServiceNode> List<ServiceNode>.findServiceNode(): T? = this
    .asSequence()
    .filterIsInstance<T>()
    .firstOrNull()

interface NodeProcessor {
    suspend fun process(
        node: Node,
        serviceNodes: List<ServiceNode>,
        relativePath: DocumentGenerator.RelativePagePath
    ): ProcessResult
}

private fun String?.isUrlRelative(): Boolean = this != null && this.indexOf("/") != 0

tailrec fun List<Node>.fixRelativeLinkPaths(relativePath: RelativePagePath): MutableMap<String, Node> =
    if (this.isNotEmpty()) {
        val first = this.first()
        val last = this.drop(1)

        when (first) {
            is ImageLinkNode -> {
                if (first.url.isUrlRelative()) {
                    val fixedLink = relativePath.fromStatic(first.url!!)
                        .toLink(relativePath)
                    first.url = fixedLink

                    last.fixRelativeLinkPaths(relativePath)
                } else {
                    last.fixRelativeLinkPaths(relativePath)
                }
            }
            is LinkNode -> {
                if (first.url.isUrlRelative()) {
                    val fixedLink = relativePath.fromStatic(first.url!!)
                        .toLink(relativePath)
                    first.url = fixedLink

                    (last + first.node).fixRelativeLinkPaths(relativePath)
                } else {
                    last.fixRelativeLinkPaths(relativePath)
                }
            }
            is NestedNodeList<*> -> (last + first.nodes).fixRelativeLinkPaths(relativePath)
            is NestedNode -> (last + first.node).fixRelativeLinkPaths(relativePath)
            else -> last.fixRelativeLinkPaths(relativePath)
        }
    } else {
        mutableMapOf()
    }
