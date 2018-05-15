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

    val mode = if (args.size >= 2 && args[1] == "test")
        ApplicationMode.TEST
    else
        ApplicationMode.PROD

    Application(workDir, mode = mode).apply {
        start(true)
    }
}

private fun unpackFiles(workDir: File) {
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

enum class ApplicationMode(val publicFolder: File = File("")) {
    TEST(File("jvm_server/public")),
    PROD;
}

class Application(
    workDir: File,
    inputDirectory: File = File(workDir, "input"),
    outputDirectory: File = File(workDir, "output/pages"),
    layoutDirectory: File = File(workDir, "layout/freemarker"),
    mode: ApplicationMode = ApplicationMode.PROD,
    private val port: Int = 8080
) {
    private val documentGenerator = DocumentGenerator(inputDirectory, outputDirectory, layoutDirectory)
    private val renderRouterConfiguration = RenderRouterConfiguration(
        workDir,
        inputDirectory,
        documentGenerator,
        mode
    )

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