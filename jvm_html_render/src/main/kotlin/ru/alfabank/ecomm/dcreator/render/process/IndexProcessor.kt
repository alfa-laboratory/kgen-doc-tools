package ru.alfabank.ecomm.dcreator.render.process

import kotlinx.coroutines.experimental.async
import kotlinx.coroutines.experimental.newFixedThreadPoolContext
import ru.alfabank.ecomm.dcreator.nodes.*
import ru.alfabank.ecomm.dcreator.render.DocumentGenerator
import java.io.File
import kotlin.text.RegexOption.IGNORE_CASE

data class IncludeFilesInfo(
    val files: List<IncludeFileInfo>
) : Node by NodeIdGen(), BlockNode

data class IncludeFileInfo(
    val link: String,
    val name: String,
    val title: String?
) : Node by NodeIdGen(), BlockNode

class IndexProcessor(
    private val generateData: suspend (String, List<ServiceNode>, DocumentGenerator.RelativePagePath) -> List<FileProcessData>
) : NodeProcessor {
    private val filesProcessingContext = newFixedThreadPoolContext(
        Runtime.getRuntime().availableProcessors(),
        "FileProcessPoll"
    )

    override suspend fun process(
        node: Node,
        serviceNodes: List<ServiceNode>,
        relativePath: DocumentGenerator.RelativePagePath
    ): ProcessResult {
        val includeFiles: List<Pair<IncludeServiceNode, DocumentGenerator.RelativePagePath>> =
            serviceNodes.filterIsInstance<IncludeServiceNode>()
                .map { serviceNode ->
                    val subFileRelative = relativePath.subPath(File(serviceNode.name.toPreparedName()))

                    Pair(serviceNode, subFileRelative)
                }

        val version = serviceNodes.findServiceNode<VersionServiceNode>()
        val backUrl = BackUrlServiceNode("../" + relativePath.toLink(relativePath))
        val title = serviceNodes.findServiceNode<TitleServiceNode>()
        val lastUpdate = serviceNodes.findServiceNode<LastUpdateServiceNode>()

        val nodes = listOfNotNull(
            version,
            backUrl,
            title,
            lastUpdate
        )

        val subFilesResults = includeFiles.map { value ->
            value to async(filesProcessingContext) { generateData(value.first.file, nodes, value.second) }
        }.map { (value, future) ->
            val processResults: List<FileProcessData> = future.await()

            if (processResults.size > 1)
                throw RuntimeException("multiple nested files not supported for this layout")
            else if (processResults.isEmpty())
                throw RuntimeException("processing result is empty for file '${value.first.file}'")

            value.first to processResults.first().copy(relativePath = value.second)
        }.toMap()

        val result = mutableMapOf(
            "data" to node
        )
        version?.let { result += "version" to it }
        title?.let { result += "title" to it }
        lastUpdate?.let { result += "lastUpdate" to it }
        result += "files" to IncludeFilesInfo(subFilesResults.map { (serviceNode, result) ->
            IncludeFileInfo(
                link = result.relativePath.toLink(relativePath),
                name = serviceNode.name,
                title = serviceNode.title
            )
        })

        listOf(node).fixRelativeLinkPaths(relativePath)

        return ProcessResult(
            relativePath = relativePath,
            result = result,
            replaceNodes = emptyMap(),
            serviceNodes = serviceNodes,
            childs = subFilesResults.values.toList()
        )
    }

    private fun String.toPreparedName(): String = this
        .map { c ->
            val position = abcCyr[c.toString()] ?: -1
            if (position >= 0) {
                abcLat[position]
            } else {
                   c.toString()
            }
        }
        .joinToString("")
        .replace("[^a-zA-Zа-яА-Я0-9]+".toRegex(IGNORE_CASE), "_")
        .toLowerCase() + ".${DocumentGenerator.MD_EXTENSION}"

    private val abcCyr = arrayOf(
        "а",
        "б",
        "в",
        "г",
        "д",
        "е",
        "ё",
        "ж",
        "з",
        "и",
        "й",
        "к",
        "л",
        "м",
        "н",
        "о",
        "п",
        "р",
        "с",
        "т",
        "у",
        "ф",
        "х",
        "ц",
        "ч",
        "ш",
        "щ",
        "ъ",
        "ы",
        "ь",
        "э",
        "ю",
        "я",
        "А",
        "Б",
        "В",
        "Г",
        "Д",
        "Е",
        "Ё",
        "Ж",
        "З",
        "И",
        "Й",
        "К",
        "Л",
        "М",
        "Н",
        "О",
        "П",
        "Р",
        "С",
        "Т",
        "У",
        "Ф",
        "Х",
        "Ц",
        "Ч",
        "Ш",
        "Щ",
        "Ъ",
        "Ы",
        "Ь",
        "Э",
        "Ю",
        "Я"
    ).mapIndexed { index, symbol -> symbol to index }
        .toMap()

    private val abcLat = arrayOf(
        "a",
        "b",
        "v",
        "g",
        "d",
        "e",
        "e",
        "zh",
        "z",
        "i",
        "y",
        "k",
        "l",
        "m",
        "n",
        "o",
        "p",
        "r",
        "s",
        "t",
        "u",
        "f",
        "h",
        "ts",
        "ch",
        "sh",
        "sch",
        "",
        "i",
        "",
        "e",
        "ju",
        "ja",
        "A",
        "B",
        "V",
        "G",
        "D",
        "E",
        "E",
        "Zh",
        "Z",
        "I",
        "Y",
        "K",
        "L",
        "M",
        "N",
        "O",
        "P",
        "R",
        "S",
        "T",
        "U",
        "F",
        "H",
        "Ts",
        "Ch",
        "Sh",
        "Sch",
        "",
        "I",
        "",
        "E",
        "Ju",
        "Ja"
    )
}




