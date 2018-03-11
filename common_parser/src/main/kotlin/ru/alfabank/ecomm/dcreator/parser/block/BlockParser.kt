package ru.alfabank.ecomm.dcreator.parser.block

import ru.alfabank.ecomm.dcreator.nodes.BlockNode
import ru.alfabank.ecomm.dcreator.parser.MarkdownParser
import ru.alfabank.ecomm.dcreator.parser.Parser

data class BlockSuiteResult(val isSuitable: Boolean, val result: BlockNode? = null)

interface BlockParser : Parser {
    val parseInstance: MarkdownParser

    fun isLinesSuitable(lines: List<String>): BlockSuiteResult

    fun parseLines(lines: List<String>): List<BlockNode>
}