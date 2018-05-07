package ru.alfabank.ecomm.dcreator.parser.block

import ru.alfabank.ecomm.dcreator.common.Matcher
import ru.alfabank.ecomm.dcreator.common.toPattern
import ru.alfabank.ecomm.dcreator.nodes.*
import ru.alfabank.ecomm.dcreator.parser.MarkdownParser

private sealed class Level(
    open val spaces: Int
)

private class NumberedLevel(
    val number: Int,
    val text: String,
    override val spaces: Int
) : Level(spaces)

private class SymbolLevel(
    val text: String,
    override val spaces: Int
) : Level(spaces)

class ListBlockParser(override val parseInstance: MarkdownParser) : BlockParser {

    override fun isLinesSuitable(lines: List<String>): BlockSuiteResult {
        if (lines.isEmpty())
            return BlockSuiteResult(false)

        val firstLine = lines.first()
        val firstLineMatcher = START_LEVEL_PATTERN.matcher(firstLine)

        val lastLineNotBlank = lines.last().isNotBlank()

        if (firstLineMatcher.find() && lastLineNotBlank) {
            return BlockSuiteResult(true)
        }

        return BlockSuiteResult(false)
    }

    data class LineParseResult(val spaces: Int, val number: Int?, val symbol: Boolean, val text: String)

    override fun parseLines(lines: List<String>): List<BlockNode> {
        val lineMatch = START_LEVEL_PATTERN.matcher(lines.first())
        if (lineMatch.find()) {
            val (sumSpaces, number, symbolExists, text) = parseMatch(lineMatch)

            when {
                number != null && !symbolExists ->
                    return parseOtherLines(NumberedLevel(number, text, sumSpaces), lines.drop(1))
                number == null && symbolExists ->
                    return parseOtherLines(SymbolLevel(text, sumSpaces), lines.drop(1))
            }
        }

        throw RuntimeException("not expect behavior")
    }

    private fun parseMatch(match: Matcher): LineParseResult {
        val spacesSize = match.group(SPACES_GROUP)?.length ?: 0
        val tabsSize = (match.group(TAB_GROUP)?.length ?: 0) * 2

        val sumSpaces = spacesSize + tabsSize

        val number = match.group(DIGIT_GROUP)?.toIntOrNull()
        val symbol = match.group(SYMBOL_ORDER_GROUP)

        val text = match.group(TEXT_GROUP) ?: ""

        return LineParseResult(sumSpaces, number, symbol != null, text)
    }

    private tailrec fun parseOtherLines(
        level: Level,
        lines: List<String>,
        prevNodes: List<BlockNode> = emptyList()
    ): List<BlockNode> {
        val buffer = mutableListOf<String>()

        for ((index, line) in lines.withIndex()) {
            val matcher = START_LEVEL_PATTERN.matcher(line)
            if (matcher.find()) {
                val (sumSpaces, number, symbolExists, text) = parseMatch(matcher)
                val spacesDiff = sumSpaces - level.spaces

                val sameLevel = spacesDiff <= 1

                if (sameLevel) {
                    val newPrevNodes = prevNodes.merge(parseLevel(level, buffer))

                    when {
                        number != null && !symbolExists ->
                            return parseOtherLines(
                                NumberedLevel(number, text, level.spaces),
                                lines.drop(index + 1),
                                newPrevNodes
                            )
                        number == null && symbolExists ->
                            return parseOtherLines(SymbolLevel(text, level.spaces), lines.drop(index + 1), newPrevNodes)
                    }
                }

                if (spacesDiff < 0)
                    buffer.add(line.trimStart())
                else {
                    val lastLine = if (line.length > 2 && line.take(2) == "\\s\\s")
                        line.drop(2)
                    else if (line.length > 1 && line.first() == '\t')
                        line.drop(1)
                    else
                        line

                    buffer.add(lastLine)
                }
            } else {
                buffer.add(line.trimStart())
            }
        }

        return prevNodes.merge(parseLevel(level, buffer))
    }

    private fun List<BlockNode>.merge(node: ListNode): List<BlockNode> {
        val prevNodes = if (this.size > 1) this.dropLast(1) else emptyList()
        val lastNode = this.lastOrNull()

        val nodes = when (lastNode) {
            null -> listOf(ListsBLockNode(listOf(node)))
            is ListsBLockNode -> listOf(ListsBLockNode(lastNode.nodes + node))
            else -> listOf(lastNode, ListsBLockNode(listOf(node)))
        }

        return prevNodes + nodes
    }

    private fun parseLevel(level: Level, buffer: MutableList<String>): ListNode = when (level) {
        is NumberedLevel -> OrderedListNode(level.number, levelText(level.text, buffer).parse())
        is SymbolLevel -> UnOrderedListNode(levelText(level.text, buffer).parse())
    }

    private fun levelText(text: String, buffer: MutableList<String>): List<String> {
        return mutableListOf(text).apply { addAll(buffer) }
    }

    private fun List<String>.parse(): Node {
        val result = parseInstance.parse(this.asSequence())

        return when {
            result is TextBlockNode && result.nodes.size == 1 -> result.nodes.first()
            result is BlockLayout && result.nodes.size == 1 -> result.nodes.first()
            else -> result
        }
    }

    override val parserId: String = "ListBlockParser"

    companion object {
        private const val TAB_GROUP = "tab"
        private const val SPACES_GROUP = "spaces"
        private const val DIGIT_GROUP = "digit"
        private const val SYMBOL_ORDER_GROUP = "notordered"
        private const val TEXT_GROUP = "text"

        private val TEXT_PATTERN = """
            (?<$TEXT_GROUP>.+)
        """.trimIndent()

        private val LIST_SYMBOL_PATTERN = """
            (((?<$DIGIT_GROUP>\d+?)(\s|\.))|((?<$SYMBOL_ORDER_GROUP>[-\*])\s))
        """.trimIndent()

        private val TABS_SPACE_PATTERN = """
            ((?<$TAB_GROUP>\t+?)|(?<$SPACES_GROUP>\s+?))?
        """.trimIndent()

        private val START_LEVEL_PATTERN = """
            ^$TABS_SPACE_PATTERN$LIST_SYMBOL_PATTERN$TEXT_PATTERN$
        """.trimIndent().toPattern()
    }
}