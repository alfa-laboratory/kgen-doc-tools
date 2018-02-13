package ru.alfabank.ecomm.dcreator

import org.junit.jupiter.api.Test

class RegexTest {
    @Test
    fun test() {
        val column1 = "adsf ds 1425hklkjhas"
        val column2 = "$2)&!@dkhflsad 128"
        val column3 = "%3@#)FHLDSHF as @#*"

        val testString = sequenceOf(
                "| $column1 | $column2 | $column3 |",
                "  | $column1 | $column2 | $column3 |  ",
                "$column1 | $column2 | $column3",
                "  $column1 | $column2 | $column3  "
        )

        testString.forEachIndexed { index, line ->
            print("$index: ")
            val matcher = COLUMN_FIND_PATTERN.matcher(line)

            while (matcher.find()) {
                val column = matcher.group(COLUMN_GROUP)
                print("$column  :: ")
            }

            println()
        }

        val testString2 = sequenceOf(
                "| :---: | :--- | ----: |",
                " | :---: | :--- | ----: | ",
                " :---: | :--- | ----: ",
                ":---: | :--- | ----:"
        )

        println()

        testString2.forEachIndexed { index, line ->
            print("$index: ")
            val matcher = COLUMN_FIND_PATTERN.matcher(line)

            while (matcher.find()) {
                val column = matcher.group(COLUMN_GROUP)
                print("$column  && ")
            }

            println()
        }
    }

    companion object {
        private const val SEPARATOR = "\\|"
        private val SPACE_PATTERN = """
            [\s\t]
        """.trimIndent()

        private const val COLUMN_GROUP = "column"
        private const val LEFT_GROUP = "left"
        private const val RIGHT_GROUP = "right"

        private val END_PATTERN = """
            (($SPACE_PATTERN+$SEPARATOR)|($SPACE_PATTERN*$SEPARATOR?$SPACE_PATTERN*$))
            """.trimIndent()

        private val START_PATTERN = """
            ((^$SPACE_PATTERN*)|($SPACE_PATTERN+))
        """.trimIndent()

        private val COLUMN_PATTERN = """
            (?<$COLUMN_GROUP>[^\s\t][^$SEPARATOR]*)
        """.trimIndent()

        private val COLUMN_FIND_PATTERN = """
            $START_PATTERN$COLUMN_PATTERN$END_PATTERN
        """.trimIndent().toPattern()
    }
}