package ru.alfabank.ecomm.dcreator.parser.common

expect class Matcher {
    fun find(): Boolean
    fun group(groupName: String): String?
}

expect class Pattern {
    fun matcher(firstLine: String): Matcher
    fun asPredicate(): Predicate

    companion object {
        val UNICODE_CHARACTER_CLASS: Int
        val CASE_INSENSITIVE: Int
    }
}

expect class Predicate : (String) -> Boolean {
    fun test(str: String): Boolean
}

expect fun String.toPattern(): Pattern

expect fun String.toPattern(flags: Int): Pattern
