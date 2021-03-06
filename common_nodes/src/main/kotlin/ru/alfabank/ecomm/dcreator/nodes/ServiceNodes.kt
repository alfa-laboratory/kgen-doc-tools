package ru.alfabank.ecomm.dcreator.nodes

interface ServiceNode : Node {
    fun toParams(): Any
}

data class SimpleServiceNode(
    val value: String,
    val param1: String? = null,
    val param2: String? = null
) : ServiceNode, Node by NodeIdGen() {
    override fun toParams(): Any = mapOf<String, Any?>(
        "value" to value,
        "param1" to param1,
        "param2" to param2
    )
}

data class LayoutServiceNode(
    val layout: String
) : ServiceNode, Node by NodeIdGen(){
    override fun toParams(): Any = TODO()
}

data class TitleServiceNode(
    val title: String
) : ServiceNode, Node by NodeIdGen() {
    override fun toParams(): Any = title
}

data class BackUrlServiceNode(
    val url: String
) : ServiceNode, Node by NodeIdGen() {
    override fun toParams(): Any = url
}

data class VersionServiceNode(
    val version: String
) : ServiceNode, Node by NodeIdGen() {
    override fun toParams(): Any = version
}

data class LastUpdateServiceNode(
    val date: String
) : ServiceNode, Node by NodeIdGen() {
    override fun toParams(): Any = date
}

data class IncludeServiceNode(
    val file: String,
    val name: String,
    val title: String?
) : ServiceNode, Node by NodeIdGen() {
    override fun toParams(): Any = TODO()
}
