package ru.alfabank.ecomm.dcreator.parser.line

import ru.alfabank.ecomm.dcreator.common.Pattern
import ru.alfabank.ecomm.dcreator.common.Predicate
import ru.alfabank.ecomm.dcreator.common.toPattern
import ru.alfabank.ecomm.dcreator.nodes.HTMLNode
import ru.alfabank.ecomm.dcreator.nodes.Node
import ru.alfabank.ecomm.dcreator.parser.MarkdownParser
import ru.alfabank.ecomm.dcreator.parser.ParseLineResult

class OpenTag(val tagName: String, val fullOpenTag: String)

class HTMLNodeLineParser(
    override val parseInstance: MarkdownParser,
    private val usedTag: OpenTag? = null
) : LineParser {
    override fun openSymbolsSuited(startSymbols: String): BoundSymbolsSuiteResult {
        val partySuited = HTML_OPEN_TAG_PARTIAL_PREDICATE.test(startSymbols)
        val openTagMatch = HTML_OPEN_TAG_PATTERN.matcher(startSymbols)

        return if (openTagMatch.find()) {
            val openTagName = openTagMatch.group(TAG_GROUP_NAME)!!

            BoundSymbolsSuiteResult(
                partySuited,
                true,
                HTMLNodeLineParser(parseInstance, OpenTag(openTagName, startSymbols))
            )
        } else
            BoundSymbolsSuiteResult(partySuited, false)
    }

    override fun endSymbolsSuited(endSymbols: String): BoundSymbolsSuiteResult {
        val tagName = usedTag?.tagName ?: throw RuntimeException("unexpected behaviour")

        val partySuited = endSymbols.first() == TAG_CLOSE.first()

        val matchResult = closePredicate(tagName).test(endSymbols)

        return BoundSymbolsSuiteResult(partySuited, matchResult)
    }

    override fun openSymbolsLength(): Int {
        return usedTag?.fullOpenTag?.length ?: throw RuntimeException("unexpected behaviour")
    }

    override fun endSymbolsLength(): Int {
        val tagLength = usedTag?.tagName?.length ?: throw RuntimeException("unexpected behaviour")

        return tagLength + 3
    }

    override val parserId: String = "HTMLNodeLineParser"

    override fun parse(line: String): ParseLineResult? {
        val tag = usedTag ?: throw RuntimeException("unexpected behaviour")

        return ParseLineResult(HTMLNode("${tag.fullOpenTag}$line</${tag.tagName}>"))
    }

    override fun combine(node: Node): Node? = null

    private fun closePredicate(tag: String): Predicate = """
        </$tag>
    """.trimIndent().toPattern(Pattern.CASE_INSENSITIVE).asPredicate()

    companion object {
        private const val TAG_CLOSE = "<"

        private const val TAG_GROUP_NAME = "tag"

        private val HTML_OPEN_TAG_PARTIAL_PREDICATE = """
            ^<.*?>?$
        """.trimIndent()
            .toPattern(Pattern.CASE_INSENSITIVE)
            .asPredicate()

        private val HTML_PARAM_PATTERN = """
            \w+?=".*"
        """.trimIndent()

        private val HTML_OPEN_TAG_PATTERN = """
            ^<\s*(?<$TAG_GROUP_NAME>\w+)\s*(?:$HTML_PARAM_PATTERN)*?>$
        """.trimIndent().toPattern(Pattern.CASE_INSENSITIVE)
    }
}