package ru.alfabank.ecomm.dcreator.parser.block

import ru.alfabank.ecomm.dcreator.common.Test
import ru.alfabank.ecomm.dcreator.common.assertEquals
import ru.alfabank.ecomm.dcreator.nodes.BlockLayout
import ru.alfabank.ecomm.dcreator.nodes.BlockquotesBlockNode
import ru.alfabank.ecomm.dcreator.nodes.TextBlockNode
import ru.alfabank.ecomm.dcreator.nodes.TextNode
import ru.alfabank.ecomm.dcreator.parser.MarkdownParser
import ru.alfabank.ecomm.dcreator.utils.toTextBlockNode

class BlockquotesParserTest {
    private val parser = MarkdownParser()

    @Test
    fun `test only blockquotes`() {
        val src = sequenceOf(
                "> line1",
                "> line2",
                "> line3"
        )

        val actual = parser.parse(src)

        val expected = BlockquotesBlockNode(TextBlockNode(listOf(
                TextNode(" line1"),
                TextNode(" line2"),
                TextNode(" line3")
        )))

        assertEquals(expected, actual)
    }

    @Test
    fun `test blockquotes with text`() {
        val src = sequenceOf(
                "text line",
                " > line1",
                " > line2",
                " > line3",
                "end line"
        )

        val actual = parser.parse(src)

        val expected = BlockLayout(listOf(
                TextNode("text line").toTextBlockNode(),
                BlockquotesBlockNode(listOf(
                        TextNode(" line1"),
                        TextNode(" line2"),
                        TextNode(" line3")
                ).toTextBlockNode()),
                TextNode("end line").toTextBlockNode()
        ))

        assertEquals(expected, actual)
    }
}