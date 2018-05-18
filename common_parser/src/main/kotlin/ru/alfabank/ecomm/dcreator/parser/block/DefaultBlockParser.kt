package ru.alfabank.ecomm.dcreator.parser.block

import ru.alfabank.ecomm.dcreator.nodes.BlockNode
import ru.alfabank.ecomm.dcreator.nodes.EmptyBlockNode
import ru.alfabank.ecomm.dcreator.nodes.EmptyNode
import ru.alfabank.ecomm.dcreator.nodes.TextBlockNode
import ru.alfabank.ecomm.dcreator.parser.MarkdownParser
import ru.alfabank.ecomm.dcreator.parser.PartialParseResult
import ru.alfabank.ecomm.dcreator.parser.toParseResult

open class DefaultBlockParser(override val parseInstance: MarkdownParser) : BlockParser {
    override val parserId: String = "DefaultBlockParser"

    override fun isLinesSuitable(lines: List<String>): BlockSuiteResult = BlockSuiteResult(false)

    override suspend fun parseLines(lines: List<String>): PartialParseResult {
        if (lines.isEmpty())
            return listOf(EmptyBlockNode).toParseResult()

        val parsedLines = lines.map { line ->
            parseInstance.lineParser.parseForLineResult(line).node
        }.filter { it != EmptyNode }

        if (parsedLines.isEmpty()
            || (parsedLines.size == 1 && parsedLines.first() == EmptyNode)
        )
            return emptyList<BlockNode>().toParseResult()

        return listOf(TextBlockNode(parsedLines)).toParseResult()
    }

}