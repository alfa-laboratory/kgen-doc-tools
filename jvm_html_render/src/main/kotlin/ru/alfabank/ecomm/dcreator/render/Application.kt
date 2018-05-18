package ru.alfabank.ecomm.dcreator.render

import kotlinx.coroutines.experimental.runBlocking
import java.io.File

fun main(args: Array<String>) = runBlocking {
    val inputDirectory = File("filesExample/input")
    val outputDirectory = File("filesExample/output/pages")

    val layoutPath = File("filesExample/layout")

    val documentGenerator = DocumentGenerator(
        inputDirectory,
        outputDirectory,
        layoutPath
    )

    for (file in inputDirectory.walk())
        documentGenerator.generateHtmlFromMd(file)
}