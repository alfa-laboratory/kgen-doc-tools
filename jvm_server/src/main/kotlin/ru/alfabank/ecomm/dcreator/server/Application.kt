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
import kotlinx.coroutines.experimental.launch
import kotlinx.coroutines.experimental.newFixedThreadPoolContext
import kotlinx.coroutines.experimental.runBlocking
import org.slf4j.LoggerFactory
import ru.alfabank.ecomm.dcreator.render.DocumentGenerator
import java.io.File
import java.util.concurrent.Executors
import java.util.concurrent.TimeUnit
import java.util.zip.ZipEntry
import java.util.zip.ZipInputStream

private val zipFiles by lazy {
    ClassLoader::class.java.getResourceAsStream("/files.zip")
}

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
    private val inputDirectory: File = File(workDir, "input"),
    outputDirectory: File = File(workDir, "output/pages"),
    staticDirectory: File = File(workDir, "output/static"),
    private val layoutDirectory: File = File(workDir, "layout"),
    mode: ApplicationMode = ApplicationMode.PROD
) {
    private val documentGenerator = DocumentGenerator(
        inputDirectory,
        outputDirectory,
        staticDirectory,
        layoutDirectory
    )
    private val renderRouterConfiguration = RenderRouterConfiguration(
        workDir,
        inputDirectory,
        documentGenerator,
        mode
    )

    private val filesRenderContext by lazy {
        newFixedThreadPoolContext(
            Runtime.getRuntime().availableProcessors(),
            "FileRenderPoll"
        )
    }
    private val templateFilesUpdateTime = HashMap<String, Long>()
    private val layoutScheduler = Executors.newScheduledThreadPool(1);

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

            val port = config.property(KTOR_PORT_PROPERTY).getString().toInt()
            val host = config.propertyOrNull(KTOR_HOST_PROPERTY)?.getString() ?: DEFAULT_HOST

            val enableHotReload = config.propertyOrNull(KTOR_TEMPLATE_HOT_RELOAD)?.getString()?.toBoolean() ?: false
            if (enableHotReload) initHotReload()

            log.info("Running application at $port.. Open http://$host:$port")

            connector {
                this.port = port
                this.host = host
            }
        })

        engine!!.start(wait)
    }

    private fun initHotReload() {
        log.info("hot reload is enabled")

        layoutDirectory.walkTopDown().forEach {
            if (it.isFile) {
                templateFilesUpdateTime += it.absolutePath to it.lastModified()
            }
        }

        layoutScheduler.scheduleWithFixedDelay({
            if (checkLayoutIsModified()) {
                renderAllFiles()
                log.info("done")
            }
        }, 0, LAYOUT_CHECK_DELAY, TimeUnit.MILLISECONDS)
    }

    private fun checkLayoutIsModified(): Boolean {
        layoutDirectory.walkTopDown().forEach {
            if (it.isFile) {
                val newModified = it.lastModified()
                val oldModified = templateFilesUpdateTime[it.absolutePath]

                if (oldModified != newModified) {
                    log.info("file ${it.absolutePath} has been modified. Reload all input files..")

                    templateFilesUpdateTime += it.absolutePath to newModified
                    return true
                }
            }
        }

        return false
    }

    private fun renderAllFiles() = runBlocking {
        inputDirectory.walkTopDown().mapNotNull {
            if (it.isFile) {
                launch(filesRenderContext) {
                    log.info("reload file ${it.absolutePath}")
                    documentGenerator.generateHtmlFromMd(it)
                }
            } else
                null
        }.forEach { it.join() }
    }

    fun stop(gracePeriod: Long = 0, timeout: Long = 3, timeUnit: TimeUnit = TimeUnit.SECONDS) {
        engine?.stop(gracePeriod, timeout, timeUnit)
    }

    companion object {
        internal const val DEFAULT_HOST = "localhost"

        private val log = LoggerFactory.getLogger(Application::class.java)

        private const val LAYOUT_CHECK_DELAY = 500L

        private const val KTOR_PORT_PROPERTY = "ktor.deployment.port"
        private const val KTOR_HOST_PROPERTY = "ktor.deployment.host"
        private const val KTOR_TEMPLATE_HOT_RELOAD = "ktor.template.reload"
    }
}