package ru.alfabank.ecomm.dcreator.render.serialize

interface Render {
    fun render(path: String, parameters: Map<String, Any?>): String
}