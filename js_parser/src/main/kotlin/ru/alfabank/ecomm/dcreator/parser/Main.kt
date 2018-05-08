package ru.alfabank.ecomm.dcreator.parser

import fs.readFile

fun main(args: Array<String>) {
    println("Hello JavaScript! 222")

    val currentPath = path.resolve(".")
    println(currentPath)

    readFile("./js_parser/src/test/kotlin/ru/alfabank/ecomm/dcreator/common/Test.kt") { err, data ->
        println(data.toString())

        val intMax = Int.MAX_VALUE
        println(intMax)

        val longMax = Long.MAX_VALUE
        println(longMax)
    }

//    val express = require("express")
//    val app = express()
//
//    app.get("/", { req, res ->
//        res.type("text/plain")
//        res.send("i am a beautiful butterfly")
//    })
//
//    app.listen(3000, {
//        println("Listening on port 3000")
//    })
}