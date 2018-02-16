package ru.alfabank.ecomm.dcreator.render.process

import ru.alfabank.ecomm.dcreator.nodes.*

data class HeaderLink(
        override val node: Node,
        val level: Level,
        val linkId: String,
        val childs: MutableList<HeaderLink>
) : Node by NodeIdGen(), NestedNode, BlockNode

data class HeaderAnchor(
        override val node: Node,
        val level: Level,
        val linkId: String
) : Node by NodeIdGen(), NestedNode, BlockNode

class HeaderProcessor : NodeProcessor {
    override fun process(node: Node): ProcessResult {
        val childNodes = when (node) {
            is NestedNodeList<*> -> node.nodes
            is NestedNode -> listOf(node.node)
            else -> emptyList()
        }

        val headerNodes = childNodes
                .filter { it is HeaderBlockNode }
                .map { it as HeaderBlockNode }

        val (headerLinks, anchors) = processHeaders(headerNodes)

        val result = mapOf(
                "data" to node,
                "headers" to BlockLayout(headerLinks)
        )

        return ProcessResult(result, anchors)
    }

    private fun processHeaders(headerNodes: List<HeaderBlockNode>): Pair<List<BlockNode>, Map<String, Node>> {
        return headerNodes.fold(mutableListOf<HeaderLink>() to mutableMapOf<String, Node>()) { (acc, replaceNodes), node ->
            val lastHeaderLink = acc.lastOrNull()

            val newReplaceNode = node.nodeId to HeaderAnchor(node.node, node.level, node.nodeId.toSelector())
            replaceNodes += newReplaceNode

            if (lastHeaderLink == null) {
                acc += node.toHeaderLink()
            } else {
                when {
                    node.level <= lastHeaderLink.level -> {
                        acc += node.toHeaderLink()
                    }
                    else -> {
                        addHeaderLinkToChild(lastHeaderLink, node)
                    }
                }
            }

            acc to replaceNodes
        }
    }

    private tailrec fun addHeaderLinkToChild(lastHeaderLink: HeaderLink, node: HeaderBlockNode) {
        if (lastHeaderLink.childs.isEmpty()) {
            lastHeaderLink.childs += node.toHeaderLink()
            return
        }

        val lastChildHL = lastHeaderLink.childs.last()

        when {
            node.level <= lastChildHL.level -> {
                lastHeaderLink.childs += node.toHeaderLink()
            }
            else -> {
                addHeaderLinkToChild(lastChildHL, node)
            }
        }
    }

    private fun HeaderBlockNode.toHeaderLink() = HeaderLink(this.node, this.level, this.nodeId.toSelector(), mutableListOf())

    //make it valid quertySelector (first digit symbol is not allowed)
    private fun String.toSelector(): String = "q$this"
}