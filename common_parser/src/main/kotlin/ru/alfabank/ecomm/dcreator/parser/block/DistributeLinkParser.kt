package ru.alfabank.ecomm.dcreator.parser.block

import ru.alfabank.ecomm.dcreator.common.Pattern
import ru.alfabank.ecomm.dcreator.common.toPattern
import ru.alfabank.ecomm.dcreator.nodes.BlockNode
import ru.alfabank.ecomm.dcreator.nodes.EmptyBlockNode
import ru.alfabank.ecomm.dcreator.parser.MarkdownParser

class DistributeLinkParser(override val parseInstance: MarkdownParser) : BlockParser {
    override fun isLinesSuitable(lines: List<String>): BlockSuiteResult {
        if (lines.size != 1)
            return BlockSuiteResult(false)

        val match = DISTRIBUTE_LINK_PATTERN.matcher(lines.first())

        return if (match.find()) {
            val id = match.group(ID_GROUP)
            val link = match.group(LINK_GROUP)
            val title = match.group(TITLE_GROUP)

            if (id != null && (link != null || title != null)) {
                parseInstance.distributeNodes[id.toLowerCase()]?.forEach { node ->
                    node.title = node.title ?: title
                    node.url = node.url ?: link
                }

                BlockSuiteResult(true, EmptyBlockNode)
            } else {
                BlockSuiteResult(false)
            }
        } else {
            BlockSuiteResult(false)
        }
    }

    override fun parseLines(lines: List<String>): List<BlockNode> = emptyList()

    override val parserId: String = "DistributeLinkParser"

    companion object {
        private const val ID_GROUP = "id"
        private const val LINK_GROUP = "link"
        private const val TITLE_GROUP = "title"

        private val SPACE_PATTERN = """
            [\s\t]*
        """.trimIndent()

        private val TITLE_NAME_PATTERN = """
            (?<$TITLE_GROUP>.+)
        """.trimIndent()

        private val TITLE_PATTERN = """
            (["'(]($TITLE_NAME_PATTERN)["')])
        """.trimIndent()

        private val LINK_PATTERN = """
            (<?(?<$LINK_GROUP>[^<>\(\)\[\]\s\t]+)>?)
        """.trimIndent()

        private val ID_PATTERN = """
            \[(?<$ID_GROUP>[\w\d\s-\.@/]+)\]:
        """.trimIndent()

        private val DISTRIBUTE_LINK_PATTERN = """
            ^$SPACE_PATTERN$ID_PATTERN$SPACE_PATTERN$LINK_PATTERN$SPACE_PATTERN($TITLE_PATTERN$SPACE_PATTERN)?$
        """.trimIndent().toPattern(Pattern.UNICODE_CHARACTER_CLASS)
    }
}