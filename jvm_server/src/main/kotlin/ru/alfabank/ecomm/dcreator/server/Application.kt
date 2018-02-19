package ru.alfabank.ecomm.dcreator.server

import com.fasterxml.jackson.databind.ObjectMapper
import com.fasterxml.jackson.module.kotlin.registerKotlinModule
import io.ktor.application.install
import io.ktor.features.ContentNegotiation
import io.ktor.jackson.jackson
import io.ktor.routing.routing
import io.ktor.server.engine.embeddedServer
import io.ktor.server.netty.Netty
import java.io.File

data class NewFileRequest(val path: String, val type: String)

data class RenameRequest(val path: String, val name: String)

val objectMapper = ObjectMapper()
        .registerKotlinModule()

fun main(args: Array<String>) {
    val inputDirectory = File("files/input")
    val outputDirectory = File("files/output/pages")
    val layoutDirectory = File("files/layout/freemarker")

    val documentGenerator = DocumentGenerator(inputDirectory, outputDirectory, layoutDirectory)
    val renderRouterConfiguration = RenderRouterConfiguration(inputDirectory, documentGenerator)



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

        routing(renderRouterConfiguration.config())
    }.start(wait = true)
}