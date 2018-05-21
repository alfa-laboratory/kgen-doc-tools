package ru.alfabank.ecomm.dcreator.nodes

interface ServiceNode : Node {
    fun toParams(): Any
}

data class SimpleServiceNode(
    val name: String,
    val value: String?
) : ServiceNode, Node by NodeIdGen() {
    override fun toParams(): Any = TODO()
}

data class EmbeddedServiceNode(
    val embedded: Boolean
): ServiceNode, Node by NodeIdGen() {
    override fun toParams(): Any = embedded
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

data class IncludeServiceNode(
    val name: String,
    val file: String
) : ServiceNode, Node by NodeIdGen() {
    override fun toParams(): Any = TODO()
}
