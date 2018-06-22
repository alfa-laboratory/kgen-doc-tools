package ru.alfabank.ecomm.dcreator.render

import kotlinx.coroutines.experimental.runBlocking
import org.junit.jupiter.api.AfterEach
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import ru.alfabank.ecomm.dcreator.parser.MarkdownParser
import ru.alfabank.ecomm.dcreator.render.process.HeaderProcessor
import ru.alfabank.ecomm.dcreator.render.process.ProcessResult
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
        val outputDirectory = File(tempDirectory, "output").apply { mkdirs() }
        val layoutRootDir = File("../files/layout")
        val freemarkerRender = FreemarkerRender(File(layoutRootDir, "default"))

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

        val (node, serviceNodes) = MarkdownParser(inputDirectory).parse(generateFile)

        val documentGenerator = DocumentGenerator(inputDirectory, outputDirectory, layoutRootDir)

        val headerProcessor = HeaderProcessor()

        val processResult: ProcessResult = headerProcessor.process(
            node,
            serviceNodes,
            documentGenerator.RelativePagePath(File(inputDirectory, "123.md"))
        )

        val (relativeLink, result, replaceNodes, resultServiceNodes, childs) = processResult

        assertEquals("123.html", relativeLink.toRelativeFilePath())

        val nodeSerializer = NodeSerializer(freemarkerRender, replaceNodes)

        val preparedResult = nodeSerializer.prepareParams(result)

        val nodesData = freemarkerRender.render("index.ftlh", preparedResult)

        val actualText = nodesData.replace(UUID_REGEX, "00000")

        val preparedActualText = actualText
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