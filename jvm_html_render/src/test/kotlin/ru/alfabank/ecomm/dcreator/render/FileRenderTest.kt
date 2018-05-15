package ru.alfabank.ecomm.dcreator.render

import kotlinx.coroutines.experimental.runBlocking
import org.junit.jupiter.api.AfterEach
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import ru.alfabank.ecomm.dcreator.parser.MarkdownParser
import ru.alfabank.ecomm.dcreator.render.process.HeaderProcessor
import ru.alfabank.ecomm.dcreator.render.serialize.FreemarkerRender
import ru.alfabank.ecomm.dcreator.render.serialize.NodeSerializer
import java.io.File
import java.nio.file.Files
import kotlin.test.assertEquals

class FileRenderTest {

    private lateinit var tempDirectory: File

    @BeforeEach
    fun before() {
        tempDirectory = Files.createTempDirectory("generate_temp_files").toFile()
    }

    @AfterEach
    fun after() {
        tempDirectory.deleteRecursively()
    }

    @Test
    fun `test file render`() = runBlocking {
        val inputDirectory = File(tempDirectory, "input").apply { mkdirs() }

        val freemarkerRender = FreemarkerRender("../files/layout/freemarker")

        val generateFile = File(inputDirectory, "testFile.md").apply {
            writeText(
                """
                # Header line 1
                ** bold text **
                _ italic text _

                # Header line 2
            """.trimIndent()
            )
        }

        val node = MarkdownParser(inputDirectory).parse(generateFile)

        val headerProcessor = HeaderProcessor()
        val (result, replaceNodes) = headerProcessor.process(node)

        val nodeSerializer = NodeSerializer(freemarkerRender, replaceNodes)

        val preparedResult = result.mapValues { value ->
            nodeSerializer.writeNodeToString(value.value)
        }

        val nodesData = freemarkerRender.render("index.ftlh", preparedResult)

        val preparedActualText = nodesData.replace(UUID_REGEX, "00000")
            .split("\n")
            .map { it.trim() }

        val expectedText = File(this::class.java.getResource("/expect.html").file).readText()
            .split("\n")
            .map { it.trim() }

        assertEquals(expectedText, preparedActualText)
    }

    companion object {
        private val UUID_REGEX = """q?[a-f0-9]{8}(-[a-f0-9]{4}){3}-[a-f0-9]{12}""".toRegex()
    }

}