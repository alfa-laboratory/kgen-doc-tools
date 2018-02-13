package ru.alfabank.ecomm.dcreator.parser.line

import ru.alfabank.ecomm.dcreator.nodes.BoldNode
import ru.alfabank.ecomm.dcreator.nodes.EmptyNode
import ru.alfabank.ecomm.dcreator.nodes.Node
import ru.alfabank.ecomm.dcreator.parser.MarkdownParser
import ru.alfabank.ecomm.dcreator.parser.ParseLineResult

abstract class BoldLineParser(symbols: String) : BoundsLineParser(symbols) {
    override fun parse(line: String): ParseLineResult? {
        val (node, findUnboundParser) = parseInstance.lineParser.parseForLineResult(line)

        return if (node == EmptyNode)
            ParseLineResult(EmptyNode, findUnboundParser)
        else
            ParseLineResult(BoldNode(node), findUnboundParser)
    }

    override fun combine(node: Node): Node? = BoldNode(node)
}

class BoldLineParserStar(override val parseInstance: MarkdownParser) : BoldLineParser("**") {
    override val parserId: String = "BoldLineParserStar"
}

class BoldLineParserUnderscore(override val parseInstance: MarkdownParser) : BoldLineParser("__") {
    override val parserId: String = "BoldLineParserUnderscore"
}