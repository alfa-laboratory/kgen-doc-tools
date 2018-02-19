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

fun main(args: Array<String>) {
    val application = Application()

    application.start(true)
}

class Application(
        inputDirectory: File = File("files/input"),
        outputDirectory: File = File("files/output/pages"),
        layoutDirectory: File = File("files/layout/freemarker"),
        private val port: Int = 8080
) {
    private val documentGenerator = DocumentGenerator(inputDirectory, outputDirectory, layoutDirectory)
    private val renderRouterConfiguration = RenderRouterConfiguration(inputDirectory, documentGenerator)

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