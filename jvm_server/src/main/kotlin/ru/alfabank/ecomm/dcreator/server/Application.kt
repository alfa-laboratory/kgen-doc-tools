package ru.alfabank.ecomm.dcreator.server

import com.fasterxml.jackson.databind.ObjectMapper
import com.fasterxml.jackson.module.kotlin.readValue
import com.fasterxml.jackson.module.kotlin.registerKotlinModule
import io.ktor.application.call
import io.ktor.application.install
import io.ktor.content.files
import io.ktor.content.static
import io.ktor.features.ContentNegotiation
import io.ktor.http.HttpStatusCode
import io.ktor.jackson.jackson
import io.ktor.request.receiveText
import io.ktor.response.respond
import io.ktor.routing.delete
import io.ktor.routing.get
import io.ktor.routing.post
import io.ktor.routing.routing
import io.ktor.server.engine.embeddedServer
import io.ktor.server.netty.Netty
import ru.alfabank.ecomm.dcreator.parser.MarkdownParser
import ru.alfabank.ecomm.dcreator.render.process.HeaderProcessor
import ru.alfabank.ecomm.dcreator.render.serialize.FreemarkerRender
import ru.alfabank.ecomm.dcreator.render.serialize.NodeSerializer
import ru.alfabank.ecomm.dcreator.server.responses.FileInfo
import ru.alfabank.ecomm.dcreator.server.responses.FileType
import ru.alfabank.ecomm.dcreator.server.responses.FilesResponse
import ru.alfabank.ecomm.dcreator.server.responses.StatusResponse
import java.io.File
import java.io.FileNotFoundException
import java.nio.file.Files
import java.time.LocalTime

data class NewFileRequest(val path: String, val type: String)

data class RenameRequest(val path: String, val name: String)

val objectMapper = ObjectMapper()
        .registerKotlinModule()

fun main(args: Array<String>) {
    val inputDirectory = File("files/input")
    val outputDirectory = File("files/output/pages")

    val freemarkerRender = FreemarkerRender("files/layout/freemarker")

    fun generateHtmlFromMd(generateFile: File) {
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

        val node = MarkdownParser(inputDirectory).parse(generateFile)

        val headerProcessor = HeaderProcessor()
        val (result, replaceNodes) = headerProcessor.process(node)

        val nodeSerializer = NodeSerializer(freemarkerRender, replaceNodes)

        val preparedResult = result.mapValues { value ->
            nodeSerializer.writeNodeToString(value.value)
        }

        val nodesData = freemarkerRender.render("index.ftlh", preparedResult)
        outputFile.writeText(nodesData)
    }

    embeddedServer(Netty, 8080) {
        install(ContentNegotiation) {
            jackson {
                writerWithDefaultPrettyPrinter()
            }
        }

//        install(CORS) {
//            method(HttpMethod.Options)
//            anyHost()
//        }

        routing {
            static("node_modules") {
                files("files/output/node_modules")
            }

            static("static") {
                files("files/output/static")
            }

            static("pages") {
                files("files/output/pages")
            }

            static("") {
                files("jvm_server/public")
            }

            get("files") {
                val path = call.parameters["path"] ?: ""

                val folder = File(inputDirectory, path)
                if (folder.exists() && folder.isDirectory) {
                    val files = folder.listFiles()?.map {
                        when {
                            it.isFile -> FileInfo(it.name, FileType.File)
                            it.isDirectory -> FileInfo(it.name, FileType.Folder)
                            else -> throw RuntimeException("invalid type")
                        }
                    } ?: emptyList()

                    call.respond(FilesResponse(true, files))
                } else {
                    call.respond(StatusResponse(false))
                }
            }

            get("file") {
                val fileName = call.parameters["path"]!!
                call.respond(File(inputDirectory, fileName).readText())
            }

            post("file") {
                println("${LocalTime.now()} try to update file..")

                val fileName = call.parameters["path"]!!
                val updateFile = File(inputDirectory, fileName)

                if (!updateFile.exists())
                    throw FileNotFoundException(fileName)

                val data = call.receiveText()
                updateFile.writeText(data)

                println("${LocalTime.now()} success")

                println("${LocalTime.now()} run md reload..")
                generateHtmlFromMd(updateFile)
                println("${LocalTime.now()} success")

                call.respond(HttpStatusCode.OK)
            }

            delete("file") {
                println("${LocalTime.now()} try to delete file..")
                val fileName = call.parameters["path"]!!

                val deleteFile = File(inputDirectory, fileName)

                if (!deleteFile.exists())
                    throw FileNotFoundException(fileName)

                val result = deleteFile.fullyDelete()

                call.respond(StatusResponse(result))
            }

            post("createFile") {
                val request = call.receiveText()
                val (fileName, type) = objectMapper.readValue<NewFileRequest>(request)

                val newFile = File(inputDirectory, fileName)
                if (newFile.exists())
                    throw FileNotFoundException(fileName)

                val result = when (type) {
                    "file" -> newFile.createNewFile()
                    "folder" -> newFile.mkdir()
                    else -> false
                }

                call.respond(StatusResponse(result))
            }

            post("renameFile") {
                val request = call.receiveText()
                val (path, newName) = objectMapper.readValue<RenameRequest>(request)

                val file = File(inputDirectory, path)
                if (!file.exists())
                    throw FileNotFoundException(file.absolutePath)

                val newFile = File(file.parent, newName)

                Files.move(file.toPath(), newFile.toPath())

                call.respond(StatusResponse(true))
            }
        }
    }.start(wait = true)
}

private fun File.fullyDelete(): Boolean {
    listFiles()?.map { file ->
        file.fullyDelete()
    }

    return delete()
}