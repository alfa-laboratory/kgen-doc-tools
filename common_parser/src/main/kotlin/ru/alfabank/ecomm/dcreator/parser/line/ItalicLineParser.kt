package ru.alfabank.ecomm.dcreator.parser.line

import ru.alfabank.ecomm.dcreator.nodes.EmptyNode
import ru.alfabank.ecomm.dcreator.nodes.ItalicNode
import ru.alfabank.ecomm.dcreator.nodes.Node
import ru.alfabank.ecomm.dcreator.parser.MarkdownParser
import ru.alfabank.ecomm.dcreator.parser.ParseLineResult

abstract class ItalicLineParser(symbols: String) : BoundsLineParser(symbols) {
    override fun parse(line: String): ParseLineResult? {
        val (node, findUnboundResult) = parseInstance.lineParser.parseForLineResult(line)

        return if (node == EmptyNode)
            ParseLineResult(EmptyNode, findUnboundResult)
        else
            ParseLineResult(ItalicNode(node), findUnboundResult)
    }

    override fun combine(node: Node): Node? = ItalicNode(node)
}

class ItalicLineParserStar(override val parseInstance: MarkdownParser) : ItalicLineParser("*") {
    override val parserId: String = "ItalicLineParserStar"
}

class ItalicLineParserUnderscore(override val parseInstance: MarkdownParser) : ItalicLineParser("_") {
    override val parserId: String = "ItalicLineParserUnderscore"
}