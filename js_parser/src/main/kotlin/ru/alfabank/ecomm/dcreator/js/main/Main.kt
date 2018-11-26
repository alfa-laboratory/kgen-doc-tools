package ru.alfabank.ecomm.dcreator.js.main

import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.promise
import ru.alfabank.ecomm.dcreator.common.File
import ru.alfabank.ecomm.dcreator.parser.MarkdownParser

external val require: dynamic
external val module: dynamic

fun main(args: Array<String>) {
    if (require.main === module) {
        mainFun()
    }
}

fun mainFun() = GlobalScope.promise {
    println("Hello JavaScript!")

    val fileToParser = File("./common_parser/src/test/resources/complex_example.md")

    val markdownParser = MarkdownParser(File("."))
    val parseResult = markdownParser.parse(fileToParser)
    println(parseResult)
}