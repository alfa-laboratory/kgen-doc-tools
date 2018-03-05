package ru.alfabank.ecomm.dcreator.parser.block

import ru.alfabank.ecomm.dcreator.nodes.BlockNode
import ru.alfabank.ecomm.dcreator.nodes.HeaderBlockNode
import ru.alfabank.ecomm.dcreator.nodes.Level
import ru.alfabank.ecomm.dcreator.parser.MarkdownParser
import ru.alfabank.ecomm.dcreator.parser.common.toPattern

@Suppress("LeakingThis")
open class HeaderBlockParser(override val parseInstance: MarkdownParser) : BlockParser {
    override val parserId: String = "HeaderBlockParser"

    override fun isLinesSuitable(lines: List<String>): BlockSuiteResult {
        val result = when {
            lines.size == 1 -> HEADER_SUITE_PREDICATE.test(lines.first())
            lines.size == 2 -> linesIsHeader(lines.last())
            else -> false
        }

        return BlockSuiteResult(result)
    }

    override fun parseLines(lines: List<String>): List<BlockNode> = when {
        lines.size == 1 -> parseSingleHeaderLine(lines.first())
        lines.size == 2 -> {
            val (firstLine, secondLine) = lines
            parseMultiLineHeader(firstLine, secondLine)
        }
        else -> emptyList()
    }

    private fun parseMultiLineHeader(firstLine: String, secondLine: String): List<BlockNode> {
        val headerNode = when (secondLine.trimStart().first()) {
            HEADER_H1_SYMBOL -> HeaderBlockNode(Level.ONE, parseInstance.lineParser.parseForLineResult(firstLine).node)
            else -> HeaderBlockNode(Level.TWO, parseInstance.lineParser.parseForLineResult(firstLine).node)
        }

        return listOf(headerNode)
    }

    private fun parseSingleHeaderLine(line: String): List<BlockNode> {
        val headerLine = line.trimStart()

        val headerText = headerLine.trimStart(HEADER_SYMBOL)

        val headerLevel = when (headerLine.length - headerText.length) {
            1 -> Level.ONE
            2 -> Level.TWO
            3 -> Level.THREE
            4 -> Level.FOUR
            else -> Level.FIVE
        }

        return listOf(
                HeaderBlockNode(headerLevel, parseInstance.lineParser.parseForLineResult(headerText).node)
        )
    }

    private fun linesIsHeader(lastLine: String): Boolean = lastLine.isNotEmpty() && lastLine
            .trim()
            .all { it == HEADER_H2_SYMBOL || it == HEADER_H1_SYMBOL }

    companion object {
        private const val HEADER_SYMBOL: Char = '#'
        private const val HEADER_H2_SYMBOL: Char = '='
        private const val HEADER_H1_SYMBOL: Char = '-'

        private val SPACE_PATTERN = """
            [\s\t]
        """.trimIndent()

        private val HEADER_SUITE_PREDICATE = """
            ^$SPACE_PATTERN*$HEADER_SYMBOL+$SPACE_PATTERN
        """.trimIndent().toPattern().asPredicate()
    }
}