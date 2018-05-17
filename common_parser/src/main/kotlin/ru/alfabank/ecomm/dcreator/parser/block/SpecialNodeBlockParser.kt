package ru.alfabank.ecomm.dcreator.parser.block

import ru.alfabank.ecomm.dcreator.parser.MarkdownParser
import ru.alfabank.ecomm.dcreator.parser.PartialParseResult

class SpecialNodeBlockParser(override val parseInstance: MarkdownParser) : BlockParser {
    override fun isLinesSuitable(lines: List<String>): BlockSuiteResult {
//        if (lines.size == 1) {
//            val firstLine = lines.first()
//            if (firstLine.length > KEYWORD_PATTERN.length
//                && firstLine.take(KEYWORD_PATTERN.length) == KEYWORD_PATTERN
//            )
//                return BlockSuiteResult(true)
//        }
//
//        return BlockSuiteResult(false)

        return BlockSuiteResult(false)
    }

    override suspend fun parseLines(lines: List<String>): PartialParseResult {
//        if (lines.size != 1)
//            throw RuntimeException("unexpected behaviour")
//
//        if (parseInstance.fileBaseDirectory == null)
//            return emptyList()
//
//        val fileNameMatcher = INCLUDE_PATTERN.matcher(lines.first())
//        if (fileNameMatcher.find()) {
//            val fileName = fileNameMatcher.group(NAME_GROUP)!!
//
//            val includeFile = File(parseInstance.fileBaseDirectory, fileName)
//
//            if (includeFile.exists()) {
//                val parseResult = parseInstance.parse(includeFile)
//
//                return when (parseResult) {
//                    is BlockLayout -> parseResult.nodes
//                    else -> listOf(parseResult)
//                }
//            }
//        }
//
//        return emptyList()

        return PartialParseResult(emptyList())
    }

    override val parserId: String = "SpecialNodeBlockParser"

    companion object {
        private const val KEYWORD_PATTERN = ":::"

//        private val SPACE_PATTERN = """
//            [\s\t]
//        """.trimIndent()
//
//        private val FILE_NAME_PATTERN = """
//            "(?<$NAME_GROUP>.+)"
//        """.trimIndent()
//
//        private val INCLUDE_PATTERN = """
//            ^$KEYWORD_PATTERN$SPACE_PATTERN+$FILE_NAME_PATTERN$SPACE_PATTERN*$
//        """.trimIndent().toPattern()
    }

}