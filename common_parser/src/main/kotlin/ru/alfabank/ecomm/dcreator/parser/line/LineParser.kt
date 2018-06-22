package ru.alfabank.ecomm.dcreator.parser.line

import ru.alfabank.ecomm.dcreator.nodes.Node
import ru.alfabank.ecomm.dcreator.parser.MarkdownParser
import ru.alfabank.ecomm.dcreator.parser.ParseLineResult
import ru.alfabank.ecomm.dcreator.parser.Parser

data class BoundSymbolsSuiteResult(
    val partySuited: Boolean,
    val fullySuited: Boolean,
    val parseInstance: LineParser? = null
)

interface LineParser : Parser {
    val parseInstance: MarkdownParser

    fun openSymbolsSuited(startSymbols: String): BoundSymbolsSuiteResult

    fun endSymbolsSuited(endSymbols: String): BoundSymbolsSuiteResult

    fun openSymbolsLength(): Int

    fun endSymbolsLength(): Int

    suspend fun parse(line: String): ParseLineResult?

    fun combine(node: Node): Node?
}