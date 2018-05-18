package ru.alfabank.ecomm.dcreator.parser.block

import ru.alfabank.ecomm.dcreator.nodes.BlockNode
import ru.alfabank.ecomm.dcreator.parser.MarkdownParser
import ru.alfabank.ecomm.dcreator.parser.Parser
import ru.alfabank.ecomm.dcreator.parser.PartialParseResult

data class BlockSuiteResult(val isSuitable: Boolean, val result: BlockNode? = null)

interface BlockParser : Parser {
    val parseInstance: MarkdownParser

    /**
     *  @return BlockSuiteResult - where:
     *      `isSuitable` is params, indicating when lines is suitable or not
     *      `result` is result of parsing, when checking is very expensive and need to collect result by first check
     */
    fun isLinesSuitable(lines: List<String>): BlockSuiteResult

    suspend fun parseLines(lines: List<String>): PartialParseResult
}