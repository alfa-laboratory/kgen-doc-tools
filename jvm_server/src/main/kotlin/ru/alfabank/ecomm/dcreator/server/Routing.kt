package ru.alfabank.ecomm.dcreator.server

import com.fasterxml.jackson.databind.ObjectMapper
import com.fasterxml.jackson.module.kotlin.readValue
import com.fasterxml.jackson.module.kotlin.registerKotlinModule
import io.ktor.application.ApplicationCall
import io.ktor.application.call
import io.ktor.content.*
import io.ktor.http.ContentType
import io.ktor.http.HttpStatusCode
import io.ktor.request.receiveMultipart
import io.ktor.request.receiveText
import io.ktor.response.header
import io.ktor.response.respond
import io.ktor.response.respondFile
import io.ktor.response.respondText
import io.ktor.routing.Routing
import io.ktor.routing.delete
import io.ktor.routing.get
import io.ktor.routing.post
import ru.alfabank.ecomm.dcreator.render.DocumentGenerator
import ru.alfabank.ecomm.dcreator.server.responses.*
import ru.alfabank.ecomm.dcreator.server.utils.copyToSuspend
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
                val indexFile = File(ApplicationMode.TEST.publicFolder, "index.html")

                static("") {
                    files(ApplicationMode.TEST.publicFolder)
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
            if (folder.exists() && folder.isDirectory && folder.relativeToOrNull(inputDirectory) != null) {
                val files = folder.listFiles()?.map {
                    when {
                        it.isFile -> FileInfo(it.name, FileType.File)
                        it.isDirectory -> FileInfo(it.name, FileType.Folder)
                        else -> throw RuntimeException("invalid type")
                    }
                } ?: emptyList()

                call.respond(FilesResponse(true, files.sortedBy { it.name }))
            } else {
                call.respond(StatusResponse(false))
            }
        }

        get("file") {
            val fileName = call.parameters["path"]!!
            val file = File(inputDirectory, fileName)

            if (file.relativeToOrNull(inputDirectory) == null)
                throw RuntimeException("invalid path")

            call.respond(file.readText())
        }

        post("file") {
            val updateFile = doAndDisplayDiff("file ${call.parameters["path"]} write") {
                val fileName = call.parameters["path"]!!
                val localUpdateFile = File(inputDirectory, fileName)

                if (localUpdateFile.relativeToOrNull(inputDirectory) == null)
                    throw RuntimeException("invalid path")

                if (!localUpdateFile.exists())
                    throw FileNotFoundException(fileName)

                val data = call.receiveText()

                localUpdateFile.writeText(data)
                return@doAndDisplayDiff localUpdateFile
            }

            doAndDisplayDiff("run md reload for file $updateFile") {
                documentGenerator.generateHtmlFromMd(updateFile)
            }

            call.respond(HttpStatusCode.OK)
        }

        delete("file") {
            println("${LocalTime.now()} try to delete file..")
            val fileName = call.parameters["path"]!!

            val deleteFile = File(inputDirectory, fileName)

            if (deleteFile.relativeToOrNull(inputDirectory) == null)
                throw RuntimeException("invalid path")

            if (!deleteFile.exists())
                throw FileNotFoundException(fileName)

            val result = deleteFile.deleteRecursively()

            call.respond(StatusResponse(result))
        }

        post("createFile") {
            val (fileName, type) = call.receiveRequest<NewFileRequest>()

            val newFile = File(inputDirectory, fileName)

            if (newFile.relativeToOrNull(inputDirectory) == null)
                throw RuntimeException("invalid path")

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

            if (file.relativeToOrNull(inputDirectory) == null)
                throw RuntimeException("invalid path")

            if (!file.exists())
                throw FileNotFoundException(file.absolutePath)

            val newFile = File(file.parent, newName)

            Files.move(file.toPath(), newFile.toPath())

            call.respond(StatusResponse(true))
        }

        get("downloadZip") {
            var zipFile: File? = null
            try {
                zipFile = zipFiles(documentGenerator.outputDirectory)
                call.response.header("Content-Disposition", "attachment; filename=\"output.zip\"")
                call.respondFile(zipFile)
            } finally {
                zipFile?.delete()
            }
        }

        post("uploadImage") {
            val fileName = call.parameters["name"]!!

            val imagesDirectory = File(documentGenerator.outputDirectory, "static/images")
            val uploadingFile = File(imagesDirectory, fileName).apply {
                if (exists() && isFile) delete()
            }

            val multipart = call.receiveMultipart()
            multipart.forEachPart { part ->
                when (part) {
                    is PartData.FileItem -> {
                        part.streamProvider().use { its -> uploadingFile.outputStream().buffered().use { its.copyToSuspend(it) } }
                    }
                }

                part.dispose()
            }

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