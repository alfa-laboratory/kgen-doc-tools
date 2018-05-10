package ru.alfabank.ecomm.dcreator.parser.block

import ru.alfabank.ecomm.dcreator.common.toPattern
import ru.alfabank.ecomm.dcreator.nodes.BlockNode
import ru.alfabank.ecomm.dcreator.nodes.CodeBlockNode
import ru.alfabank.ecomm.dcreator.parser.MarkdownParser

open class CodeBlockParser(override val parseInstance: MarkdownParser) : BlockParser {
    override val parserId: String = "CodeBlockParser"

    override fun isLinesSuitable(lines: List<String>): BlockSuiteResult {
        if (lines.isEmpty())
            return BlockSuiteResult(false)

        val firstLine = lines.first()

        val matcher = CODE_START_PATTERN.matcher(firstLine)
        if (!matcher.find())
            return BlockSuiteResult(false)

        val indexOfEnd = lines.drop(1)
            .indexOfFirst { CODE_END_PREDICATE.test(it) }

        return BlockSuiteResult(indexOfEnd < 0 || indexOfEnd == lines.size - 2)
    }

    override suspend fun parseLines(lines: List<String>): List<BlockNode> {
        val firstLine = lines.first()

        val matcher = CODE_START_PATTERN.matcher(firstLine)
        if (!matcher.find() || lines.size == 1)
            return emptyList()

        val language = matcher.group(LANGUAGE_GROUP_NAME)!!

        val codeLines = lines.drop(1)
            .let {
                if (!CODE_END_PREDICATE.test(it.last()))
                    it
                else
                    it.dropLast(1)
            }

        return listOf(
            CodeBlockNode(language, codeLines.joinToString("\n"))
        )
    }

    companion object {
        private const val LANGUAGE_GROUP_NAME = "language"

        private val CODE_END_PREDICATE = """
            ^\s*?```\s*?$
        """.trimIndent().toPattern().asPredicate()

        private val CODE_START_PATTERN = """
            ^\s*?```(?<$LANGUAGE_GROUP_NAME>\w*)
        """.trimIndent().toPattern()
    }
}

