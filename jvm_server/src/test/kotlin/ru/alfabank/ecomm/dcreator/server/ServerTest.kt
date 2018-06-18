package ru.alfabank.ecomm.dcreator.server

import org.junit.jupiter.api.AfterEach
import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import ru.alfabank.ecomm.dcreator.server.Application.Companion.DEFAULT_HOST
import java.io.File
import java.net.InetAddress
import java.net.ServerSocket
import java.time.Duration
import java.time.ZonedDateTime
import java.util.concurrent.TimeoutException

class ServerTest {
    private val application = Application(
        File("../"),
        File("../files/input"),
        File("../files/output/pages"),
        File("../files/output/static"),
        File("../files/layout/freemarker"),
        ApplicationMode.PROD
    )

    @AfterEach
    fun after() {
        try {
            application.stop()
        } catch (e: Exception) {
        }
    }

    @BeforeEach
    fun `application test`() {
        application.start(false)
    }

    @Test
    fun test() {
        waitOfCondition(condition = this::isServerConnect)
        Assertions.assertTrue(isServerConnect())

        println("all good")
    }

    private fun isServerConnect() = try {
        ServerSocket(8088, 0, InetAddress.getByName(DEFAULT_HOST)).run {
            println("not connect")
            close()
            false
        }
    } catch (e: Exception) {
        println("connect")
        true
    }

    private fun waitOfCondition(waitTimeout: Long = 1000, sleepTime: Long = 100, condition: () -> Boolean) {
        if (waitTimeout < sleepTime)
            throw RuntimeException("invalid config")

        val startTime = ZonedDateTime.now()
        while (!Thread.interrupted()) {
            val currentTime = ZonedDateTime.now()

            if (Duration.between(startTime, currentTime).toMillis() > waitTimeout)
                throw TimeoutException("timeout")

            if (condition())
                break

            Thread.sleep(sleepTime)
        }
    }
}