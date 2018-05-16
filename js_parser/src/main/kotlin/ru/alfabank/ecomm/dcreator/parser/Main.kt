package ru.alfabank.ecomm.dcreator.parser

import kotlinx.coroutines.experimental.promise
import ru.alfabank.ecomm.dcreator.common.File

fun main(args: Array<String>) {
    println("Hello JavaScript!")

    promise {
        val fileToParser = File("./common_parser/src/test/resources/complex_example.md")

        val markdownParser = MarkdownParser(File("."))
        val parseResult = markdownParser.parse(fileToParser)
        println(parseResult)
    }
}