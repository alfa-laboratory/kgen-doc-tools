package ru.alfabank.ecomm.dcreator.render

import kotlinx.coroutines.sync.Mutex
import kotlinx.coroutines.sync.withLock
import ru.alfabank.ecomm.dcreator.nodes.LayoutServiceNode
import ru.alfabank.ecomm.dcreator.nodes.ServiceNode
import ru.alfabank.ecomm.dcreator.parser.MarkdownParser
import ru.alfabank.ecomm.dcreator.render.process.*
import ru.alfabank.ecomm.dcreator.render.serialize.FreemarkerRender
import ru.alfabank.ecomm.dcreator.render.serialize.NodeSerializer
import java.io.File

class DocumentGenerator(
    val inputDirectory: File,
    val outputDirectory: File,
    val rootLayoutDir: File
) {
    private var init = false
    private val lock = Mutex(false)

    private val outputPageDirectory = File(outputDirectory, "pages")

    private val freemarkerRenders: MutableMap<String, FreemarkerRender> = mutableMapOf()
    private val nodeProcessors: MutableMap<String, NodeProcessor> = mutableMapOf()

    interface RelativePath {
        fun toLink(relativePagePath: RelativePagePath): String
    }

    inner class RelativeStaticPath(
        val path: String
    ) : RelativePath {
        override fun toLink(relativePagePath: RelativePagePath): String {
            return "${inputDirectory.toRelativeString(relativePagePath.srcFile)}/$path"
        }
    }

    inner class RelativeFilesPath(
        val path: String
    ) : RelativePath {
        override fun toLink(relativePagePath: RelativePagePath): String {
            return path
        }
    }

    inner class RelativePagePath(
        val srcFile: File
    ) {
        fun fromPath(path: String): RelativePath {
            return if (path.startsWith(RELATIVE_PREFIX)) {
                RelativeFilesPath(path.drop(RELATIVE_PREFIX.length))
            } else {
                RelativeStaticPath(path)
            }
        }

        fun toRelativeFilePath(): String {
            val relativePath = srcFile.toRelative()
            return "$relativePath${srcFile.nameWithoutExtension}.$HTML_EXTENSION"
        }

        fun toRelativePath(): String {
            return inputDirectory.toRelativeString(srcFile)
        }

        fun toLink(relativePagePath: RelativePagePath? = null): String {
            val relativePath = srcFile
                .relativeTo(toFolderPath((relativePagePath ?: RelativePagePath(inputDirectory)).srcFile.parentFile))

            return if (relativePath.extension == MD_EXTENSION) {
                "${relativePath.parentFile?.let { "$it/" } ?: ""}${relativePath.nameWithoutExtension}.$HTML_EXTENSION"
            } else {
                relativePath.toString()
            }
        }

        fun subPath(subFile: File, postFix: String = subFile.extension): RelativePagePath {
            val dir = File(srcFile.parent, srcFile.nameWithoutExtension)
            val newFileName = subFile.nameWithoutExtension
            val newFile = File(dir, "$newFileName.$postFix")

            return RelativePagePath(newFile)
        }

        private fun File?.toRelative(): String {
            if (this == null || this.parentFile == null)
                return ""

            return this.parentFile
                .toRelativeString(inputDirectory)
                .let { if (it.isEmpty()) "" else "$it/" }
        }

        private fun toFolderPath(file: File?): File {
            return if (file == null)
                File(inputDirectory, "")
            else
                File(file.parent, file.nameWithoutExtension)
        }
    }

    suspend fun generateHtmlFromMd(generateFile: File) {
        val nodesData: List<FileProcessData> = renderFile(generateFile)
        if (nodesData.isEmpty())
            return

        nodesData.forEach { (data, _, relativePath) ->
            val outputFile = File(outputPageDirectory, relativePath.toRelativeFilePath())

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
        serviceNodes: List<ServiceNode> = emptyList(),
        relativePagePath: RelativePagePath = RelativePagePath(generateFile)
    ): List<FileProcessData> {
        if (!init) {
            initRenders()
        }

        if (!generateFile.isFile)
            return emptyList()

        if (generateFile.extension.toLowerCase() != MD_EXTENSION) {
            generateFile.copyTo(File(outputPageDirectory, generateFile.name), overwrite = true)
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

        return nodeProcessor.process(node, localServiceNodes + serviceNodes, relativePagePath)
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
        serviceNodes: List<ServiceNode>,
        relativePagePath: RelativePagePath
    ): List<FileProcessData> {
        return renderFile(File(inputDirectory, generateFile), serviceNodes, relativePagePath)
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
        nodeProcessors += TABS_LAYOUT to IndexProcessor({ file, serviceNodes, relativePagePath ->
            generateEmbeddedHtmlFromMd(
                file,
                serviceNodes,
                relativePagePath
            )
        })
    }

    companion object {
        private const val DEFAULT_LAYOUT = "default"
        private const val TABS_LAYOUT = "index"

        private const val INDEX_FILE_NAME = "index.ftlh"

        private const val RELATIVE_PREFIX = "./"

        const val MD_EXTENSION = "md"
        const val HTML_EXTENSION = "html"
    }
}