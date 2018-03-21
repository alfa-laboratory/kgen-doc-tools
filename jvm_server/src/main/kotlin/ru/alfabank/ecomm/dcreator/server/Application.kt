package ru.alfabank.ecomm.dcreator.server

import io.ktor.application.install
import io.ktor.features.ContentNegotiation
import io.ktor.jackson.jackson
import io.ktor.routing.routing
import io.ktor.server.engine.ApplicationEngine
import io.ktor.server.engine.embeddedServer
import io.ktor.server.netty.Netty
import java.io.File
import java.util.concurrent.TimeUnit
import java.util.zip.ZipEntry
import java.util.zip.ZipInputStream

private val zipFiles = ClassLoader::class.java.getResourceAsStream("/files.zip")

fun main(args: Array<String>) {
    val workDir = if (args.isEmpty())
        File("files/")
    else
        File(args[0])

    if (!workDir.exists())
        workDir.mkdirs()

    if (workDir.list().isEmpty())
        unpackFiles(workDir)

    Application(workDir).apply {
        start(true)
    }
}

fun unpackFiles(workDir: File) {
    ZipInputStream(zipFiles).use { zipInputStream ->
        while (true) {
            val entry: ZipEntry = zipInputStream.nextEntry ?: break

            try {
                val output = File(workDir, entry.name)

                if (entry.isDirectory) {
                    output.mkdirs()
                } else {
                    output.createNewFile()
                    output.outputStream().use { zipInputStream.copyTo(it) }
                }
            } finally {
                zipInputStream.closeEntry()
            }
        }
    }
}

class Application(
        workDir: File,
        inputDirectory: File = File(workDir, "input"),
        outputDirectory: File = File(workDir, "output/pages"),
        layoutDirectory: File = File(workDir, "layout/freemarker"),
        private val port: Int = 8080
) {
    private val documentGenerator = DocumentGenerator(inputDirectory, outputDirectory, layoutDirectory)
    private val renderRouterConfiguration = RenderRouterConfiguration(workDir, inputDirectory, documentGenerator)

    private var engine: ApplicationEngine? = null

    fun start(wait: Boolean = true) {
        engine = embeddedServer(Netty, port) {
            install(ContentNegotiation) {
                jackson {
                    writerWithDefaultPrettyPrinter()
                }
            }

            routing(renderRouterConfiguration.config())
        }

        engine!!.start(wait)
    }

    fun stop(gracePeriod: Long = 0, timeout: Long = 3, timeUnit: TimeUnit = TimeUnit.SECONDS) {
        engine?.stop(gracePeriod, timeout, timeUnit)
    }
}