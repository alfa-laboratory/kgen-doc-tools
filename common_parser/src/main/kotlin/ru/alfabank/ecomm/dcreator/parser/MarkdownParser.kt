package ru.alfabank.ecomm.dcreator.parser

import ru.alfabank.ecomm.dcreator.common.File
import ru.alfabank.ecomm.dcreator.common.withLines
import ru.alfabank.ecomm.dcreator.nodes.*
import ru.alfabank.ecomm.dcreator.parser.block.*
import ru.alfabank.ecomm.dcreator.parser.line.*

data class ParseResult(
    val result: BlockNode,
    val serviceNodes: List<ServiceNode>
)

data class PartialParseResult(
    val result: List<BlockNode>,
    val serviceNodes: List<ServiceNode> = emptyList()
)

fun List<BlockNode>.toParseResult(): PartialParseResult = PartialParseResult(this)

@Suppress("MemberVisibilityCanBePrivate")
class MarkdownParser(val fileBaseDirectory: File? = null) {
    private val defaultBlockParser = DefaultBlockParser(this)

    val blockParsers = mutableListOf(
        HeaderBlockParser(this),
        CodeBlockParser(this),
        ListBlockParser(this),
        DistributeLinkParser(this),
        BlockquotesParser(this),
        TableBlockParser(this),
        IncludeBlockParser(this)
    )

    val lineParsers: MutableList<LineParser> = mutableListOf(
        BoldLineParserStar(this),
        BoldLineParserUnderscore(this),
        ItalicLineParserStar(this),
        ItalicLineParserUnderscore(this),
        StrikethroughLineParser(this),
        UnderlineLineParser(this),
        CodeLineSingleQuoteParser(this),
        CodeLineDoubleQuoteParser(this),
        HTMLNodeLineParser(this),
        LinkLineParser(this),
        ImageLinkLineParser(this)
    )

    val lineParser = LineParserImpl(lineParsers)

    val distributeNodes: MutableMap<String, List<DistributeNode>> = mutableMapOf()

    suspend fun parse(source: File): ParseResult = source.withLines { lines ->
        parse(lines)
    }

    //TODO add more block parsers (html block elements)
    //TODO(mega) add nested table
    suspend fun parse(lines: Sequence<String>): ParseResult {
        val linesBuffer = mutableListOf<String>()
        var currentBlockParser: BlockParser? = null
        val parsedNodes = mutableListOf<BlockNode>()
        val serviceNodes = mutableListOf<ServiceNode>()

        for (line in lines.iterator()) {
            if (currentBlockParser != null) {
                val (isSuitable, result) = currentBlockParser.isLinesSuitable(linesBuffer + line)

                if (!isSuitable && result == null) {
                    currentBlockParser.parseLinesAndClearBuffer(linesBuffer)
                        .let {
                            parsedNodes += it.result
                            serviceNodes += it.serviceNodes
                        }
                    currentBlockParser = null
                } else {
                    if (result != null) {
                        parsedNodes += result
                        currentBlockParser = null
                    }
                }
            }

            if (currentBlockParser == null) {
                if (linesBuffer.isNotEmpty()) {
                    if (line.isBlank()) {
                        defaultBlockParser.parseLinesAndClearBuffer(linesBuffer)
                            .let {
                                parsedNodes += it.result
                                serviceNodes += it.serviceNodes
                            }
                    } else {
                        findBlockParser(listOf(line), parsedNodes)?.also { findParser ->
                            (currentBlockParser ?: defaultBlockParser).parseLinesAndClearBuffer(linesBuffer)
                                .let {
                                    parsedNodes += it.result
                                    serviceNodes += it.serviceNodes
                                }
                            currentBlockParser = findParser
                        }
                    }
                }

                currentBlockParser ?: run {
                    val lastLine = linesBuffer.takeLast(1)

                    findBlockParser(lastLine + listOf(line), parsedNodes)?.also { findParser ->
                        if (linesBuffer.isNotEmpty())
                            linesBuffer.removeAt(linesBuffer.lastIndex)

                        (currentBlockParser ?: defaultBlockParser).parseLinesAndClearBuffer(linesBuffer)
                            .let {
                                parsedNodes += it.result
                                serviceNodes += it.serviceNodes
                            }
                        linesBuffer.addAll(lastLine)
                        currentBlockParser = findParser
                    }
                }
            }

            linesBuffer += line
        }

        if (linesBuffer.isNotEmpty()) {
            currentBlockParser = currentBlockParser ?: findBlockParser(linesBuffer, parsedNodes)
            (currentBlockParser ?: defaultBlockParser).parseLinesAndClearBuffer(linesBuffer)
                .let {
                    parsedNodes += it.result
                    serviceNodes += it.serviceNodes
                }
        }

        val resultNode = parsedNodes.merge().let { result ->
            when {
                result.size == 1 -> result.first()
                else -> BlockLayout(result)
            }
        }

        return ParseResult(resultNode, emptyList())
    }

    private fun findBlockParser(linesBuffer: List<String>, parsedNodes: MutableList<BlockNode>): BlockParser? {
        if (linesBuffer.isNotEmpty()) {
            val blockParser = blockParsers.find {
                val (isSuitable, result) = it.isLinesSuitable(linesBuffer)

                when {
                    isSuitable && result == null -> true
                    isSuitable && result != null -> {
                        parsedNodes += result
                        true
                    }
                    else -> false
                }
            }

            if (blockParser != null)
                return blockParser
        }
        return null
    }

    private suspend fun BlockParser.parseLinesAndClearBuffer(linesBuffer: MutableList<String>): PartialParseResult =
        this.parseLines(linesBuffer).also {
            linesBuffer.clear()
        }

    private fun List<BlockNode>.merge(): List<BlockNode> = this.filter { it != EmptyBlockNode }
}
