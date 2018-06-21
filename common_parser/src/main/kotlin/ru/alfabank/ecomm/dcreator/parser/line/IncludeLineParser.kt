package ru.alfabank.ecomm.dcreator.parser.line

import ru.alfabank.ecomm.dcreator.common.File
import ru.alfabank.ecomm.dcreator.common.toPattern
import ru.alfabank.ecomm.dcreator.nodes.BlockLayout
import ru.alfabank.ecomm.dcreator.nodes.Node
import ru.alfabank.ecomm.dcreator.nodes.TextBlockNode
import ru.alfabank.ecomm.dcreator.parser.MarkdownParser
import ru.alfabank.ecomm.dcreator.parser.ParseLineResult

class IncludeLineParser(
    override val parseInstance: MarkdownParser,
    private val startSymbols: String? = null
) : LineParser {
    override fun openSymbolsSuited(startSymbols: String): BoundSymbolsSuiteResult {
        return if (startSymbols.startsWith(START_SYMBOLS)) {
            BoundSymbolsSuiteResult(
                true,
                START_PREDICATE.test(startSymbols),
                IncludeLineParser(parseInstance, startSymbols)
            )
        } else {
            BoundSymbolsSuiteResult(false, false, null)
        }
    }

    override fun endSymbolsSuited(endSymbols: String): BoundSymbolsSuiteResult {
        return if (endSymbols.endsWith(END_SYMBOLS)) {
            BoundSymbolsSuiteResult(true, true, this)
        } else {
            BoundSymbolsSuiteResult(true, false, this)
        }
    }

    override fun openSymbolsLength(): Int = startSymbols!!.length

    override fun endSymbolsLength(): Int = END_SYMBOLS.length

    override suspend fun parse(line: String): ParseLineResult? {
        val matcher = FILE_NAME_PATTERN.matcher(line)

        if (matcher.find()) {
            val fileName = matcher.group(NAME_GROUP)!!

            val includeFile = File(parseInstance.fileBaseDirectory!!, fileName)

            if (includeFile.exists()) {
                val (parseResult, serviceNodes) = parseInstance.parse(includeFile)

                val result: Node = when {
                    parseResult is BlockLayout && parseResult.nodes.size == 1 -> parseResult.nodes.first()
                    parseResult is TextBlockNode && parseResult.nodes.size == 1 -> parseResult.nodes.first()
                    else -> parseResult
                }

                return ParseLineResult(result)
            }
        }

        return null
    }

    override fun combine(node: Node): Node? = null

    override val parserId: String = "IncludeLineParser"

    companion object {
        private const val NAME_GROUP = "filename"

        private const val START_SYMBOLS = ":"

        private val KEYWORD_PATTERN = """
            ${START_SYMBOLS}:include
        """.trimIndent()

        private val SPACE_PATTERN = """
            [\s\t]
        """.trimIndent()

        private val END_SYMBOLS = "\""

        private val FILE_NAME_PATTERN = """
            ^(?<$NAME_GROUP>.+)$
        """.trimIndent().toPattern()

        private val START_PREDICATE = """
            ^$KEYWORD_PATTERN$SPACE_PATTERN+"?$
        """.trimIndent().toPattern().asPredicate()
    }

}