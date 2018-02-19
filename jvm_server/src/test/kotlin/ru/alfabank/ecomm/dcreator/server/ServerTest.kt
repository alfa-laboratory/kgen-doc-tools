package ru.alfabank.ecomm.dcreator.server

import kotlinx.coroutines.experimental.runBlocking
import org.junit.jupiter.api.AfterEach
import org.junit.jupiter.api.Assertions.assertTrue
import org.junit.jupiter.api.Test
import java.io.File
import java.net.ServerSocket

class ServerTest {
    val application = Application(
            File("../files/input"),
            File("../files/output/pages"),
            File("../files/layout/freemarker"),
            8081
    )

    @AfterEach
    fun after() {
        try {
            application.stop()
        } catch (e: Exception) {
        }
    }

    @Test
    fun `application test`() = runBlocking {
        application.start(false)

        val isConnected = try {
            ServerSocket(8081)
            false
        } catch (e: Exception) {
            true
        }

        assertTrue(isConnected)
    }
}