package ru.alfabank.ecomm.dcreator.server

import org.junit.jupiter.api.AfterEach
import org.junit.jupiter.api.Assertions.assertTrue
import org.junit.jupiter.api.Test
import java.io.File
import java.net.ServerSocket

class ServerTest {
    private val application = Application(
            File("../"),
            File("../files/input"),
            File("../files/output/pages"),
            File("../files/layout/freemarker"),
            ApplicationMode.PROD,
            SERVER_PORT
    )

    @AfterEach
    fun after() {
        try {
            application.stop()
        } catch (e: Exception) {
        }
    }

    @Test
    fun `application test`() {
        application.start(false)

        val isConnected = try {
            ServerSocket(SERVER_PORT)
            false
        } catch (e: Exception) {
            true
        }

        assertTrue(isConnected)
    }

    companion object {
        private const val SERVER_PORT = 8081
    }
}