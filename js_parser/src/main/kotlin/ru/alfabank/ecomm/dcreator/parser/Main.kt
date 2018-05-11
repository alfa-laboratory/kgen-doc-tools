//package ru.alfabank.ecomm.dcreator.parser
//
//import ru.alfabank.ecomm.dcreator.js.types.RegexpResult
//import ru.alfabank.ecomm.dcreator.js.types.UUID
//import ru.alfabank.ecomm.dcreator.js.types.namedRegexp
//
////import fs.readFile
//
////import ru.alfabank.ecomm.dcreator.js.types.require
//
////private val namedRegexp = require<NamedRegexp>("named-js-regexp")
//
//fun main(args: Array<String>) {
//    println("Hello JavaScript! 222")
////
////    val currentPath = path.resolve(".")
////    println(currentPath)
////
////    readFile("./js_parser/src/test/kotlin/ru/alfabank/ecomm/dcreator/common/Test.kt") { err, data ->
////        println(data.toString())
////
////        val intMax = Int.MAX_VALUE
////        println(intMax)
////
////        val longMax = Long.MAX_VALUE
////        println(longMax)
////    }
//
//    val testRegex = namedRegexp("(?<hours>\\d\\d?):(?<minutes>\\d\\d?)(:(?<seconds>\\d\\d?))?", "g")
//    var result: RegexpResult?
//
//    do {
//        result = testRegex.exec("1:2; 3:4")
//
//        if (result !=null) {
//            println(result.group("hours"))
//            println(result.group("minutes") ?: "notFound")
//            println(result.group("seconds") ?: "notFound")
//        }
//    } while (result != null)
//
//    println("==========")
//    println(testRegex.test(""))
//    println(testRegex.test("1:2"))
//
//    println("==========")
//    println(UUID.v4())
//
//    println("==========")
//    val testRegex2 = namedRegexp("^[\\s\\w]*>(?<text>.*)\$")
//    println(testRegex2.exec("> line1"))
//    val testRegex3 = namedRegexp("^[\\s\\w]*>(?<text>.*)\$")
//    println(testRegex3.exec("> line2"))
//}