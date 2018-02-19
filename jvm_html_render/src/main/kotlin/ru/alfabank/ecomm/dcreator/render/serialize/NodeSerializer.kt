package ru.alfabank.ecomm.dcreator.render.serialize

import com.fasterxml.jackson.core.JsonGenerator
import com.fasterxml.jackson.databind.JsonSerializer
import com.fasterxml.jackson.databind.ObjectMapper
import com.fasterxml.jackson.databind.SerializationFeature
import com.fasterxml.jackson.databind.SerializerProvider
import com.fasterxml.jackson.databind.module.SimpleModule
import com.fasterxml.jackson.module.kotlin.registerKotlinModule
import ru.alfabank.ecomm.dcreator.nodes.*
import ru.alfabank.ecomm.dcreator.render.process.HeaderAnchor
import ru.alfabank.ecomm.dcreator.render.process.HeaderLink
import kotlin.reflect.KClass
import kotlin.reflect.full.declaredMemberProperties
import kotlin.reflect.jvm.javaGetter

class NodeSerializer(
        private val render: Render,
        private val replaceNodes: Map<String, Node>
) {
    val templates: MutableMap<KClass<out Node>, String> = mutableMapOf(
            HeaderBlockNode::class to "nodes/header_node.ftlh",
            TextNode::class to "nodes/text.ftlh",
            BoldNode::class to "nodes/bold.ftlh",
            ItalicNode::class to "nodes/italic.ftlh",
            StrikethroughNode::class to "nodes/strikethrough.ftlh",
            UnderlineNode::class to "nodes/underline.ftlh",
            HTMLNode::class to "nodes/htmlnode.ftlh",
            CodeSampleNode::class to "nodes/code_sample.ftlh",
            CodeBlockNode::class to "nodes/code_block.ftlh",
            LinkNode::class to "nodes/link.ftlh",
            ImageLinkNode::class to "nodes/image_link.ftlh",
            ListsBLockNode::class to "nodes/lists.ftlh",
            OrderedListNode::class to "nodes/ordered_list_item.ftlh",
            UnOrderedListNode::class to "nodes/un_ordered_list_item.ftlh",
            BlockLayout::class to "nodes/block_layout.ftlh",
            RowLayout::class to "nodes/row_layout.ftlh",
            TextBlockNode::class to "nodes/text_block.ftlh",
            BlockquotesBlockNode::class to "nodes/blockquotesblock.ftlh",
            TableBlockNode::class to "nodes/table.ftlh",
            HeaderLink::class to "special/header_link.ftlh",
            HeaderAnchor::class to "special/header_anchor.ftlh"
    )

    fun writeNodeToString(node: Node): String {
        val templatePath = templates[node::class]
                ?: throw RuntimeException("not found template for: ${node::class}")

        if (node.nodeId in replaceNodes) {
            return writeNodeToString(replaceNodes[node.nodeId]!!)
        }

        val properties = node::class.declaredMemberProperties

        val parameters = properties.map { property ->
            val fieldName = property.name
            val fieldValue = property.javaGetter!!.invoke(node)

            fieldName to processValue(fieldValue)
        }
                .filter { it.second != null }
                .toMap(mutableMapOf())

        val result = render.render(templatePath, parameters)

        return result
    }

    private val module = SimpleModule().apply {
        addSerializer(Node::class.java, NodeSerializer())
    }
    private val mapper = ObjectMapper()
            .registerKotlinModule()
            .configure(SerializationFeature.INDENT_OUTPUT, true)
            .registerModule(module)

    private inner class NodeSerializer<T : Node> : JsonSerializer<T>() {

        override fun serialize(value: T, gen: JsonGenerator, serializers: SerializerProvider) {
            val result = writeNodeToString(value)
            gen.writeRaw(result)
        }

    }

    private fun processValue(value: Any?): Any? = when (value) {
        is Node -> mapper.writeValueAsString(value)
        is List<*> -> value.map { processValue(it) }
        else -> value
    }


}
