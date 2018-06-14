package ru.alfabank.ecomm.dcreator.parser.block

import ru.alfabank.ecomm.dcreator.nodes.*
import ru.alfabank.ecomm.dcreator.parser.MarkdownParser
import ru.alfabank.ecomm.dcreator.test.runTest
import kotlin.test.Test
import kotlin.test.assertEquals

class SpecialNodeBlockParserTest {
    private val parser = MarkdownParser()

    @Test
    fun `test_special_parser`() = runTest {
        val src = sequenceOf(
            """
                :::layout [some layout]
            """.trimIndent(),
            """
                :::title [some title]
            """.trimIndent(),
            """
                :::include [some filename1.md] (some "text1" "some text2")
            """.trimIndent(),
            """
                :::include [some filename2.md] (some text2)
            """.trimIndent(),
            """
                :::version [1.0.32]
            """.trimIndent(),
            """
                :::update [2018-06-14 16:13:00]
            """.trimIndent(),
            """
                :::otherTag [some otherTag] (some otherTag value)
            """.trimIndent()
        )

//        [LayoutServiceNode(layout=some layout), TitleServiceNode(title=some title), SimpleServiceNode(name=some include","some include value, value=null), SimpleServiceNode(name=some otherTag","some otherTag value, value=null)]

        val (result: BlockNode, serviceNodes) = parser.parse(src)

        val expectedServiceNodes = listOf(
            LayoutServiceNode("some layout"),
            TitleServiceNode("some title"),
            IncludeServiceNode("some filename1.md", "some \"text1\"", "some text2"),
            IncludeServiceNode("some filename2.md", "some text2", null),
            VersionServiceNode("1.0.32"),
            LastUpdateServiceNode("2018-06-14 16:13:00"),
            SimpleServiceNode("some otherTag", "some otherTag value", null)
        )

        assertEquals(result, BlockLayout())
        assertEquals(expectedServiceNodes.size, serviceNodes.size)

        expectedServiceNodes.forEachIndexed { index, expectedNode ->
            assertEquals(expectedNode, serviceNodes[index])
        }
    }

}
