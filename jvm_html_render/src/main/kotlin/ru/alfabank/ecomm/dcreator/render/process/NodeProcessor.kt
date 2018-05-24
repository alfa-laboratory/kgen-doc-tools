package ru.alfabank.ecomm.dcreator.render.process

import ru.alfabank.ecomm.dcreator.nodes.Node
import ru.alfabank.ecomm.dcreator.nodes.ServiceNode
import ru.alfabank.ecomm.dcreator.nodes.TitleServiceNode
import ru.alfabank.ecomm.dcreator.render.DocumentGenerator

const val DEFAULT_TITLE = "default title"

data class ProcessResult(
    val relativePath: String,
    val result: Map<String, Node>,
    val replaceNodes: Map<String, Node>,
    val serviceNodes: List<ServiceNode>
)

data class ProcessingData(
    val data: String,
    val serviceNodes: List<ServiceNode>,
    val relativePath: String
)

fun findTitle(serviceNodes: List<ServiceNode>): TitleServiceNode? = serviceNodes
    .asSequence()
    .filterIsInstance<TitleServiceNode>()
    .firstOrNull()

interface NodeProcessor {
    fun process(
        node: Node,
        serviceNodes: List<ServiceNode>,
        relativePath: DocumentGenerator.RelativePath,
        embedded: Boolean
    ): List<ProcessResult>
}