package ru.alfabank.ecomm.dcreator.parser.block

import ru.alfabank.ecomm.dcreator.nodes.BlockLayout
import ru.alfabank.ecomm.dcreator.nodes.CodeBlockNode
import ru.alfabank.ecomm.dcreator.nodes.TextNode
import ru.alfabank.ecomm.dcreator.parser.MarkdownParser
import ru.alfabank.ecomm.dcreator.test.runTest
import ru.alfabank.ecomm.dcreator.utils.toTextBlockNode
import kotlin.test.Test
import kotlin.test.assertEquals

class CodeBlockTest {
    private val parser = MarkdownParser()

    @Test
    fun test_parsing_of_code_block() = runTest {
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
    fun test_parsing_of_code_block_without_language_name() = runTest {
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
    fun test_parsing_of_code_block_with_text() = runTest {
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
        val expectedResult = BlockLayout(
            listOf(
                TextNode("some text").toTextBlockNode(),
                CodeBlockNode("javascript", expectedCodeLines.joinToString("\n")),
                TextNode("some another text").toTextBlockNode()
            )
        )

        assertEquals(expectedResult, actualResult)
    }
}