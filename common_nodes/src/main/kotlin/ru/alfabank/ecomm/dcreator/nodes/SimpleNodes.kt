package ru.alfabank.ecomm.dcreator.nodes

import ru.alfabank.ecomm.dcreator.common.UUID

interface Node {
    var nodeId: String
}

class NodeIdGen : Node {
    override var nodeId: String = NOT_INITIALIZED_VALUE
        get() = if (field == NOT_INITIALIZED_VALUE) {
            field = _nodeId
            field
        } else
            field
        set(value) {
            field = value
        }

    private val _nodeId: String by lazy { UUID.randomUUID().print() }

    companion object {
        private const val NOT_INITIALIZED_VALUE = "-100500"
    }
}

interface NestedNode : Node {
    val node: Node
}

object EmptyNode : Node by NodeIdGen() {
    override fun toString(): String = "EmptyNode"
}

data class TextNode(val text: String) : Node by NodeIdGen() {
    companion object {
        fun from(text: String): Node = if (text.isEmpty())
            EmptyNode
        else
            TextNode(text)
    }
}

data class BoldNode(override val node: Node) : NestedNode, Node by NodeIdGen()

data class ItalicNode(override val node: Node) : NestedNode, Node by NodeIdGen()

data class StrikethroughNode(override val node: Node) : NestedNode, Node by NodeIdGen()

data class UnderlineNode(override val node: Node) : NestedNode, Node by NodeIdGen()

//code blocks
data class HTMLNode(val text: String) : Node by NodeIdGen()

data class CodeSampleNode(val text: String) : Node by NodeIdGen()

//links
sealed class DistributeNode(open var url: String? = null, open var title: String? = null)

data class LinkNode(
    override val node: Node,
    override var url: String? = null,
    override var title: String? = null
) : Node by NodeIdGen(), DistributeNode(url, title), NestedNode

data class ImageLinkNode(
    val text: String,
    override var url: String? = null,
    override var title: String? = null
) : Node by NodeIdGen(), DistributeNode(url, title)

//lists
interface ListNode : Node

data class OrderedListNode(val number: Int, override val node: Node) : ListNode, NestedNode, Node by NodeIdGen()

data class UnOrderedListNode(override val node: Node) : ListNode, NestedNode, Node by NodeIdGen()

