package ru.alfabank.ecomm.dcreator.server

import ru.alfabank.ecomm.dcreator.parser.MarkdownParser
import ru.alfabank.ecomm.dcreator.render.process.HeaderProcessor
import ru.alfabank.ecomm.dcreator.render.serialize.FreemarkerRender
import ru.alfabank.ecomm.dcreator.render.serialize.NodeSerializer
import java.io.File

class DocumentGenerator(
    private val inputDirectory: File,
    private val outputDirectory: File,
    layoutDirectory: File
) {
    private val freemarkerRender = FreemarkerRender(layoutDirectory)

    suspend fun generateHtmlFromMd(generateFile: File) {
        if (!generateFile.isFile)
            return

        if (generateFile.extension.toLowerCase() != "md") {
            generateFile.copyTo(File(outputDirectory, generateFile.name), overwrite = true)
            return
        }

        val relativePath = generateFile
            .parentFile
            .toRelativeString(inputDirectory).let {
                if (it.isEmpty())
                    ""
                else
                    "$it/"
            }

        val outputFile = File(outputDirectory, "$relativePath${generateFile.nameWithoutExtension}.html")

        outputFile.parentFile.apply {
            if (!exists()) mkdirs()
        }

        if (!outputFile.exists()) {
            outputFile.createNewFile()
        }

        val node =  MarkdownParser(inputDirectory).parse(generateFile)

        val headerProcessor = HeaderProcessor()
        val (result, replaceNodes) = headerProcessor.process(node)

        val nodeSerializer = NodeSerializer(freemarkerRender, replaceNodes)

        val preparedResult = result.mapValues { value ->
            nodeSerializer.writeNodeToString(value.value)
        }

        val nodesData = freemarkerRender.render("index.ftlh", preparedResult)
        outputFile.writeText(nodesData)
    }
}