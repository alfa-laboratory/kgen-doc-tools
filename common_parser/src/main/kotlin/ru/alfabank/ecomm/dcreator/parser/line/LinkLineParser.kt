package ru.alfabank.ecomm.dcreator.parser.line

import ru.alfabank.ecomm.dcreator.nodes.EmptyNode
import ru.alfabank.ecomm.dcreator.nodes.LinkNode
import ru.alfabank.ecomm.dcreator.nodes.Node
import ru.alfabank.ecomm.dcreator.parser.MarkdownParser
import ru.alfabank.ecomm.dcreator.parser.ParseLineResult
import ru.alfabank.ecomm.dcreator.parser.common.Modifiers
import ru.alfabank.ecomm.dcreator.parser.common.toPattern

sealed class LinkData(open val endSymbols: String)
class LinkIdData(override val endSymbols: String, val id: String?) : LinkData(endSymbols)
class LinkUrlTitleData(override val endSymbols: String, val url: String, val title: String? = null) : LinkData(endSymbols)

class LinkLineParser(
        override val parseInstance: MarkdownParser,
        private val linkData: LinkData? = null
) : LineParser {
    override fun openSymbolsSuited(startSymbols: String): BoundSymbolsSuiteResult {
        val suitedResult = startSymbols == OPEN_SYMBOL

        return BoundSymbolsSuiteResult(suitedResult, suitedResult, null)
    }

    override fun endSymbolsSuited(endSymbols: String): BoundSymbolsSuiteResult {
        val partySuited = endSymbols.first() == END_SYMBOL_PARTY.first()

        if (!partySuited)
            return BoundSymbolsSuiteResult(false, false)

        val fullySuitedMatch = END_SYMBOLS_PATTERN.matcher(endSymbols)
        if (fullySuitedMatch.find()) {
            val linkIdGroup = fullySuitedMatch.group(LINK_ID_GROUP)
            val linkUrlGroup = fullySuitedMatch.group(LINK_URL_GROUP)
            val linkTitleGroup = fullySuitedMatch.group(LINK_TITLE_GROUP)

            when {
                linkIdGroup != null -> return BoundSymbolsSuiteResult(
                        true,
                        true,
                        LinkLineParser(parseInstance, LinkIdData(endSymbols, linkIdGroup))
                )
                linkUrlGroup != null -> return BoundSymbolsSuiteResult(
                        true,
                        true,
                        LinkLineParser(parseInstance, LinkUrlTitleData(endSymbols, linkUrlGroup, linkTitleGroup))
                )
                else -> return BoundSymbolsSuiteResult(
                        true,
                        true,
                        LinkLineParser(parseInstance, LinkIdData(endSymbols, null))
                )
            }
        }

        return BoundSymbolsSuiteResult(true, false)
    }

    override fun openSymbolsLength(): Int = OPEN_SYMBOL.length

    override fun endSymbolsLength(): Int {
        val endSymbols = linkData?.endSymbols ?: throw RuntimeException("unexpected behaviour")

        return endSymbols.length
    }

    override fun parse(line: String): ParseLineResult? {
        val result = parseInstance.lineParser.parseForLineResult(line)

        return tryCreateLink(result, line)
    }

    private fun tryCreateLink(result: ParseLineResult, line: String = ""): ParseLineResult? {
        if (linkData == null)
            return null

        val (node, findUnboundResult) = result

        return if (node == EmptyNode)
            ParseLineResult(EmptyNode, findUnboundResult)
        else when (linkData) {
            is LinkIdData -> {
                val distributeNode = LinkNode(node)

                val nodesKey = if (linkData.id?.isBlank() != false) {
                    line
                } else {
                    linkData.id
                }

                val nodes = (parseInstance.distributeNodes[nodesKey.toLowerCase()] ?: emptyList()) + distributeNode
                parseInstance.distributeNodes += nodesKey.toLowerCase() to nodes

                ParseLineResult(distributeNode, findUnboundResult)
            }
            is LinkUrlTitleData -> ParseLineResult(LinkNode(node, linkData.url, linkData.title), findUnboundResult)
        }
    }

    override fun combine(node: Node): Node? {
        return tryCreateLink(ParseLineResult(node))?.node
    }

    override val parserId: String = "LinkLineParser"

    companion object {
        private const val OPEN_SYMBOL = "["

        private const val END_SYMBOL_PARTY = "]"

        private const val LINK_ID_GROUP = "linkIdGroup"

        private const val LINK_URL_GROUP = "linkUrlGroup"

        private const val LINK_TITLE_GROUP = "linkTitleGroup"

        private val LINK_ID_PATTERN = """
            \[(?<$LINK_ID_GROUP>[\s\w\d]*?)\]
        """.trimIndent()

        private val LINK_URL_TITLE_PATTERN = """
            \((?<$LINK_URL_GROUP>[^\[\]\(\)]+?)\s*("(?<$LINK_TITLE_GROUP>[\w\s\d]+)")?\)
        """.trimIndent()

        private val END_SYMBOLS_PATTERN = """
            ^\](\s*?(($LINK_URL_TITLE_PATTERN)|($LINK_ID_PATTERN)))?$
        """.trimIndent().toPattern(Modifiers.UNICODE_CHARACTER_CLASS)
    }
}

