package ru.alfabank.ecomm.dcreator.nodes

interface Layout : NestedNodeList<Node>

data class RowLayout(override val nodes: List<Node> = emptyList()) : Layout, Node by NodeIdGen()

data class BlockLayout(override val nodes: List<BlockNode> = emptyList()) : Layout, BlockNode, Node by NodeIdGen()