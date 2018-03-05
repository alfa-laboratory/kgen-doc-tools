package ru.alfabank.ecomm.dcreator.parser.line

import ru.alfabank.ecomm.dcreator.nodes.EmptyNode
import ru.alfabank.ecomm.dcreator.nodes.Node
import ru.alfabank.ecomm.dcreator.nodes.UnderlineNode
import ru.alfabank.ecomm.dcreator.parser.MarkdownParser
import ru.alfabank.ecomm.dcreator.parser.ParseLineResult

class UnderlineLineParser(override val parseInstance: MarkdownParser) : BoundsLineParser("++") {
    override val parserId: String = "UnderlineLineParser"

    override fun parse(line: String): ParseLineResult? {
        val (node, findUnboundResult) = parseInstance.lineParser.parseForLineResult(line)

        return if (node == EmptyNode)
            ParseLineResult(EmptyNode, findUnboundResult)
        else
            ParseLineResult(UnderlineNode(node), findUnboundResult)
    }

    override fun combine(node: Node): Node? = UnderlineNode(node)
}