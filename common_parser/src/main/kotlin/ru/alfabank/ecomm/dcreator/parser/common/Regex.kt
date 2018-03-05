package ru.alfabank.ecomm.dcreator.parser.common

expect enum class Modifiers {
    UNICODE_CHARACTER_CLASS,
    CASE_INSENSITIVE
}

expect class Matcher {
    fun find(): Boolean
    fun group(groupName: String): String?
}

expect class Pattern {
    fun matcher(firstLine: String): Matcher
    fun asPredicate(): Predicate
}

expect class Predicate: (String) -> Boolean {
    fun test(str: String): Boolean
}

expect fun String.toPattern(): Pattern

expect fun String.toPattern(vararg args: Modifiers): Pattern
