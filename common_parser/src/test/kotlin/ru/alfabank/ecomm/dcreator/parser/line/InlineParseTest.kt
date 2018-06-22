package ru.alfabank.ecomm.dcreator.parser.line

import ru.alfabank.ecomm.dcreator.nodes.*
import ru.alfabank.ecomm.dcreator.parser.MarkdownParser
import ru.alfabank.ecomm.dcreator.test.runTest
import kotlin.test.Test
import kotlin.test.assertEquals

class SimpleTest {
    private val parser = MarkdownParser()
    private val lineParser = parser.lineParser

    @Test
    fun simple_bounds_test() = runTest {
        val testItalic = lineParser.parse("_регистрационная_")
        assertEquals(ItalicNode(TextNode("регистрационная")), testItalic)

        val testItalic2 = lineParser.parse("*регистрационная*")
        assertEquals(ItalicNode(TextNode("регистрационная")), testItalic2)

        val testItalic3 = lineParser.parse("**регистрационная*")
        assertEquals(ItalicNode(TextNode("*регистрационная")), testItalic3)

        val testItalic4 = lineParser.parse("**регистрационная*saf")
        assertEquals(RowLayout(listOf(ItalicNode(TextNode("*регистрационная")), TextNode("saf"))), testItalic4)

        val testBold = lineParser.parse("__регистрационная__")
        assertEquals(BoldNode(TextNode("регистрационная")), testBold)

        val testBold2 = lineParser.parse("**регистрационная**")
        assertEquals(BoldNode(TextNode("регистрационная")), testBold2)

        //combine
        val testCombine = lineParser.parse("**_регистрационная_**")
        assertEquals(BoldNode(ItalicNode(TextNode("регистрационная"))), testCombine)

        val testCombine2 = lineParser.parse("** _регистрационная_**")
        assertEquals(BoldNode(RowLayout(listOf(TextNode(" "), ItalicNode(TextNode("регистрационная"))))), testCombine2)

        val testCombine3 = lineParser.parse("** _регистрационная _**")
        assertEquals(BoldNode(RowLayout(listOf(TextNode(" "), ItalicNode(TextNode("регистрационная "))))), testCombine3)

        val testCombine4 = lineParser.parse("** _ регистрационная _**")
        assertEquals(
            BoldNode(RowLayout(listOf(TextNode(" "), ItalicNode(TextNode(" регистрационная "))))),
            testCombine4
        )

        //combine reverse
        val testCombineReverse = lineParser.parse("_**регистрационная**_")
        assertEquals(ItalicNode(BoldNode(TextNode("регистрационная"))), testCombineReverse)

        val testCombineReverse2 = lineParser.parse("_ **регистрационная**_")
        assertEquals(
            ItalicNode(RowLayout(listOf(TextNode(" "), BoldNode(TextNode("регистрационная"))))),
            testCombineReverse2
        )

        val testCombineReverse3 = lineParser.parse("_ **регистрационная** _")
        assertEquals(
            ItalicNode(RowLayout(listOf(TextNode(" "), BoldNode(TextNode("регистрационная")), TextNode(" ")))),
            testCombineReverse3
        )

        val testCombineReverse4 = lineParser.parse("_ ** регистрационная **_")
        assertEquals(
            ItalicNode(RowLayout(listOf(TextNode(" "), BoldNode(TextNode(" регистрационная "))))),
            testCombineReverse4
        )

        //underline
        val underline = lineParser.parse("++some text++")
        assertEquals(UnderlineNode(TextNode("some text")), underline)

        //strikethough
        val strikethrough = lineParser.parse("~~some text~~")
        assertEquals(StrikethroughNode(TextNode("some text")), strikethrough)

    }

    @Test
    fun simple_code_tests() = runTest {
        val testCode = lineParser.parse("`some code `")
        assertEquals(CodeSampleNode("some code "), testCode)

        val testCode2 = lineParser.parse("` **some code** `")
        assertEquals(CodeSampleNode(" **some code** "), testCode2)

        val testCode3 = lineParser.parse("some *text*` _ **some code** _ ` mo**re other** text")
        assertEquals(
            RowLayout(
                listOf(
                    TextNode("some "),
                    ItalicNode(TextNode("text")),
                    CodeSampleNode(" _ **some code** _ "),
                    TextNode(" mo"),
                    BoldNode(TextNode("re other")),
                    TextNode(" text")
                )
            ), testCode3
        )
    }

    @Test
    fun simple_html_tests() = runTest {
        val testHtml = lineParser.parse("<tr class=\"clazz1\"> text</tr>")
        assertEquals(HTMLNode("<tr class=\"clazz1\"> text</tr>"), testHtml)

        val testHtmlA = lineParser.parse("<a href=\"http://localhost\">text</a>")
        assertEquals(HTMLNode("<a href=\"http://localhost\">text</a>"), testHtmlA)

        val testHtmlComplex = lineParser.parse("some text **boldText** <tr class=\"clazz1\"> **text*</tr> endText")
        assertEquals(
            RowLayout(
                listOf(
                    TextNode("some text "),
                    BoldNode(TextNode("boldText")),
                    TextNode(" "),
                    HTMLNode("<tr class=\"clazz1\"> **text*</tr>"),
                    TextNode(" endText")
                )
            ), testHtmlComplex
        )

        val testHtmlComplex2 = lineParser.parse("some text **boldText** <tr class=\"clazz1\" **text*</tr> endText")
        assertEquals(
            RowLayout(
                listOf(
                    TextNode("some text "),
                    BoldNode(TextNode("boldText")),
                    TextNode(" <tr class=\"clazz1\" "),
                    ItalicNode(TextNode("*text")),
                    TextNode("</tr> endText")
                )
            ), testHtmlComplex2
        )

        val testHtmlComplex3 = lineParser.parse("some text **boldText** <a class=\"clazz1\" **text*</a> endText")
        assertEquals(
            RowLayout(
                listOf(
                    TextNode("some text "),
                    BoldNode(TextNode("boldText")),
                    TextNode(" <a class=\"clazz1\" "),
                    ItalicNode(TextNode("*text")),
                    TextNode("</a> endText")
                )
            ), testHtmlComplex3
        )
    }

    @Test
    fun complex_links_test_1() = runTest {
        val complex = """
            [![N|Solid](https://cldup.com/dTxpPi9lDf.thumb.png)](https://nodesource.com/products/nsolid "some title")
        """.trimIndent()

        val actual = lineParser.parse(complex)
        val expected = LinkNode(
            ImageLinkNode("N|Solid", "https://cldup.com/dTxpPi9lDf.thumb.png"),
            "https://nodesource.com/products/nsolid",
            "some title"
        )

        assertEquals(expected, actual)
    }

    @Test
    fun complex_links_test_2() = runTest {
        val complex = """
            some begin text[**asdfasd** ![N|Solid](https://cldup.com/dTxpPi9lDf.thumb.png)](https://nodesource.com/products/nsolid "some title")
        """.trimIndent()

        val actual = lineParser.parse(complex)
        val expected = RowLayout(
            listOf(
                TextNode("some begin text"),
                LinkNode(
                    RowLayout(
                        listOf(
                            BoldNode(TextNode("asdfasd")),
                            TextNode(" "),
                            ImageLinkNode("N|Solid", "https://cldup.com/dTxpPi9lDf.thumb.png")
                        )
                    ),
                    "https://nodesource.com/products/nsolid",
                    "some title"
                )
            )
        )

        assertEquals(expected, actual)
    }


}