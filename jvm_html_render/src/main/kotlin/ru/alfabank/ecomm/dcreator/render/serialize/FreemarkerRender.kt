package ru.alfabank.ecomm.dcreator.render.serialize

import freemarker.template.Configuration
import freemarker.template.TemplateExceptionHandler
import java.io.File
import java.io.StringWriter

class FreemarkerRender(private val layoutFile: File) : Render {
    constructor(layoutPath: String) : this(File(layoutPath))

    private val cfg = Configuration(Configuration.VERSION_2_3_27).apply {
        setDirectoryForTemplateLoading(layoutFile)
        defaultEncoding = "UTF-8"
        templateExceptionHandler = TemplateExceptionHandler.RETHROW_HANDLER
        logTemplateExceptions = false
        wrapUncheckedExceptions = true
    }

    override fun render(path: String, parameters: Map<String, Any?>): String {
        val template = cfg.getTemplate(path)

        val buffer = StringWriter()
        template.process(parameters, buffer)

        return buffer.toString()
    }
}