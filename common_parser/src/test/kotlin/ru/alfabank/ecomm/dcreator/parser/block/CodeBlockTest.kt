package ru.alfabank.ecomm.dcreator.parser.block

import ru.alfabank.ecomm.dcreator.common.Test
import ru.alfabank.ecomm.dcreator.common.assertEquals
import ru.alfabank.ecomm.dcreator.nodes.BlockLayout
import ru.alfabank.ecomm.dcreator.nodes.CodeBlockNode
import ru.alfabank.ecomm.dcreator.nodes.TextNode
import ru.alfabank.ecomm.dcreator.parser.MarkdownParser
import ru.alfabank.ecomm.dcreator.utils.toTextBlockNode

class CodeBlockTest {
    private val parser = MarkdownParser()

    @Test
    fun `test parsing of code block`() {
        val code = sequenceOf(
                "```javascript",
                "some code line 1",
                "",
                "some code line 3",
                "```"
        )
        val actualResult = parser.parse(code)

        val expectedCodeLines = listOf(
                "some code line 1",
                "",
                "some code line 3"
        )
        val expectedResult = CodeBlockNode("javascript", expectedCodeLines.joinToString("\n"))

        assertEquals(expectedResult, actualResult)
    }

    @Test
    fun `test parsing of code block without language name`() {
        val code = sequenceOf(
                "```",
                "some code line 1",
                "```"
        )
        val actualResult = parser.parse(code)

        val expectedCodeLines = listOf(
                "some code line 1"
        )
        val expectedResult = CodeBlockNode("", expectedCodeLines.joinToString("\n"))

        assertEquals(expectedResult, actualResult)
    }

    @Test
    fun `test parsing of code block with text`() {
        val code = sequenceOf(
                "some text",
                "```javascript",
                "some code line 1",
                "some code line 2",
                "some code line 3",
                "```",
                "some another text"
        )
        val actualResult = parser.parse(code)

        val expectedCodeLines = listOf(
                "some code line 1",
                "some code line 2",
                "some code line 3"
        )
        val expectedResult = BlockLayout(listOf(
                TextNode("some text").toTextBlockNode(),
                CodeBlockNode("javascript", expectedCodeLines.joinToString("\n")),
                TextNode("some another text").toTextBlockNode()
        ))

        assertEquals(expectedResult, actualResult)
    }
}