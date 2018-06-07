package ru.alfabank.ecomm.dcreator.server

import com.typesafe.config.ConfigFactory
import io.ktor.application.install
import io.ktor.config.HoconApplicationConfig
import io.ktor.features.ContentNegotiation
import io.ktor.jackson.jackson
import io.ktor.routing.routing
import io.ktor.server.engine.ApplicationEngine
import io.ktor.server.engine.applicationEngineEnvironment
import io.ktor.server.engine.connector
import io.ktor.server.engine.embeddedServer
import io.ktor.server.netty.Netty
import org.slf4j.LoggerFactory
import ru.alfabank.ecomm.dcreator.render.DocumentGenerator
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
    layoutDirectory: File = File(workDir, "layout"),
    mode: ApplicationMode = ApplicationMode.PROD
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
        engine = embeddedServer(Netty, applicationEngineEnvironment {
            log = LoggerFactory.getLogger("ktor.application")
            config = HoconApplicationConfig(ConfigFactory.load())

            module {
                install(ContentNegotiation) {
                    jackson {
                        writerWithDefaultPrettyPrinter()
                    }
                }

                routing(renderRouterConfiguration.config())
            }

            connector {
                port = config.property("ktor.deployment.port").getString().toInt()
                host = "127.0.0.1"
            }
        })

        engine!!.start(wait)
    }

    fun stop(gracePeriod: Long = 0, timeout: Long = 3, timeUnit: TimeUnit = TimeUnit.SECONDS) {
        engine?.stop(gracePeriod, timeout, timeUnit)
    }
}