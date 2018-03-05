package ru.alfabank.ecomm.dcreator.parser.block

import ru.alfabank.ecomm.dcreator.nodes.BlockNode
import ru.alfabank.ecomm.dcreator.nodes.BlockquotesBlockNode
import ru.alfabank.ecomm.dcreator.nodes.EmptyBlockNode
import ru.alfabank.ecomm.dcreator.parser.MarkdownParser
import ru.alfabank.ecomm.dcreator.parser.common.toPattern

class BlockquotesParser(override val parseInstance: MarkdownParser) : BlockParser {
    override fun isLinesSuitable(lines: List<String>): BlockSuiteResult {
        if (lines.isEmpty())
            return BlockSuiteResult(false)

        val suitableResult = lines.all { line ->
            QUOTE_START_PREDICATE.test(line)
        }

        return BlockSuiteResult(suitableResult)
    }

    override fun parseLines(lines: List<String>): List<BlockNode> {
        val content = lines.map { line ->
            val matcher = QUITE_LINE_PATTERN.matcher(line)
            if (matcher.find()) {
                matcher.group(TEXT_GROUP)!!
            } else {
                throw RuntimeException("unexpected behaviour")
            }
        }

        val parseNode = parseInstance.parse(content.asSequence())

        if (parseNode == EmptyBlockNode)
            return emptyList()

        return listOf(BlockquotesBlockNode(parseNode))
    }

    override val parserId: String = "BlockquotesParser"

    companion object {
        private const val QUOTE_SYMBOL = ">"
        private const val TEXT_GROUP = "text"

        private val SPACE_PATTERN = """
            [\s\w]*
        """.trimIndent()

        private val QUOTE_START_PREDICATE = """
            ^$SPACE_PATTERN$QUOTE_SYMBOL
        """.trimIndent().toPattern().asPredicate()

        private val QUITE_LINE_PATTERN = """
             ^$SPACE_PATTERN$QUOTE_SYMBOL(?<$TEXT_GROUP>.*)$
        """.trimIndent().toPattern()
    }
}