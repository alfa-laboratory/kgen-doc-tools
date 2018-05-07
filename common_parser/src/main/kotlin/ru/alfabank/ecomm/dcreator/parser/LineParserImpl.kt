package ru.alfabank.ecomm.dcreator.parser

import ru.alfabank.ecomm.dcreator.nodes.EmptyNode
import ru.alfabank.ecomm.dcreator.nodes.Node
import ru.alfabank.ecomm.dcreator.nodes.RowLayout
import ru.alfabank.ecomm.dcreator.nodes.TextNode
import ru.alfabank.ecomm.dcreator.parser.line.LineParser

typealias FindSuitedResult = Pair<Boolean, List<LineParser>>

inline fun <T> CharSequence.findSuitedResult(lambda: (Char, Int) -> Pair<Boolean, T>): Pair<Int, T>? {
    var index = 0
    for (item in this) {
        val (find, value) = lambda(item, index)
        if (find)
            return Pair(index, value)

        index++
    }
    return null
}

data class ParseLineResult(val node: Node, val findUnboundParser: Boolean = false)

class LineParserImpl(private val lineParsers: List<LineParser>) {
    fun parse(line: String): Node = parseForLineResult(line).node

    fun parseForLineResult(line: String): ParseLineResult {
        val result = parseLine(line)

        return ParseLineResult(result.nodes.merge(), result.findUnboundParser)
    }

    private fun List<Node>.merge(): Node {
        val nodes = this.filter { it != EmptyNode }
            .fold(listOf()) { acc: List<Node>, node: Node ->
                if (acc.lastOrNull() is TextNode && node is TextNode) {
                    val textNode = acc.last() as TextNode
                    acc.dropLast(1) + TextNode(textNode.text + node.text)
                } else {
                    acc + node
                }
            }

        return when {
            nodes.isEmpty() -> EmptyNode
            nodes.size == 1 -> nodes.first()
            else -> RowLayout(nodes)
        }
    }

    data class ParseLineNodesResult(val nodes: List<Node>, val findUnboundParser: Boolean)

    private tailrec fun parseLine(
        line: String,
        prevNodes: List<Node> = emptyList(),
        findUnboundParser: Boolean = false
    ): ParseLineNodesResult {
        if (line.isEmpty())
            return ParseLineNodesResult(prevNodes, findUnboundParser = findUnboundParser)

        val result = line.findSuitedResult { char, _ ->
            findSuitedParser(char.toString())
        }

        return if (result == null)
            ParseLineNodesResult(prevNodes + listOf(TextNode.from(line)), findUnboundParser)
        else {
            val (index, suitedParsers) = result
            val textLine = line.substring(0, index)
            val lineToProcess = line.substring(index)

            //process with all first special symbols
            val (notParsedLine: String, node: Node?, _, localFindUnboundParser) =
                    parseLineByParsers(lineToProcess, suitedParsers, startCheckIndex = 2)

            if (node != null) {
                parseLine(
                    notParsedLine,
                    prevNodes + listOf(TextNode.from(textLine), node),
                    findUnboundParser = findUnboundParser || localFindUnboundParser
                )
            } else {
                //move to 1 symbol forward
                parseLine(
                    line.substring(index + 1),
                    prevNodes + listOf(TextNode.from(line.substring(0, index + 1))),
                    findUnboundParser = findUnboundParser || localFindUnboundParser
                )
            }
        }
    }

    data class ParseResult(
        val notParserString: String,
        val resultNode: Node?,
        val skipSymbols: Int = 0,
        val findUnboundParser: Boolean = false
    )

