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

        val (tag: String, param1: String, param2: String?) = parseLine(lineToParse)

        val parseResult: ServiceNode = when {
            tag == LAYOUT_TAG_NAME -> LayoutServiceNode(param1)
            tag == TITLE_TAG_NAME -> TitleServiceNode(param1)
            tag == INCLUDE_TAG_NAME && param2 != null -> IncludeServiceNode(param1, param2)
            else -> SimpleServiceNode(param1, param2)
        }

        return PartialParseResult(emptyList(), listOf(parseResult))
    }

    data class LineParseResult(val tag: String, val param1: String, val param2: String?)

    private fun parseLine(lineToParse: String): LineParseResult {
        val matcher = PARSE_PATTERN.matcher(lineToParse)

        if (!matcher.find())
            throw RuntimeException("unexpected behaviour")

        return LineParseResult(
            matcher.group(TAG_GROUP)!!,
            matcher.group(PARAM1_GROUP)!!,
            matcher.group(PARAM2_GROUP)
        )
    }

    override val parserId: String = "SpecialNodeBlockParser"

    companion object {
        private const val KEYWORD_PATTERN = ":::"

        private const val LAYOUT_TAG_NAME = "layout"
        private const val TITLE_TAG_NAME = "title"
        private const val INCLUDE_TAG_NAME = "include"

        private const val TAG_GROUP = "tag"
        private const val PARAM1_GROUP = "param1"
        private const val PARAM2_GROUP = "param2"

        private val SPACE_PATTERN = """
            [\s\t]
        """.trimIndent()

        private val TAG_PATTERN = """
            (?<$TAG_GROUP>[a-zA-Z]+)
        """.trimIndent()

        private val NAME_PATTERN = """
            "(?<$PARAM1_GROUP>[^"]+)"
        """.trimIndent()

        private val VALUE_PATTERN = """
            (,"(?<$PARAM2_GROUP>[^"]+)")
        """.trimIndent()

        private val PARSE_PATTERN = """
            ^$TAG_PATTERN$SPACE_PATTERN+$NAME_PATTERN$SPACE_PATTERN*$VALUE_PATTERN?$SPACE_PATTERN*$
        """.trimIndent().toPattern()
    }

}