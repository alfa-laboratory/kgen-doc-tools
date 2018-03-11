package ru.alfabank.ecomm.dcreator.parser.line

import ru.alfabank.ecomm.dcreator.nodes.CodeSampleNode
import ru.alfabank.ecomm.dcreator.nodes.Node
import ru.alfabank.ecomm.dcreator.parser.MarkdownParser
import ru.alfabank.ecomm.dcreator.parser.ParseLineResult

class CodeLineSingleQuoteParser(override val parseInstance: MarkdownParser) : BoundsLineParser("`") {
    override val parserId: String = "CodeLineSingleQuoteParser"

    override fun parse(line: String): ParseLineResult? = ParseLineResult(CodeSampleNode(line))

    override fun combine(node: Node): Node? = null
}

class CodeLineDoubleQuoteParser(override val parseInstance: MarkdownParser) : BoundsLineParser("``") {
    override val parserId: String = "CodeLineDoubleQuoteParser"

    override fun parse(line: String): ParseLineResult? = ParseLineResult(CodeSampleNode(line))

    override fun combine(node: Node): Node? = null
}