package ru.alfabank.ecomm.dcreator.render

import kotlinx.coroutines.experimental.sync.Mutex
import kotlinx.coroutines.experimental.sync.withLock
import ru.alfabank.ecomm.dcreator.nodes.LayoutServiceNode
import ru.alfabank.ecomm.dcreator.nodes.ServiceNode
import ru.alfabank.ecomm.dcreator.parser.MarkdownParser
import ru.alfabank.ecomm.dcreator.render.process.*
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

    inner class RelativePath(
        private val srcFile: File
    ) {
        fun toRelativePath(): String {
            val relativePath = srcFile.toRelative()
            return "$relativePath${srcFile.nameWithoutExtension}.$HTML_EXTENSION"
        }

        fun toLink(): String {
            val relativePath = srcFile.toRelative()
            return "/${outputDirectory.name}/$relativePath${srcFile.nameWithoutExtension}.$HTML_EXTENSION"
        }

        fun subPath(newName: String, postFix: String = ""): RelativePath {
            val dir = File(srcFile.parent, srcFile.nameWithoutExtension)
            val newFileName = File(newName).nameWithoutExtension
            val newFile = File(dir, "$newFileName$postFix")

            return RelativePath(newFile)
        }

        private fun File.toRelative(): String = this.parentFile
            .toRelativeString(inputDirectory)
            .let { if (it.isEmpty()) "" else "$it/" }
    }

    suspend fun generateHtmlFromMd(generateFile: File) {
        val nodesData: List<FileProcessData> = renderFile(generateFile)
        if (nodesData.isEmpty())
            return

        nodesData.forEach { (data, _, relativePath) ->
            val outputFile = File(outputDirectory, relativePath.toRelativePath())

            outputFile.parentFile.apply {
                if (!exists()) mkdirs()
            }

            if (!outputFile.exists()) {
                outputFile.createNewFile()
            }

            outputFile.writeText(data)
        }
    }

    private suspend fun renderFile(
        generateFile: File,
        serviceNodes: List<ServiceNode> = emptyList()
    ): List<FileProcessData> {
        if (!init) {
            initRenders()
        }

        val relativePath = RelativePath(generateFile)

        if (!generateFile.isFile)
            return emptyList()

        if (generateFile.extension.toLowerCase() != MD_EXTENSION) {
            generateFile.copyTo(File(outputDirectory, generateFile.name), overwrite = true)
            return emptyList()
        }

        val (node, localServiceNodes) = MarkdownParser(inputDirectory).parse(generateFile)

        val layoutNode: LayoutServiceNode? = findLayoutNode(localServiceNodes)

        val nodeProcessor = layoutNode?.let { nodeProcessors[it.layout] }
            ?: nodeProcessors[DEFAULT_LAYOUT]
            ?: throw RuntimeException("default node processor not found")
        val freemarkerRender = layoutNode?.let { freemarkerRenders[it.layout] }
            ?: freemarkerRenders[DEFAULT_LAYOUT]
            ?: throw RuntimeException("default layout not found in directory: $rootLayoutDir")

        return nodeProcessor.process(node, localServiceNodes + serviceNodes, relativePath)
            .let { (localRelativePath, result, replaceNodes, localServiceNodes, childs): ProcessResult ->
                val nodeSerializer = NodeSerializer(freemarkerRender, replaceNodes)
                val preparedResult = nodeSerializer.prepareParams(result)

                listOf(
                    FileProcessData(
                        data = freemarkerRender.render(INDEX_FILE_NAME, preparedResult),
                        serviceNodes = localServiceNodes,
                        relativePath = localRelativePath
                    )
                ) + childs
            }
    }

    private suspend fun generateEmbeddedHtmlFromMd(
        generateFile: String,
        serviceNodes: List<ServiceNode>
    ): List<FileProcessData> {
        return renderFile(File(inputDirectory, generateFile), serviceNodes)
    }

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
        nodeProcessors += TABS_LAYOUT to IndexProcessor({ file, serviceNodes ->
            generateEmbeddedHtmlFromMd(
                file,
                serviceNodes
            )
        })
    }

    companion object {
        private const val DEFAULT_LAYOUT = "default"
        private const val TABS_LAYOUT = "index"

        private const val INDEX_FILE_NAME = "index.ftlh"
        private const val MD_EXTENSION = "md"
        private const val HTML_EXTENSION = "html"
    }
}