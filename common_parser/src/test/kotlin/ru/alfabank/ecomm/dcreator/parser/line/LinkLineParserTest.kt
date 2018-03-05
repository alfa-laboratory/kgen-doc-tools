package ru.alfabank.ecomm.dcreator.parser.line

import ru.alfabank.ecomm.dcreator.nodes.LinkNode
import ru.alfabank.ecomm.dcreator.nodes.TextNode
import ru.alfabank.ecomm.dcreator.parser.MarkdownParser

class LinkLineParserTest {
    private val parser = MarkdownParser()
    private val lineParser = parser.lineParser

    @Test
    fun `simple links test`() {
        val linkResult = lineParser.parse("[some id фывафы] (http://someuri.com  \"title\")")
        Assertions.assertEquals(LinkNode(TextNode("some id фывафы"), "http://someuri.com", "title"), linkResult)

        val linkResult2 = lineParser.parse("[some id] (http://someuri.com)")
        Assertions.assertEquals(LinkNode(TextNode("some id"), "http://someuri.com"), linkResult2)

        val linkResultWithSpaces = lineParser.parse("[some id] (/static/images/manual/Оплата с помощью связки на платежной странице.png)")
        Assertions.assertEquals(LinkNode(TextNode("some id"), "/static/images/manual/Оплата с помощью связки на платежной странице.png"), linkResultWithSpaces)

        val linkResult3 = lineParser.parse("[some id][id1]")
        val expectNode3 = LinkNode(TextNode("some id"))
        Assertions.assertEquals(expectNode3, linkResult3)
        Assertions.assertEquals(parser.distributeNodes["id1"]?.first(), expectNode3)

        val linkResult4 = lineParser.parse("[some id] [id2]")
        val expectNode4 = LinkNode(TextNode("some id"))
        Assertions.assertEquals(expectNode4, linkResult4)
        Assertions.assertEquals(parser.distributeNodes["id2"]?.first(), expectNode4)

        val linkResult5 = lineParser.parse("[some id 123F] [id adsf3]")
        val expectNode5 = LinkNode(TextNode("some id 123F"))
        Assertions.assertEquals(expectNode5, linkResult5)
        Assertions.assertEquals(parser.distributeNodes["id adsf3"]?.first(), expectNode5)

        val linkResult6 = lineParser.parse("[some another id] []")
        val expectNode6 = LinkNode(TextNode("some another id"))
        Assertions.assertEquals(expectNode6, linkResult6)
        Assertions.assertEquals(parser.distributeNodes["some another id"]?.first(), expectNode6)
        lineParser.parse("[some another id] []")
        Assertions.assertEquals(parser.distributeNodes["some another id"], listOf(expectNode6, expectNode6))

        val linkResult7 = lineParser.parse("[only text id]")
        val expectNode7 = LinkNode(TextNode("only text id"))
        Assertions.assertEquals(expectNode7, linkResult7)
        Assertions.assertEquals(expectNode7, parser.distributeNodes["only text id"]?.first())
    }
}