package ru.alfabank.ecomm.dcreator.parser.line

import ru.alfabank.ecomm.dcreator.common.File
import ru.alfabank.ecomm.dcreator.nodes.BoldNode
import ru.alfabank.ecomm.dcreator.nodes.Node
import ru.alfabank.ecomm.dcreator.nodes.RowLayout
import ru.alfabank.ecomm.dcreator.nodes.TextNode
import ru.alfabank.ecomm.dcreator.parser.MarkdownParser
import ru.alfabank.ecomm.dcreator.test.runTest
import kotlin.test.Test
import kotlin.test.assertEquals

class IncludeLineParserTest {
    private val parser = MarkdownParser(File("../common_parser/src/test/resources/includeLineTest"))
    private val lineParser = parser.lineParser

    @Test
    fun test_line_include() = runTest {
        val parseResult: Node = lineParser.parse("**some text** ::include \"test.md\" **some after text**")
        val expectedResult = RowLayout(
            nodes = listOf(
                BoldNode(node = TextNode(text = "some text")),
                TextNode(text = " some text from test.md "),
                BoldNode(node = TextNode(text = "some after text"))
            )
        )

        assertEquals(
            expectedResult, parseResult
        )
    }
}