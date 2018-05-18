package ru.alfabank.ecomm.dcreator.render.process

import ru.alfabank.ecomm.dcreator.nodes.Node
import ru.alfabank.ecomm.dcreator.nodes.ServiceNode
import ru.alfabank.ecomm.dcreator.nodes.TitleServiceNode

data class ProcessResult(
    val relativeLink: String,
    val result: Map<String, Node>,
    val replaceNodes: Map<String, Node>,
    val serviceNodes: List<ServiceNode>
)

data class ProcessingData(
    val data: String,
    val serviceNodes: List<ServiceNode>,
    val relativeFileName: String
)

fun findTitle(serviceNodes: List<ServiceNode>): TitleServiceNode? = serviceNodes
    .asSequence()
    .filterIsInstance<TitleServiceNode>()
    .firstOrNull()

interface NodeProcessor {
    fun process(node: Node, serviceNodes: List<ServiceNode>): List<ProcessResult>
}