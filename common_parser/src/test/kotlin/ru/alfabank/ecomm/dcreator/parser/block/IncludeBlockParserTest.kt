package ru.alfabank.ecomm.dcreator.parser.block

import ru.alfabank.ecomm.dcreator.common.File
import ru.alfabank.ecomm.dcreator.common.Test
import ru.alfabank.ecomm.dcreator.common.assertEquals
import ru.alfabank.ecomm.dcreator.nodes.BlockLayout
import ru.alfabank.ecomm.dcreator.nodes.TextNode
import ru.alfabank.ecomm.dcreator.parser.MarkdownParser
import ru.alfabank.ecomm.dcreator.utils.toTextBlockNode

class IncludeBlockParserTest {

    @Test
    fun `test multiple files include`() {
        val parser = MarkdownParser(File("../common_parser/src/test/resources/multifiles"))

        val actual = parser.parse(File("../common_parser/src/test/resources/multifiles/file1.md"))

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