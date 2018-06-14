package ru.alfabank.ecomm.dcreator.render.process

import ru.alfabank.ecomm.dcreator.nodes.Node
import ru.alfabank.ecomm.dcreator.nodes.ServiceNode
import ru.alfabank.ecomm.dcreator.render.DocumentGenerator
import ru.alfabank.ecomm.dcreator.render.DocumentGenerator.RelativePath

const val DEFAULT_TITLE = "default title"

data class ProcessResult(
    val relativePath: RelativePath,
    val result: Map<String, Node>,
    val replaceNodes: Map<String, Node>,
    val serviceNodes: List<ServiceNode>,
    val childs: List<FileProcessData> = emptyList()
)

data class FileProcessData(
    val data: String,
    val serviceNodes: List<ServiceNode>,
    val relativePath: RelativePath
)

inline fun <reified T : ServiceNode> List<ServiceNode>.findServiceNode(): T? = this
    .asSequence()
    .filterIsInstance<T>()
    .firstOrNull()

interface NodeProcessor {
    suspend fun process(
        node: Node,
        serviceNodes: List<ServiceNode>,
        relativePath: DocumentGenerator.RelativePath
    ): ProcessResult
}