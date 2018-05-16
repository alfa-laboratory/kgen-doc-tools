package ru.alfabank.ecomm.dcreator.nodes

interface ServiceNode : Node

data class SimpleSeviceNode(
    val name: String,
    val value: String
) : ServiceNode, Node by NodeIdGen()

data class LayoutServiceNode(
    val layout: String
) : ServiceNode, Node by NodeIdGen()

data class TitleServiceNode(
    val title: String
) : ServiceNode, Node by NodeIdGen()

data class IncludeServiceNode(
    val name: String,
    val file: String
) : ServiceNode, Node by NodeIdGen()
