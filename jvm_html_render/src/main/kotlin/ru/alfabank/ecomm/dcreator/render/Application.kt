package ru.alfabank.ecomm.dcreator.render

import kotlinx.coroutines.runBlocking
import java.io.File

fun main(args: Array<String>) = runBlocking {
    val fileDirectory = "filesExample"

    val inputDirectory = File("$fileDirectory/input")
    val outputDirectory = File("$fileDirectory/output")
    val layoutPath = File("$fileDirectory/layout")

    val documentGenerator = DocumentGenerator(
        inputDirectory,
        outputDirectory,
        layoutPath
    )

    for (file in inputDirectory.walk()) {
        try {
            documentGenerator.generateHtmlFromMd(file)
        } catch (e: Exception) {
            println("ERROR: while try to parse file $file")
            e.printStackTrace()
        }
    }
}