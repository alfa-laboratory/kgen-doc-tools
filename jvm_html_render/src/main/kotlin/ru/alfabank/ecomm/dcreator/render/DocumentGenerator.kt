package ru.alfabank.ecomm.dcreator.render

import kotlinx.coroutines.experimental.runBlocking
import kotlinx.coroutines.experimental.sync.Mutex
import kotlinx.coroutines.experimental.sync.withLock
import ru.alfabank.ecomm.dcreator.nodes.LayoutServiceNode
import ru.alfabank.ecomm.dcreator.nodes.ServiceNode
import ru.alfabank.ecomm.dcreator.parser.MarkdownParser
import ru.alfabank.ecomm.dcreator.render.process.HeaderProcessor
import ru.alfabank.ecomm.dcreator.render.process.NodeProcessor
import ru.alfabank.ecomm.dcreator.render.process.ProcessingData
import ru.alfabank.ecomm.dcreator.render.process.TabsProcessor
import ru.alfabank.ecomm.dcreator.render.serialize.FreemarkerRender
import ru.alfabank.ecomm.dcreator.render.serialize.NodeSerializer
import java.io.File

class DocumentGenerator(
    private val inputDirectory: File,
    private val outputDirectory: File,
    private val rootLayoutDir: File
) {
    private var init = false
    private val lock = Mutex(false)

    private val freemarkerRenders: MutableMap<String, FreemarkerRender> = mutableMapOf()
    private val nodeProcessors: MutableMap<String, NodeProcessor> = mutableMapOf()

    suspend fun generateHtmlFromMd(generateFile: File) {
        val nodesData = renderFile(generateFile)
        if (nodesData.isEmpty())
            return

        val relativePath = generateFile.parentFile
            .toRelativeString(inputDirectory)
            .let { if (it.isEmpty()) "" else "$it/" }

        nodesData.forEach { (data, _, prefix) ->
            val relativeFileName = if (prefix.isEmpty())
                toRelativeFileName(relativePath, generateFile)
            else
                prefix

            val outputFile = File(outputDirectory, relativeFileName)

            outputFile.parentFile.apply {
                if (!exists()) mkdirs()
            }

            if (!outputFile.exists()) {
                outputFile.createNewFile()
            }

            outputFile.writeText(data)
        }
    }

    private suspend fun renderFile(generateFile: File): List<ProcessingData> {
        if (!init) {
            initRenders()
        }

        if (!generateFile.isFile)
            return emptyList()

        if (generateFile.extension.toLowerCase() != MD_EXTENSION) {
            generateFile.copyTo(File(outputDirectory, generateFile.name), overwrite = true)
            return emptyList()
        }

        val (node, serviceNodes) = MarkdownParser(inputDirectory).parse(generateFile)

        val layoutNode: LayoutServiceNode? = findLayoutNode(serviceNodes)

        val nodeProcessor = layoutNode?.let { nodeProcessors[it.layout] }
            ?: nodeProcessors[DEFAULT_LAYOUT]!!
        val freemarkerRender = layoutNode?.let { freemarkerRenders[it.layout] }
            ?: freemarkerRenders[DEFAULT_LAYOUT]!!

        return nodeProcessor.process(node, serviceNodes)
            .map { (relativeLink, result, replaceNodes, localServiceNodes) ->
                val nodeSerializer = NodeSerializer(freemarkerRender, replaceNodes)
                val preparedResult = result.mapValues { value ->
                    nodeSerializer.writeNodeToString(value.value)
                }

                ProcessingData(
                    data = freemarkerRender.render(INDEX_FILE_NAME, preparedResult),
                    serviceNodes = localServiceNodes,
                    relativeFileName = relativeLink
                )
            }
    }

    private fun generateHtmlFromMd(generateFile: String): List<ProcessingData> = runBlocking {
        renderFile(File(inputDirectory, generateFile))
    }

    private fun toRelativeLink(name: String): String {
        val srcFile = File(inputDirectory, name)

        val relativePath = srcFile.parentFile
            .toRelativeString(inputDirectory)
            .let { if (it.isEmpty()) "" else "$it/" }

        return toRelativeFileName(relativePath, srcFile)
    }

    private fun toRelativeFileName(relativePath: String, srcFile: File) =
        "$relativePath${srcFile.nameWithoutExtension}.$HTML_EXTENSION"

    private fun findLayoutNode(serviceNodes: List<ServiceNode>): LayoutServiceNode? = serviceNodes
        .asSequence()
        .filterIsInstance<LayoutServiceNode>()
        .firstOrNull()

    private suspend fun initRenders() = lock.withLock {
        if (init)
            return

        if (!rootLayoutDir.exists() || rootLayoutDir.isFile)
            throw RuntimeException("root layout directory: $rootLayoutDir not exist, or it's a file")

        for (layoutDir in rootLayoutDir.listFiles()) {
            if (layoutDir.isFile)
                continue

            freemarkerRenders += layoutDir.name to FreemarkerRender(layoutDir)
        }

        nodeProcessors += DEFAULT_LAYOUT to HeaderProcessor()
        nodeProcessors += TABS_LAYOUT to TabsProcessor(this::generateHtmlFromMd, this::toRelativeLink)
    }

    companion object {
        private const val DEFAULT_LAYOUT = "default"
        private const val TABS_LAYOUT = "tabs"

        private const val INDEX_FILE_NAME = "index.ftlh"
        private const val MD_EXTENSION = "md"
        private const val HTML_EXTENSION = "html"
    }
}