//package ru.alfabank.ecomm.dcreator.render
//
//import ru.alfabank.ecomm.dcreator.parser.MarkdownParser
//import ru.alfabank.ecomm.dcreator.render.process.HeaderProcessor
//import ru.alfabank.ecomm.dcreator.render.serialize.FreemarkerRender
//import ru.alfabank.ecomm.dcreator.render.serialize.NodeSerializer
//import java.io.File
//
//fun main(args: Array<String>) {
//    val inputDirectory = File("files/input")
//    val outputDirectory = File("files/output")
//
//    val thymeleafRender = FreemarkerRender("files/layout/freemarker")
//
//    for (srcFile in inputDirectory.walk()) {
//        if (!srcFile.isFile)
//            continue
//
//        if (srcFile.extension.toLowerCase() != "md") {
//            srcFile.copyTo(File(outputDirectory, srcFile.name), overwrite = true)
//            continue
//        }
//
//        val outputFile = File(outputDirectory, "${srcFile.nameWithoutExtension}.html")
//
//        if (outputFile.exists()) {
//            outputFile.delete()
//            outputFile.createNewFile()
//        }
//
//        val node = MarkdownParser().parse(srcFile)
//
//        val headerProcessor = HeaderProcessor()
//        val (result, replaceNodes) = headerProcessor.process(node)
//
//        val nodeSerializer = NodeSerializer(thymeleafRender, replaceNodes)
//
//        val preparedResult = result.mapValues { value ->
//            nodeSerializer.writeNodeToString(value.value)
//        }
//
//        val nodesData = thymeleafRender.render("index.ftlh", preparedResult)
//        outputFile.writeText(nodesData)
//    }
//}