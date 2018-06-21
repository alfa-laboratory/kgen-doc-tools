package ru.alfabank.ecomm.dcreator.parser.line

import ru.alfabank.ecomm.dcreator.common.File
import ru.alfabank.ecomm.dcreator.nodes.Node
import ru.alfabank.ecomm.dcreator.nodes.TextNode
import ru.alfabank.ecomm.dcreator.parser.MarkdownParser
import ru.alfabank.ecomm.dcreator.test.runTest
import kotlin.test.BeforeTest
import kotlin.test.Test
import kotlin.test.assertEquals

class IncludeLineParserTest {
    private val parser = MarkdownParser(File("../common_parser/src/test/resources/includeLineTest"))
    private val lineParser = parser.lineParser

    @BeforeTest
    fun before() {
        parser.lineParsers.clear()
        parser.lineParsers.add(IncludeLineParser(parser))
    }

    @Test
    fun test_line_include() = runTest {
        val parseResult: Node = lineParser.parse("::include \"test.md\"")
        val expectedResult = TextNode("some text from test.md")

        assertEquals(expectedResult, parseResult)
    }
}