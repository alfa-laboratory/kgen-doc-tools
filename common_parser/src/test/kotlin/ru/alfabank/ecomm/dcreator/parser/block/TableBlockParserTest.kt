package ru.alfabank.ecomm.dcreator.parser.block

import ru.alfabank.ecomm.dcreator.nodes.*
import ru.alfabank.ecomm.dcreator.parser.MarkdownParser
import ru.alfabank.ecomm.dcreator.test.runTest
import kotlin.test.Test
import kotlin.test.assertEquals

class TableBlockParserTest {
    val parser = MarkdownParser()

    @Test
    fun test_table_with_start_end_symbols() = runTest {
        val lines = sequenceOf(
            "| Plugin | README | column number 3 | column number 4 |",
            "| :----- | :----: | ----: | ---- |",
            "| Dropbox | text for dropbox | end column1 | end column2 |",
            "| Dropbox2 | text for dropbox2 | end column12 | end column22 |"
        )

        val (actual, _) = parser.parse(lines)

        val expect = TableBlockNode(
            header = listOf(
                TextNode("Plugin"),
                TextNode("README"),
                TextNode("column number 3"),
                TextNode("column number 4 ")
            ),
            content = listOf(
                listOf(
                    TextNode("Dropbox"),
                    TextNode("text for dropbox"),
                    TextNode("end column1"),
                    TextNode("end column2 ")
                ),
                listOf(
                    TextNode("Dropbox2"),
                    TextNode("text for dropbox2"),
                    TextNode("end column12"),
                    TextNode("end column22 ")
                )
            ),
            modifier = listOf(
                ColumnAlignModifier(Direction.Left),
                ColumnAlignModifier(Direction.Center),
                ColumnAlignModifier(Direction.Right),
                EmptyColumnModifier
            )
        )

        assertEquals(expect, actual)
    }

    @Test
    fun test_table_without_start_end_symbols() = runTest {
        val lines = sequenceOf(
            " Plugin | README ",
            " :----- | :----: ",
            " Dropbox | text for dropbox "
        )

        val (actual, _) = parser.parse(lines)

        val expect = TableBlockNode(
            header = listOf(
                TextNode("Plugin"),
                TextNode("README ")
            ),
            content = listOf(
                listOf(
                    TextNode("Dropbox"),
                    TextNode("text for dropbox ")
                )
            ),
            modifier = listOf(
                ColumnAlignModifier(Direction.Left),
                ColumnAlignModifier(Direction.Center)
            )
        )

        assertEquals(expect, actual)
    }

    @Test
    fun table_table_with_footer() = runTest {
        val lines = sequenceOf(
            " Plugin | README ",
            " :----- | :----: ",
            " Dropbox | text for dropbox ",
            " ---- | ----- ",
            " footer text 1 | footer text 2 "
        )

        val (actual, _) = parser.parse(lines)

        val expect = TableBlockNode(
            header = listOf(
                TextNode("Plugin"),
                TextNode("README ")
            ),
            content = listOf(
                listOf(
                    TextNode("Dropbox"),
                    TextNode("text for dropbox ")
                )
            ),
            footer = listOf(
                TextNode("footer text 1"),
                TextNode("footer text 2 ")
            ),
            modifier = listOf(
                ColumnAlignModifier(Direction.Left),
                ColumnAlignModifier(Direction.Center)
            )
        )

        assertEquals(expect, actual)
    }
}