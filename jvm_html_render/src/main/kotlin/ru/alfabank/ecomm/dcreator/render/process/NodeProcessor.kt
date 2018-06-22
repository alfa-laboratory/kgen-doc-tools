package ru.alfabank.ecomm.dcreator.render.process

import ru.alfabank.ecomm.dcreator.nodes.*
import ru.alfabank.ecomm.dcreator.render.DocumentGenerator
import ru.alfabank.ecomm.dcreator.render.DocumentGenerator.RelativePagePath

const val DEFAULT_TITLE = "default title"

data class ProcessResult(
    val relativePath: RelativePagePath,
    val result: Map<String, Node>,
    val replaceNodes: Map<String, Node>,
    val serviceNodes: List<ServiceNode>,
    val childs: List<FileProcessData> = emptyList()
)

data class FileProcessData(
    val data: String,
    val serviceNodes: List<ServiceNode>,
    val relativePath: RelativePagePath
)

inline fun <reified T : ServiceNode> List<ServiceNode>.findServiceNode(): T? = this
    .asSequence()
    .filterIsInstance<T>()
    .firstOrNull()

interface NodeProcessor {
    suspend fun process(
        node: Node,
        serviceNodes: List<ServiceNode>,
        relativePath: DocumentGenerator.RelativePagePath
    ): ProcessResult
}

private fun String?.isUrlRelative(): Boolean = this != null
    && this.isNotEmpty()
    && this[0] != '/'
    && (this.length < 4 || this.take(4) != "http")

tailrec fun List<Node>.fixRelativeLinkPaths(relativePath: RelativePagePath): MutableMap<String, Node> =
    if (this.isNotEmpty()) {
        val first = this.first()
        val last = this.drop(1)

        when (first) {
            is ImageLinkNode -> {
                if (first.url.isUrlRelative()) {
                    val fixedLink = relativePath.fromPath(first.url!!)
                        .toLink(relativePath)
                    first.url = fixedLink

                    last.fixRelativeLinkPaths(relativePath)
                } else {
                    last.fixRelativeLinkPaths(relativePath)
                }
            }
            is LinkNode -> {
                if (first.url.isUrlRelative()) {
                    val fixedLink = relativePath.fromPath(first.url!!)
                        .toLink(relativePath)
                    first.url = fixedLink

                    (last + first.node).fixRelativeLinkPaths(relativePath)
                } else {
                    last.fixRelativeLinkPaths(relativePath)
                }
            }
            is NestedNodeList<*> -> (last + first.nodes).fixRelativeLinkPaths(relativePath)
            is NestedNode -> (last + first.node).fixRelativeLinkPaths(relativePath)
            else -> last.fixRelativeLinkPaths(relativePath)
        }
    } else {
        mutableMapOf()
    }

internal fun String.toPreparedName(): String = this
    .map { c ->
        val position = abcCyr[c.toString()] ?: -1
        if (position >= 0) {
            abcLat[position]
        } else {
            c.toString()
        }
    }
    .joinToString("")
    .replace("[^a-zA-Zа-яА-Я0-9]+".toRegex(RegexOption.IGNORE_CASE), "_")
    .toLowerCase()

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