    private fun parseLineByParsers(
        lineToProcess: String,
        suitedParsers: List<LineParser> = emptyList(),
        startCheckIndex: Int = 1,
        parentParser: LineParser? = null
    ): ParseResult {
        if (lineToProcess.isEmpty())
            return ParseResult("", null)

        //forward
        var findParser = false
        val allParsers: MutableList<Pair<Int, List<LineParser>>> = mutableListOf(1 to suitedParsers)
        for (index in startCheckIndex..lineToProcess.length) {
            val testSymbols = lineToProcess.substring(0, index)
            val (status, parsers) = findSuitedParser(testSymbols)

            if (parsers.isNotEmpty())
                findParser = true

            if (status) {
                allParsers += index to parsers
            } else {
                break
            }
        }

        val parseResult = if (parentParser == null)
            tryParse(allParsers, lineToProcess)
        else
            null

        return when {
            parentParser == null -> when (parseResult) {
                null -> ParseResult(lineToProcess, null, findUnboundParser = findParser)
                else -> ParseResult(lineToProcess.substring(parseResult.skipSymbols), parseResult.resultNode)
            }
            parseResult?.resultNode != null -> {
                val combineNode = parentParser.combine(parseResult.resultNode)
                when (combineNode) {
                    null -> null
                    else -> {
                        val notProcessingLine = lineToProcess.substring(parseResult.skipSymbols)

                        val lastSubstringIndex = parentParser.endSymbolsLength()
                        if (lastSubstringIndex > notProcessingLine.length)
                            null
                        else {
                            val testSymbols = notProcessingLine.substring(0, lastSubstringIndex)
                            val (_, fullySuited) = parentParser.endSymbolsSuited(testSymbols)

                            if (fullySuited) {
                                val newSkipLength = parseResult.skipSymbols + parentParser.endSymbolsLength()
                                ParseResult(
                                    lineToProcess.substring(newSkipLength),
                                    parentParser.combine(parseResult.resultNode),
                                    newSkipLength + parentParser.openSymbolsLength()
                                )
                            } else
                                null
                        }
                    }
                }
            }
            else -> tryParseByParentParser(lineToProcess, parentParser)
        } ?: ParseResult(lineToProcess, null)
    }

    private fun tryParseByParentParser(lineToProcess: String, parentParser: LineParser): ParseResult? {
        outerLoop@ for (startIndex in 0..(lineToProcess.lastIndex)) {
            val findSuitedSymbols = mutableListOf<Triple<Int, Int, LineParser?>>()
            innerLoop@ for (endIndex in (startIndex + 1)..(lineToProcess.length)) {
                val testSymbols = lineToProcess.substring(startIndex, endIndex)
                val (partySuited, fullySuited, newParentParser) = parentParser.endSymbolsSuited(testSymbols)

                if (!partySuited)
                    break@innerLoop

                if (fullySuited) {
                    findSuitedSymbols += Triple(startIndex, endIndex, newParentParser)
                }
            }

            findSuitedSymbols.asReversed().forEach { (startIndex, endIndex, newParentParser) ->
                val notParserString = lineToProcess.substring(endIndex, lineToProcess.length)
                val parseString = lineToProcess.substring(0, startIndex)
                val usedParser = newParentParser ?: parentParser

                val result = usedParser.parse(parseString)
                if (result != null && result.node != EmptyNode && !result.findUnboundParser) {
                    return ParseResult(
                        notParserString,
                        result.node,
                        parseString.length + usedParser.endSymbolsLength() + usedParser.openSymbolsLength()
                    )
                }
            }
        }



        return null
    }

    private fun tryParse(allParsers: MutableList<Pair<Int, List<LineParser>>>, lineToProcess: String): ParseResult? {
        allParsers.reversed()
            .forEach { (index, parsers) ->

                parsers.forEach { parser ->
                    if (index <= lineToProcess.length) {
                        val result = parseLineByParsers(lineToProcess.substring(index), parentParser = parser)

                        if (result.resultNode != null) {
                            return result
                        }
                    }
                }
            }

        return null
    }

    private fun findSuitedParser(
        line: String
    ): FindSuitedResult {
        val childParsers = lineParsers

        val newPartialSuitedParsers = childParsers
            .map { it to it.openSymbolsSuited(line) }
            .filter { (_, suited) -> suited.partySuited }

        if (newPartialSuitedParsers.isNotEmpty()) {
            val fullySuitedParsers = newPartialSuitedParsers
                .filter { it.second.fullySuited }
                .map { it.second.parseInstance ?: it.first }

            return FindSuitedResult(true, fullySuitedParsers)
        }

        return FindSuitedResult(false, emptyList())
    }
}
