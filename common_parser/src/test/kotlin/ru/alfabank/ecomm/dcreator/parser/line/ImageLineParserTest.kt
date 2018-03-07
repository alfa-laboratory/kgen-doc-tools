package ru.alfabank.ecomm.dcreator.parser.line

import ru.alfabank.ecomm.dcreator.common.Test
import ru.alfabank.ecomm.dcreator.common.assertEquals
import ru.alfabank.ecomm.dcreator.nodes.ImageLinkNode
import ru.alfabank.ecomm.dcreator.parser.MarkdownParser

class ImageLineParserTest {
    private val parser = MarkdownParser()
    private val lineParser = parser.lineParser

    @Test
    fun `simple image test`() {
        val linkResult = lineParser.parse("![some id фывафы] (http://someuri.com  \"title\")")
        assertEquals(ImageLinkNode("some id фывафы", "http://someuri.com", "title"), linkResult)

        val linkResult2 = lineParser.parse("![some id] (http://someuri.com)")
        assertEquals(ImageLinkNode("some id", "http://someuri.com"), linkResult2)

        val linkResultWithSpaces = lineParser.parse("![some id] (/static/images/manual/Оплата с помощью связки на платежной странице.png)")
        assertEquals(ImageLinkNode("some id", "/static/images/manual/Оплата с помощью связки на платежной странице.png"), linkResultWithSpaces)

        val linkResult3 = lineParser.parse("![some id][id1]")
        val expectNode3 = ImageLinkNode("some id")
        assertEquals(expectNode3, linkResult3)
        assertEquals(parser.distributeNodes["id1"]?.first(), expectNode3)

        val linkResult4 = lineParser.parse("![some id] [id2]")
        val expectNode4 = ImageLinkNode("some id")
        assertEquals(expectNode4, linkResult4)
        assertEquals(parser.distributeNodes["id2"]?.first(), expectNode4)

        val linkResult5 = lineParser.parse("![some id 123F] [id adsf3]")
        val expectNode5 = ImageLinkNode("some id 123F")
        assertEquals(expectNode5, linkResult5)
        assertEquals(parser.distributeNodes["id adsf3"]?.first(), expectNode5)

        val linkResult6 = lineParser.parse("![some another id] []")
        val expectNode6 = ImageLinkNode("some another id")
        assertEquals(expectNode6, linkResult6)
        assertEquals(parser.distributeNodes["some another id"]?.first(), expectNode6)
        lineParser.parse("![some another id] []")
        assertEquals(parser.distributeNodes["some another id"], listOf(expectNode6, expectNode6))

        val linkResult7 = lineParser.parse("![only text id]")
        val expectNode7 = ImageLinkNode("only text id")
        assertEquals(expectNode7, linkResult7)
        assertEquals(expectNode7, parser.distributeNodes["only text id"]?.first())
    }
}