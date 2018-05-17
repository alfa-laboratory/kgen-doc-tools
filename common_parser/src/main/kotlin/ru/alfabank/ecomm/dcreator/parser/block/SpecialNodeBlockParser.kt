package ru.alfabank.ecomm.dcreator.parser.block

import ru.alfabank.ecomm.dcreator.common.toPattern
import ru.alfabank.ecomm.dcreator.nodes.*
import ru.alfabank.ecomm.dcreator.parser.MarkdownParser
import ru.alfabank.ecomm.dcreator.parser.PartialParseResult

class SpecialNodeBlockParser(override val parseInstance: MarkdownParser) : BlockParser {
    override fun isLinesSuitable(lines: List<String>): BlockSuiteResult {
        if (lines.size == 1) {
            val firstLine = lines.first()
            if (firstLine.length > KEYWORD_PATTERN.length
                && firstLine.take(KEYWORD_PATTERN.length) == KEYWORD_PATTERN
            ) {
                return BlockSuiteResult(true)
            }
        }

        return BlockSuiteResult(false)
    }

    override suspend fun parseLines(lines: List<String>): PartialParseResult {
        if (lines.size != 1)
            throw RuntimeException("unexpected behaviour")

        val lineToParse = lines.first().let {
            it.substring(KEYWORD_PATTERN.length, it.length)
        }

        val (tag: String, name: String, value: String?) = parseLine(lineToParse)

        val parseResult: ServiceNode = when {
            tag == LAYOUT_TAG_NAME -> LayoutServiceNode(name)
            tag == TITLE_TAG_NAME -> TitleServiceNode(name)
            tag == INCLUDE_TAG_NAME && value != null -> IncludeServiceNode(name, value)
            else -> SimpleSeviceNode(name, value)
        }

        return PartialParseResult(emptyList(), listOf(parseResult))
    }

    data class LineParseResult(val tag: String, val name: String, val value: String?)

    private fun parseLine(lineToParse: String): LineParseResult {
        val matcher = PARSE_PATTERN.matcher(lineToParse)

        if (!matcher.find())
            throw RuntimeException("unexpected behaviour")

        return LineParseResult(
            matcher.group(TAG_GROUP)!!,
            matcher.group(NAME_GROUP)!!,
            matcher.group(VALUE_GROUP)
        )
    }

    override val parserId: String = "SpecialNodeBlockParser"

    companion object {
        private const val KEYWORD_PATTERN = ":::"

        private const val LAYOUT_TAG_NAME = "layout"
        private const val TITLE_TAG_NAME = "title"
        private const val INCLUDE_TAG_NAME = "include"

        private const val TAG_GROUP = "tag"
        private const val NAME_GROUP = "name"
        private const val VALUE_GROUP = "value"

        private val SPACE_PATTERN = """
            [\s\t]
        """.trimIndent()

        private val TAG_PATTERN = """
            (?<$TAG_GROUP>[a-z]+)
        """.trimIndent()

        private val NAME_PATTERN = """
            "(?<$NAME_GROUP>.+)"
        """.trimIndent()

        private val VALUE_PATTERN = """
            "(?<$VALUE_GROUP>.+)"
        """.trimIndent()

        private val PARSE_PATTERN = """
            ^$TAG_PATTERN$SPACE_PATTERN+$NAME_PATTERN$SPACE_PATTERN*$VALUE_PATTERN?$SPACE_PATTERN*$
        """.trimIndent().toPattern()
    }

}