package ru.alfabank.ecomm.dcreator.server

import com.fasterxml.jackson.databind.ObjectMapper
import com.fasterxml.jackson.module.kotlin.readValue
import com.fasterxml.jackson.module.kotlin.registerKotlinModule
import io.ktor.application.ApplicationCall
import io.ktor.application.call
import io.ktor.content.files
import io.ktor.content.resources
import io.ktor.content.static
import io.ktor.http.ContentType
import io.ktor.http.HttpStatusCode
import io.ktor.request.receiveText
import io.ktor.response.respond
import io.ktor.response.respondFile
import io.ktor.response.respondText
import io.ktor.routing.Routing
import io.ktor.routing.delete
import io.ktor.routing.get
import io.ktor.routing.post
import ru.alfabank.ecomm.dcreator.render.DocumentGenerator
import ru.alfabank.ecomm.dcreator.server.responses.*
import ru.alfabank.ecomm.dcreator.server.utils.doAndDisplayDiff
import java.io.File
import java.io.FileNotFoundException
import java.nio.file.Files
import java.time.LocalTime

class RenderRouterConfiguration(
    private val workDir: File,
    private val inputDirectory: File,
    private val documentGenerator: DocumentGenerator,
    private val mode: ApplicationMode
) {
    fun config(): Routing.() -> Unit = {
        static("static") {
            files(File(workDir, "output/static"))
        }

        static("pages") {
            files(File(workDir, "output/pages"))
        }

        when (mode) {
            ApplicationMode.TEST -> {
                val indexFile = File(mode.publicFolder, "index.html")

                static("") {
                    files(mode.publicFolder)
                    get {
                        call.respondFile(indexFile)
                    }
                }

                get("portal") {
                    call.respondFile(indexFile)
                }

                get("portal/*") {
                    call.respondFile(indexFile)
                }
            }
            ApplicationMode.PROD -> {
                static("") {
                    resources(staticFolder)
                    get {
                        call.respondText(text = staticIndexFileText, contentType = contentType)
                    }
                }

                get("portal") {
                    call.respondText(text = staticIndexFileText, contentType = contentType)
                }

                get("portal/*") {
                    call.respondText(text = staticIndexFileText, contentType = contentType)
                }
            }
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
            val updateFile = doAndDisplayDiff("file write") {
                val fileName = call.parameters["path"]!!
                val _updateFile = File(inputDirectory, fileName)

                if (!_updateFile.exists())
                    throw FileNotFoundException(fileName)

                val data = call.receiveText()

                _updateFile.writeText(data)
                return@doAndDisplayDiff _updateFile
            }

            doAndDisplayDiff("run md reload") {
                documentGenerator.generateHtmlFromMd(updateFile)
            }

            call.respond(HttpStatusCode.OK)
        }

        delete("file") {
            println("${LocalTime.now()} try to delete file..")
            val fileName = call.parameters["path"]!!

            val deleteFile = File(inputDirectory, fileName)

            if (!deleteFile.exists())
                throw FileNotFoundException(fileName)

            val result = deleteFile.deleteRecursively()

            call.respond(StatusResponse(result))
        }

        post("createFile") {
            val (fileName, type) = call.receiveRequest<NewFileRequest>()

            val newFile = File(inputDirectory, fileName)
            if (newFile.exists())
                throw FileNotFoundException(fileName)

            val result = when (type) {
                "file" -> newFile.createNewFile()
                "staticFolder" -> newFile.mkdir()
                else -> false
            }

            call.respond(StatusResponse(result))
        }

        post("renameFile") {
            val (path, newName) = call.receiveRequest<RenameRequest>()

            val file = File(inputDirectory, path)
            if (!file.exists())
                throw FileNotFoundException(file.absolutePath)

            val newFile = File(file.parent, newName)

            Files.move(file.toPath(), newFile.toPath())

            call.respond(StatusResponse(true))
        }
    }

    private suspend inline fun <reified T : Any> ApplicationCall.receiveRequest(): T {
        val request = this.receiveText()
        return objectMapper.readValue(request)
    }

    companion object {
        private val objectMapper = ObjectMapper()
            .registerKotlinModule()

        private const val staticFolder = "static"
        private val staticIndexFileText = ClassLoader::class.java.getResourceAsStream("/$staticFolder/index.html")
            .reader().readText()

        private val contentType = ContentType.parse("text/html; charset=UTF-8")
    }
}