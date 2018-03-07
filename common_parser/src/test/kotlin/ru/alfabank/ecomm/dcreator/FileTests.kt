package ru.alfabank.ecomm.dcreator

import ru.alfabank.ecomm.dcreator.parser.MarkdownParser

class FileTests {
    private val parser = MarkdownParser()

    @Test
    fun `full text test`() {
        val file = File(this.javaClass.getResource("/complex_example.md").file)

        val retries = 500

        val sum = (1..retries).map {
            val start = LocalTime.now()
            val result = parser.parse(file)
            val end = LocalTime.now()

            Duration.between(start,end).toMillis()
        }.fold(0.0) { acc, l -> acc + l }

        //8.608
        println(sum / retries)
    }

    //
//    @Test
//    fun `full text test attlasian`() {
//        val file = File(this.javaClass.getResource("/md_examples/complex_example.md").file)
//
//        val attlasianParser = Parser.builder().build()
//        val document = attlasianParser.parseForLineResult(file.readText())
//
//        println(document)
//    }
}
