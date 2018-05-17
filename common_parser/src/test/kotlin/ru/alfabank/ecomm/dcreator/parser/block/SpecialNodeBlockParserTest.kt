package ru.alfabank.ecomm.dcreator.parser.block

import ru.alfabank.ecomm.dcreator.nodes.*
import ru.alfabank.ecomm.dcreator.parser.MarkdownParser
import ru.alfabank.ecomm.dcreator.test.runTest
import kotlin.test.Test
import kotlin.test.assertEquals

class SpecialNodeBlockParserTest {
    private val parser = MarkdownParser()

    @Test
    fun createDeployment() = runTest {
        val src = sequenceOf(
            """
                :::layout "some layout"
            """.trimIndent(),
            """
                :::title "some title"
            """.trimIndent(),
            """
                :::include "some include","some include value"
            """.trimIndent(),
            """
                :::otherTag "some otherTag","some otherTag value"
            """.trimIndent()
        )

//        [LayoutServiceNode(layout=some layout), TitleServiceNode(title=some title), SimpleServiceNode(name=some include","some include value, value=null), SimpleServiceNode(name=some otherTag","some otherTag value, value=null)]

        val (result: BlockNode, serviceNodes) = parser.parse(src)

        val expectedServiceNodes = listOf(
            LayoutServiceNode("some layout"),
            TitleServiceNode("some title"),
            IncludeServiceNode("some include", "some include value"),
            SimpleServiceNode("some otherTag", "some otherTag value")
        )

        assertEquals(result, BlockLayout())
        assertEquals(expectedServiceNodes, serviceNodes)
    }

}
