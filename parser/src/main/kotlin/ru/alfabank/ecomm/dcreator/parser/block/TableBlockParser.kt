package ru.alfabank.ecomm.dcreator.parser.block

import ru.alfabank.ecomm.dcreator.nodes.*
import ru.alfabank.ecomm.dcreator.parser.MarkdownParser

class TableBlockParser(override val parseInstance: MarkdownParser) : BlockParser {
    override fun isLinesSuitable(lines: List<String>): BlockSuiteResult {
        if (lines.isEmpty())
            return BlockSuiteResult(false)

        val suitedResult = lines.all { line ->
            val index = line.indexOf('|')
            index >= 0 && index < line.lastIndex
        }

        return BlockSuiteResult(suitedResult)
    }

    private data class Rows(
            val content: List<String>,
            val header: String? = null,
            val footer: String? = null,
            val modifiers: String? = null
    )

    override fun parseLines(lines: List<String>): List<BlockNode> {
        if (lines.isEmpty())
            return emptyList()

        val headerLine = lines.first()

        val rows = if (lines.size == 1) {
            Rows(lines)
        } else {
            val secondLine = lines[1]

            val (otherContent, header, modifierLine) = if (isModifierColumn(secondLine)) {
                Triple(lines.drop(2), headerLine, secondLine)
            } else {
                Triple(lines, null, null)
            }

            val (content, footerLine) = if (otherContent.size >= 2
                    && isModifierColumn(otherContent[otherContent.lastIndex - 1])) {
                otherContent.dropLast(2) to otherContent.last()
            } else {
                otherContent to null
            }

            Rows(content, header = header, footer = footerLine, modifiers = modifierLine)
        }

        return listOf(parseTableLines(rows))
    }

    private fun isModifierColumn(column: String): Boolean {
        val columnMatcher = COLUMN_FIND_PATTERN.matcher(column)

        if (columnMatcher.find()) {
            val group = columnMatcher.group(COLUMN_GROUP)

            if (group != null) {
                val directionMatcher = DIRECTION_PATTERN.matcher(group)

                return directionMatcher.find()
            }
        }

        return false
    }

    private fun parseTableLines(rows: Rows): TableBlockNode {
        val (content, header, footer, modifiers) = rows

        val (headerColumns, footerColumns) = listOf(header, footer)
                .map { it?.parserRow() }

        val contentColumns = content.map { it.parserRow() }

        val modifiersParsed = modifiers
                ?.let { parseTableRow(it) }
                ?.mapNotNull { it.parseModifier() }

        return TableBlockNode(
                content = contentColumns,
                header = headerColumns,
                footer = footerColumns,
                modifier = modifiersParsed
        )
    }

    private fun String.parseModifier(): ColumnModifier? {
        val directionMatcher = DIRECTION_PATTERN.matcher(this)

        return if (directionMatcher.find()) {
            val leftGroup = directionMatcher.group(LEFT_GROUP)
            val rightGroup = directionMatcher.group(RIGHT_GROUP)

            when {
                leftGroup != null && rightGroup != null -> ColumnAlignModifier(Direction.Center)
                leftGroup != null -> ColumnAlignModifier(Direction.Left)
                rightGroup != null -> ColumnAlignModifier(Direction.Right)
                else -> EmptyColumnModifier
            }
        } else {
            null
        }
    }

    private fun String.parserRow(): List<Node> = this
            .let { parseTableRow(it) }
            .map { parseInstance.lineParser.parse(it) }

    private fun parseTableRow(line: String): List<String> {
        val columnMatcher = COLUMN_FIND_PATTERN.matcher(line)

        val lines = mutableListOf<String>()
        while (columnMatcher.find()) {
            lines += columnMatcher.group(COLUMN_GROUP)
        }

        return lines
    }

    override val parserId: String = "TableBlockParser"

    companion object {
        private const val SEPARATOR = "\\|"
        private val SPACE_PATTERN = """
            [\s\t]
        """.trimIndent()

        private const val COLUMN_GROUP = "column"
        private const val LEFT_GROUP = "left"
        private const val RIGHT_GROUP = "right"

        private val DIRECTION_PATTERN = """
            ((?<$LEFT_GROUP>:)?[-]+(?<$RIGHT_GROUP>:)?)
        """.trimIndent().toPattern()

        private val END_PATTERN = """
            (($SPACE_PATTERN+$SEPARATOR)|($SPACE_PATTERN*$SEPARATOR?$SPACE_PATTERN*$))
            """.trimIndent()

        private val START_PATTERN = """
            ((^$SPACE_PATTERN*$SEPARATOR?$SPACE_PATTERN*)|($SPACE_PATTERN+))
        """.trimIndent()

        private val COLUMN_PATTERN = """
            (?<$COLUMN_GROUP>[^\s\t][^$SEPARATOR]*)
        """.trimIndent()

        private val COLUMN_FIND_PATTERN = """
            $START_PATTERN$COLUMN_PATTERN$END_PATTERN
        """.trimIndent().toPattern()
    }
}