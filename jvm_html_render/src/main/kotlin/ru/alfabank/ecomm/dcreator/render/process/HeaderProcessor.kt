package ru.alfabank.ecomm.dcreator.render.process

import ru.alfabank.ecomm.dcreator.nodes.*
import ru.alfabank.ecomm.dcreator.render.DocumentGenerator

data class HeaderLinks(
    override val nodes: List<BlockNode>
) : Node by NodeIdGen(), NestedNodeList<BlockNode>, BlockNode

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
    override suspend fun process(
        node: Node,
        serviceNodes: List<ServiceNode>,
        relativePath: DocumentGenerator.RelativePagePath
    ): ProcessResult {
        val childNodes = when (node) {
            is NestedNodeList<*> -> node.nodes
            is NestedNode -> listOf(node.node)
            else -> emptyList()
        }

        val headerNodes = childNodes.filterIsInstance(HeaderBlockNode::class.java)
        val (headerLinks, anchors) = processHeaders(headerNodes)

        listOf(node).fixRelativeLinkPaths(relativePath)

        val result = mutableMapOf(
            "data" to node,
            "headers" to HeaderLinks(headerLinks)
        )
        serviceNodes.findServiceNode<TitleServiceNode>()
            .let { result += "title" to TitleServiceNode(it?.title ?: DEFAULT_TITLE) }
        serviceNodes.findServiceNode<VersionServiceNode>()
            ?.let { result += "version" to it }
        serviceNodes.findServiceNode<BackUrlServiceNode>()
            ?.let { result += "backUrl" to it }
        serviceNodes.findServiceNode<LastUpdateServiceNode>()
            ?.let { result += "lastUpdate" to it }

        return ProcessResult(relativePath, result, anchors, serviceNodes)
    }

    private fun processHeaders(headerNodes: List<HeaderBlockNode>): Pair<List<BlockNode>, Map<String, Node>> {
        return headerNodes.fold(mutableListOf<HeaderLink>() to mutableMapOf<String, Node>()) { (acc, replaceNodes), node ->
            val lastHeaderLink = acc.lastOrNull()

            val headerLink = node.toHeaderLink()

            val newReplaceNode = node.nodeId to HeaderAnchor(node.node, node.level, headerLink.linkId)
            replaceNodes += newReplaceNode

            if (lastHeaderLink == null) {
                acc += headerLink
            } else {
                when {
                    node.level <= lastHeaderLink.level -> {
                        acc += headerLink
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

    private fun HeaderBlockNode.toHeaderLink(): HeaderLink {
        var selector: String = when (node) {
            is TextNode -> (node as TextNode).text.trim().toPreparedName()
            else -> node.toString().toPreparedName()
        }

        if (selector.isEmpty()) {
            selector = nodeId
        }

        /**
         * convert it to valid querySelector (first digit symbol is not allowed)
         */
        if (selector.first().isDigit()) {
            selector = "q$selector"
        }

        return HeaderLink(this.node, this.level, selector, mutableListOf())
    }
}