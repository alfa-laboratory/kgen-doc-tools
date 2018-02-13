package ru.alfabank.ecomm.dcreator.render.process

import ru.alfabank.ecomm.dcreator.nodes.Node

data class ProcessResult(val result: Map<String, Node>, val replaceNodes: Map<String, Node>)

interface NodeProcessor {
    fun process(node: Node): ProcessResult
}