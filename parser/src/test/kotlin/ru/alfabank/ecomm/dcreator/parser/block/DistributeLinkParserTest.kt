package ru.alfabank.ecomm.dcreator.parser.block

import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test
import ru.alfabank.ecomm.dcreator.nodes.*
import ru.alfabank.ecomm.dcreator.parser.MarkdownParser

class DistributeLinkParserTest {
    private val parser = MarkdownParser()

    @Test
    fun `test distribute nodes`() {
        val src = """
            |  some big text [text link 1] [id1] before text [link2]
            |  text 2 ![image text] before text [link id1]
            |
            |       [id1]: <http://someurl.com> 'title 123'
            |       [link2]: <http://someurl2.com> (some title)
            |       [image text]: <http://someurl.com> "some another title"
            |       [link id1]: <http://someurl.com>
        """.trimMargin()

        val actualResult = parser.parse(src.split("\n").asSequence())

        val expectedResult = TextBlockNode(listOf(
                RowLayout(listOf(
                        TextNode("  some big text "),
                        LinkNode(TextNode("text link 1"), "http://someurl.com", "title 123"),
                        TextNode(" before text "),
                        LinkNode(TextNode("link2"), "http://someurl2.com", "some title")
                )),
                RowLayout(listOf(
                        TextNode("  text 2 "),
                        ImageLinkNode("image text", "http://someurl.com", "some another title"),
                        TextNode("before text "),
                        LinkNode(TextNode("link id1"), "http://someurl.com")
                ))
        ))

        assertEquals(expectedResult, actualResult)
    }
}