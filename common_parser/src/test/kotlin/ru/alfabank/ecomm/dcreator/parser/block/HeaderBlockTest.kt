package ru.alfabank.ecomm.dcreator.parser.block

import ru.alfabank.ecomm.dcreator.nodes.BlockLayout
import ru.alfabank.ecomm.dcreator.nodes.HeaderBlockNode
import ru.alfabank.ecomm.dcreator.nodes.Level
import ru.alfabank.ecomm.dcreator.nodes.TextNode
import ru.alfabank.ecomm.dcreator.parser.MarkdownParser
import ru.alfabank.ecomm.dcreator.utils.toTextBlockNode

class HeaderBlockTest {
    private val parser = MarkdownParser()

    @Test
    fun `test single line header block`() {
        val singleLineHeader = sequenceOf("# some text")
        val singleLineResult = parser.parse(singleLineHeader)
        assertEquals(HeaderBlockNode(level = Level.ONE, node = TextNode(text = " some text")), singleLineResult)

        val singleLineHeader2 = sequenceOf("## some text")
        val singleLineResult2 = parser.parse(singleLineHeader2)
        assertEquals(HeaderBlockNode(level = Level.TWO, node = TextNode(text = " some text")), singleLineResult2)

        val singleLineHeader3 = sequenceOf("### some text")
        val singleLineResult3 = parser.parse(singleLineHeader3)
        assertEquals(HeaderBlockNode(level = Level.THREE, node = TextNode(text = " some text")), singleLineResult3)

        val singleLineHeader4 = sequenceOf("#### some text")
        val singleLineResult4 = parser.parse(singleLineHeader4)
        assertEquals(HeaderBlockNode(level = Level.FOUR, node = TextNode(text = " some text")), singleLineResult4)

        val singleLineHeader5 = sequenceOf("##### some text")
        val singleLineResult5 = parser.parse(singleLineHeader5)
        assertEquals(HeaderBlockNode(level = Level.FIVE, node = TextNode(text = " some text")), singleLineResult5)
    }

    @Test
    fun `test two line header block`() {
        val twoLineHeader = sequenceOf(
                " some text",
                "----------"
        )
        val twoLineHeaderResult = parser.parse(twoLineHeader)
        assertEquals(HeaderBlockNode(level = Level.ONE, node = TextNode(text = " some text")), twoLineHeaderResult)


        val twoLineHeader2 = sequenceOf(
                " some text",
                "============"
        )
        val twoLineHeaderResult2 = parser.parse(twoLineHeader2)
        assertEquals(HeaderBlockNode(level = Level.TWO, node = TextNode(text = " some text")), twoLineHeaderResult2)
    }

    @Test
    fun `test complex cases for single line header`() {
        val testLines = sequenceOf(
                " some text",
                " some text2",
                "# some header",
                "another text"
        )
        val actualResult = parser.parse(testLines)
        val expectedResult = BlockLayout(listOf(
                listOf(
                        TextNode(" some text"),
                        TextNode(" some text2")
                ).toTextBlockNode(),
                HeaderBlockNode(level = Level.ONE, node = TextNode(text = " some header")),
                TextNode("another text").toTextBlockNode()
        ))

        assertEquals(expectedResult, actualResult)
    }

    @Test
    fun `test complex cases for multi line header`() {
        val testLines = sequenceOf(
                " some text",
                " some text2",
                " some header",
                " ===============",
                "another text"
        )
        val actualResult = parser.parse(testLines)
        val expectedResult = BlockLayout(listOf(
                listOf(
                        TextNode(" some text"),
                        TextNode(" some text2")
                ).toTextBlockNode(),
                HeaderBlockNode(level = Level.TWO, node = TextNode(text = " some header")),
                TextNode("another text").toTextBlockNode()
        ))

        assertEquals(expectedResult, actualResult)
    }
}