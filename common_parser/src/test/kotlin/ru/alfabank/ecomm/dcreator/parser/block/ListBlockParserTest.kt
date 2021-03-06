package ru.alfabank.ecomm.dcreator.parser.block

import ru.alfabank.ecomm.dcreator.nodes.BlockLayout
import ru.alfabank.ecomm.dcreator.nodes.ListsBLockNode
import ru.alfabank.ecomm.dcreator.nodes.TextNode
import ru.alfabank.ecomm.dcreator.nodes.UnOrderedListNode
import ru.alfabank.ecomm.dcreator.parser.MarkdownParser
import ru.alfabank.ecomm.dcreator.test.runTest
import ru.alfabank.ecomm.dcreator.utils.toTextBlockNode
import kotlin.test.Test
import kotlin.test.assertEquals

class ListBlockParserTest {
    private val parser = MarkdownParser()

    @Test
    fun test_inner_list() = runTest {
        val simpleList = sequenceOf(
            "text for line 1",
            "text for line 2",
            "",
            " - text for line 4",
            " - text for line 5",
            " - text for line 6",
            "",
            "text for line 8"
        )

        val (actualResult, _) = parser.parse(simpleList)

        val expectResult = BlockLayout(
            listOf(
                listOf(
                    TextNode("text for line 1"),
                    TextNode("text for line 2")
                ).toTextBlockNode(),
                ListsBLockNode(
                    listOf(
                        UnOrderedListNode(TextNode("text for line 4")),
                        UnOrderedListNode(TextNode("text for line 5")),
                        UnOrderedListNode(TextNode("text for line 6"))
                    )
                ),
                TextNode("text for line 8").toTextBlockNode()
            )
        )

        assertEquals(expectResult, actualResult)
    }

    @Test
    fun test_nested_lists() = runTest {
        val simpleList = sequenceOf(
            "- text for line 1",
            "  - text for line 2",
            "    - text for line 3",
            "       - text for line 4",
            "  - text for line 5",
            "- text for line 6"
        )
        val (actualResult, _) = parser.parse(simpleList)

        val expectResult = ListsBLockNode(
            listOf(
                UnOrderedListNode(
                    BlockLayout(
                        listOf(
                            TextNode("text for line 1").toTextBlockNode(),
                            ListsBLockNode(
                                listOf(
                                    UnOrderedListNode(
                                        BlockLayout(
                                            listOf(
                                                TextNode("text for line 2").toTextBlockNode(),
                                                ListsBLockNode(
                                                    listOf(
                                                        UnOrderedListNode(
                                                            BlockLayout(
                                                                listOf(
                                                                    TextNode("text for line 3").toTextBlockNode(),
                                                                    ListsBLockNode(
                                                                        listOf(
                                                                            UnOrderedListNode(TextNode("text for line 4"))
                                                                        )
                                                                    )
                                                                )
                                                            )
                                                        )
                                                    )
                                                )
                                            )
                                        )
                                    ),
                                    UnOrderedListNode(TextNode("text for line 5"))
                                )
                            )
                        )
                    )
                ),
                UnOrderedListNode(TextNode("text for line 6"))
            )
        )

        assertEquals(expectResult, actualResult)
    }
}