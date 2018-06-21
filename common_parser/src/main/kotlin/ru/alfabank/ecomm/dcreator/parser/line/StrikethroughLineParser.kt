package ru.alfabank.ecomm.dcreator.parser.line

import ru.alfabank.ecomm.dcreator.nodes.EmptyNode
import ru.alfabank.ecomm.dcreator.nodes.Node
import ru.alfabank.ecomm.dcreator.nodes.StrikethroughNode
import ru.alfabank.ecomm.dcreator.parser.MarkdownParser
import ru.alfabank.ecomm.dcreator.parser.ParseLineResult

class StrikethroughLineParser(override val parseInstance: MarkdownParser) : BoundsLineParser("~~") {
    override val parserId: String = "StrikethroughLineParser"

    override suspend fun parse(line: String): ParseLineResult? {
        val (node, findUnboundResult) = parseInstance.lineParser.parseForLineResult(line)

        return if (node == EmptyNode)
            ParseLineResult(EmptyNode, findUnboundResult)
        else
            ParseLineResult(StrikethroughNode(node), findUnboundResult)
    }

    override fun combine(node: Node): Node? = StrikethroughNode(node)
}