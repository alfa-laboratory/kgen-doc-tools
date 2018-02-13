package ru.alfabank.ecomm.dcreator.parser.block

import ru.alfabank.ecomm.dcreator.nodes.BlockLayout
import ru.alfabank.ecomm.dcreator.nodes.BlockNode
import ru.alfabank.ecomm.dcreator.parser.MarkdownParser
import java.io.File

class IncludeBlockParser(override val parseInstance: MarkdownParser) : BlockParser {
    override fun isLinesSuitable(lines: List<String>): BlockSuiteResult {
        if (lines.size == 1) {
            val firstLine = lines.first()
            if (firstLine.length > KEYWORD_PATTERN.length
                    && firstLine.take(KEYWORD_PATTERN.length) == KEYWORD_PATTERN)
                return BlockSuiteResult(true)
        }

        return BlockSuiteResult(false)
    }

    override fun parseLines(lines: List<String>): List<BlockNode> {
        if (lines.size != 1)
            throw RuntimeException("unexpected behaviour")

        if (parseInstance.fileBaseDirectory == null)
            return emptyList()

        val fileNameMatcher = INCLUDE_PATTERN.matcher(lines.first())
        if (fileNameMatcher.find()) {
            val fileName = fileNameMatcher.group(NAME_GROUP)

            val includeFile = File(parseInstance.fileBaseDirectory, fileName)

            if (includeFile.exists()) {
                val parseResult = parseInstance.parse(includeFile)

                return when (parseResult) {
                    is BlockLayout -> parseResult.nodes
                    else -> listOf(parseResult)
                }
            }
        }

        return emptyList()
    }

    override val parserId: String = "IncludeBlockParser"

    companion object {
        private const val NAME_GROUP = "filename"

        private val KEYWORD_PATTERN = """
            #include
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