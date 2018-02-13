package ru.alfabank.ecomm.dcreator.parser

import ru.alfabank.ecomm.dcreator.nodes.BlockLayout
import ru.alfabank.ecomm.dcreator.nodes.BlockNode
import ru.alfabank.ecomm.dcreator.nodes.DistributeNode
import ru.alfabank.ecomm.dcreator.nodes.EmptyBlockNode
import ru.alfabank.ecomm.dcreator.parser.block.*
import ru.alfabank.ecomm.dcreator.parser.line.*
import java.io.File

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

    fun parse(source: File): BlockNode {
        return source.useLines { lines ->
            parse(lines)
        }
    }

    //TODO add more block parsers (html block elements)
    //TODO(mega) add nested table
    fun parse(lines: Sequence<String>): BlockNode {
        val linesBuffer = mutableListOf<String>()
        var currentBlockParser: BlockParser? = null
        val parsedNodes = mutableListOf<BlockNode>()

        for (line in lines.iterator()) {
            if (currentBlockParser != null) {
                val (isSuitable, result) = currentBlockParser.isLinesSuitable(linesBuffer + line)

                if (!isSuitable && result == null) {
                    parseNodes(currentBlockParser, linesBuffer).let { parsedNodes += it }
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
                        parseNodes(currentBlockParser, linesBuffer).let { parsedNodes += it }
                    } else {
                        findBlockParser(listOf(line), parsedNodes)?.also { findParser ->
                            parseNodes(currentBlockParser, linesBuffer).let { parsedNodes += it }
                            currentBlockParser = findParser
                        }
                    }
                }

                currentBlockParser ?: run {
                    val lastLine = linesBuffer.takeLast(1)

                    findBlockParser(lastLine + listOf(line), parsedNodes)?.also { findParser ->
                        if (linesBuffer.isNotEmpty())
                            linesBuffer.removeAt(linesBuffer.lastIndex)

                        parseNodes(currentBlockParser, linesBuffer).let { parsedNodes += it }
                        linesBuffer.addAll(lastLine)
                        currentBlockParser = findParser
                    }
                }
            }

            linesBuffer += line
        }

        if (linesBuffer.isNotEmpty()) {
            currentBlockParser = currentBlockParser ?: findBlockParser(linesBuffer, parsedNodes)
            parseNodes(currentBlockParser, linesBuffer).let { parsedNodes += it }
        }

        parsedNodes.merge().let { result ->
            return when {
                result.size == 1 -> result.first()
                else -> BlockLayout(result)
            }
        }
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

    private fun parseNodes(currentParser: BlockParser?, linesBuffer: MutableList<String>): List<BlockNode> {
        val blockParser = currentParser ?: defaultBlockParser

        return blockParser.parseLines(linesBuffer).also {
            linesBuffer.clear()
        }
    }

    private fun List<BlockNode>.merge(): List<BlockNode> = this.filter { it != EmptyBlockNode }
}
