package ru.alfabank.ecomm.dcreator.parser.block

import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test
import ru.alfabank.ecomm.dcreator.nodes.BlockLayout
import ru.alfabank.ecomm.dcreator.nodes.TextNode
import ru.alfabank.ecomm.dcreator.parser.MarkdownParser
import ru.alfabank.ecomm.dcreator.utils.toTextBlockNode
import java.io.File

class IncludeBlockParserTest {

    @Test
    fun `test multiple files include`() {
        val parser = MarkdownParser(File("src/test/resources/multifiles"))

        val actual = parser.parse(File("src/test/resources/multifiles/file1.md"))

        val expected = BlockLayout(listOf(
                listOf(
                        TextNode("file 1 some text line 1"),
                        TextNode("file 1 some text line 2")
                ).toTextBlockNode(),
                listOf(
                        TextNode("file 2 some text line 1"),
                        TextNode("file 2 some text line 2")
                ).toTextBlockNode(),
                listOf(
                        TextNode("file 3 some text line 1"),
                        TextNode("file 3 some text line 2")
                ).toTextBlockNode(),
                listOf(
                        TextNode("file 3 some text line 3"),
                        TextNode("file 3 some text line 4")
                ).toTextBlockNode(),
                listOf(
                        TextNode("file 2 some text line 3"),
                        TextNode("file 2 some text line 4")
                ).toTextBlockNode(),
                listOf(
                        TextNode("file 1 some text line 3"),
                        TextNode("file 1 some text line 4")
                ).toTextBlockNode()
        ))

        assertEquals(expected, actual)
    }
}