package ru.alfabank.ecomm.dcreator.parser.block

import ru.alfabank.ecomm.dcreator.common.File
import ru.alfabank.ecomm.dcreator.common.toPattern
import ru.alfabank.ecomm.dcreator.nodes.BlockLayout
import ru.alfabank.ecomm.dcreator.nodes.BlockNode
import ru.alfabank.ecomm.dcreator.parser.MarkdownParser
import ru.alfabank.ecomm.dcreator.parser.PartialParseResult
import ru.alfabank.ecomm.dcreator.parser.toParseResult

class IncludeBlockParser(override val parseInstance: MarkdownParser) : BlockParser {
    override fun isLinesSuitable(lines: List<String>): BlockSuiteResult {
        if (lines.size == 1) {
            val firstLine = lines.first()
            if (firstLine.length > KEYWORD_PATTERN.length
                && firstLine.take(KEYWORD_PATTERN.length) == KEYWORD_PATTERN
            )
                return BlockSuiteResult(true)
        }

        return BlockSuiteResult(false)
    }

    override suspend fun parseLines(lines: List<String>): PartialParseResult {
        if (lines.size != 1)
            throw RuntimeException("unexpected behaviour")

        if (parseInstance.fileBaseDirectory == null)
            return emptyList<BlockNode>().toParseResult()

        val fileNameMatcher = INCLUDE_PATTERN.matcher(lines.first())
        if (fileNameMatcher.find()) {
            val fileName = fileNameMatcher.group(NAME_GROUP)!!

            val includeFile = File(parseInstance.fileBaseDirectory, fileName)

            if (includeFile.exists()) {
                val (parseResult, serviceNodes) = parseInstance.parse(includeFile)

                val result: List<BlockNode> = when (parseResult) {
                    is BlockLayout -> parseResult.nodes
                    else -> listOf(parseResult)
                }

                return PartialParseResult(result, serviceNodes)
            }
        }

        return emptyList<BlockNode>().toParseResult()
    }

    override val parserId: String = "IncludeBlockParser"

    companion object {
        private const val NAME_GROUP = "filename"

        private val KEYWORD_PATTERN = """
            ::include
        """.trimIndent()

        private val SPACE_PATTERN = """
            [\s\t]
        """.trimIndent()

        private val FILE_NAME_PATTERN = """
            "(?<$NAME_GROUP>.+)"
        """.trimIndent()

        private val INCLUDE_PATTERN = """
            ^$KEYWORD_PATTERN$SPACE_PATTERN+$FILE_NAME_PATTERN$SPACE_PATTERN*$
        """.trimIndent().toPattern()
    }
}