package ru.alfabank.ecomm.dcreator.nodes

interface BlockNode : Node

interface NestedNodeList<out T : Node> : Node {
    val nodes: List<T>
}

object EmptyBlockNode : BlockNode, Node by NodeIdGen() {
    override fun toString(): String = "EmptyBlockNode"
}

enum class Level { ONE, TWO, THREE, FOUR, FIVE }

data class HeaderBlockNode(val level: Level, override val node: Node) : BlockNode, NestedNode, Node by NodeIdGen()

data class TextBlockNode(override val nodes: List<Node>) : BlockNode, NestedNodeList<Node>, Node by NodeIdGen()

data class CodeBlockNode(val language: String, val text: String) : BlockNode, Node by NodeIdGen()

data class ListsBLockNode(override val nodes: List<ListNode>) : BlockNode, NestedNodeList<ListNode>, Node by NodeIdGen()

data class BlockquotesBlockNode(override val node: BlockNode) : BlockNode, NestedNode, Node by NodeIdGen()

//tables
typealias TableRow = List<Node>

interface ColumnModifier

object EmptyColumnModifier : ColumnModifier {
    override fun toString(): String = "EmptyColumnModifier"
}

enum class Direction { Left, Center, Right }

data class ColumnAlignModifier(val direction: Direction) : ColumnModifier

data class TableBlockNode(
        val content: List<TableRow>,
        val header: TableRow? = null,
        val footer: TableRow? = null,
        val modifier: List<ColumnModifier>? = null,
        override val nodes: List<Node> = (content.flatten() + (header ?: emptyList()) + (footer ?: emptyList()))
) : BlockNode, Node by NodeIdGen(), NestedNodeList<Node>