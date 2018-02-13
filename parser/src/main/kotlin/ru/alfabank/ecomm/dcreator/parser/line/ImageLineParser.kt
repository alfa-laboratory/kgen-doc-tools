package ru.alfabank.ecomm.dcreator.parser.line

import ru.alfabank.ecomm.dcreator.nodes.ImageLinkNode
import ru.alfabank.ecomm.dcreator.nodes.Node
import ru.alfabank.ecomm.dcreator.parser.MarkdownParser
import ru.alfabank.ecomm.dcreator.parser.ParseLineResult
import java.util.regex.Pattern

sealed class ImageLinkData(open val endSymbols: String)
data class ImageLinkIdData(override val endSymbols: String, val id: String?) : ImageLinkData(endSymbols)
data class ImageLinkUrlTitleData(override val endSymbols: String, val url: String, val title: String? = null) : ImageLinkData(endSymbols)

class ImageLinkLineParser(
        override val parseInstance: MarkdownParser,
        private val linkData: ImageLinkData? = null
) : LineParser {
    override fun openSymbolsSuited(startSymbols: String): BoundSymbolsSuiteResult {
        val partySuited = startSymbols.first() == OPEN_SYMBOL.first() && startSymbols.length <= OPEN_SYMBOL.length
        val fullySuited = startSymbols == OPEN_SYMBOL

        return BoundSymbolsSuiteResult(partySuited, fullySuited, null)
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

            if (linkIdGroup != null) {
                return BoundSymbolsSuiteResult(
                        true,
                        true,
                        ImageLinkLineParser(parseInstance, ImageLinkIdData(endSymbols, linkIdGroup))
                )
            } else if (linkUrlGroup != null) {
                return BoundSymbolsSuiteResult(
                        true,
                        true,
                        ImageLinkLineParser(parseInstance, ImageLinkUrlTitleData(endSymbols, linkUrlGroup, linkTitleGroup))
                )
            } else {
                return BoundSymbolsSuiteResult(
                        true,
                        true,
                        ImageLinkLineParser(parseInstance, ImageLinkIdData(endSymbols, null))
                )
            }
        }

        return BoundSymbolsSuiteResult(true, false)
    }

    override fun openSymbolsLength(): Int = OPEN_SYMBOL.length

    override fun endSymbolsLength(): Int {
        val endSymbols = linkData?.endSymbols ?: throw RuntimeException("un expect behaviour")

        return endSymbols.length
    }

    override fun parse(line: String): ParseLineResult? {
        if (linkData == null)
            return null

        return when (linkData) {
            is ImageLinkIdData -> {
                val distributeNode = ImageLinkNode(line)

                val nodesKey = if (linkData.id?.isBlank() != false) {
                    line
                } else {
                    linkData.id
                }

                val nodes = (parseInstance.distributeNodes[nodesKey] ?: emptyList()) + distributeNode
                parseInstance.distributeNodes += nodesKey to nodes

                ParseLineResult(distributeNode)
            }
            is ImageLinkUrlTitleData -> ParseLineResult(ImageLinkNode(line, linkData.url, linkData.title))
        }
    }

    override fun combine(node: Node): Node? = null

    override val parserId: String = "ImageLinkLineParser"

    companion object {
        private const val OPEN_SYMBOL = "!["

        private const val END_SYMBOL_PARTY = "]"

        private const val LINK_ID_GROUP = "linkIdGroup"

        private const val LINK_URL_GROUP = "linkUrlGroup"

        private const val LINK_TITLE_GROUP = "linkTitleGroup"

        private val LINK_ID_PATTERN = """
            \[(?<$LINK_ID_GROUP>[\s\w\d]*?)\]
        """.trimIndent()

        private val LINK_URL_TITLE_PATTERN = """
            \((?<$LINK_URL_GROUP>[^\s\[\]\(\)]+?)\s*("(?<$LINK_TITLE_GROUP>[\w\s\d]+)")?\)
        """.trimIndent()

        private val END_SYMBOLS_PATTERN = """
            ^\]\s*?(($LINK_URL_TITLE_PATTERN)|($LINK_ID_PATTERN))?$
        """.trimIndent().toPattern(Pattern.UNICODE_CHARACTER_CLASS)
    }
}

