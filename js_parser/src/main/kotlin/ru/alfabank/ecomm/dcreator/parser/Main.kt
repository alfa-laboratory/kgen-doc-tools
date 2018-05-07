package ru.alfabank.ecomm.dcreator.parser

import fs.readFileSync

fun main(args: Array<String>) {
    println("Hello JavaScript!")

    val currentPath = path.resolve(".")
    println(currentPath)

    val result = readFileSync("./src/test/kotlin/ru/alfabank/ecomm/dcreator/common/Test.kt", "UTF-8")
    println(result)

    val intMax = Int.MAX_VALUE
    println(intMax)

    val longMax = Long.MAX_VALUE
    println(longMax)

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