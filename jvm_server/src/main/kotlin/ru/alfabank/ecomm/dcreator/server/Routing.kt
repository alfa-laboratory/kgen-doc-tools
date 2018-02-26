package ru.alfabank.ecomm.dcreator.server

import com.fasterxml.jackson.databind.ObjectMapper
import com.fasterxml.jackson.module.kotlin.readValue
import com.fasterxml.jackson.module.kotlin.registerKotlinModule
import io.ktor.application.ApplicationCall
import io.ktor.application.call
import io.ktor.content.files
import io.ktor.content.static
import io.ktor.http.HttpStatusCode
import io.ktor.request.receiveText
import io.ktor.response.respond
import io.ktor.routing.Routing
import io.ktor.routing.delete
import io.ktor.routing.get
import io.ktor.routing.post
import ru.alfabank.ecomm.dcreator.server.responses.*
import ru.alfabank.ecomm.dcreator.server.utils.doAndDisplayDiff
import java.io.File
import java.io.FileNotFoundException
import java.nio.file.Files
import java.time.LocalTime

class RenderRouterConfiguration(
        private val inputDirectory: File,
        private val documentGenerator: DocumentGenerator
) {
    fun config(): Routing.() -> Unit = {
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
            files("jvm_server/public_react")
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
                "folder" -> newFile.mkdir()
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
    }
}