package ru.alfabank.ecomm.dcreator.utils

import ru.alfabank.ecomm.dcreator.nodes.Node
import ru.alfabank.ecomm.dcreator.nodes.TextBlockNode

fun Node.toTextBlockNode(): TextBlockNode = TextBlockNode(listOf(this))

fun List<Node>.toTextBlockNode(): TextBlockNode = TextBlockNode(this)