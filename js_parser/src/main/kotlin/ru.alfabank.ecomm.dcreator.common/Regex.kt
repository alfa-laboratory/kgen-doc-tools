//package ru.alfabank.ecomm.dcreator.common
//
//import java.util.regex.Matcher as JMatcher
//import java.util.regex.Pattern as JPattern
//
//actual class Matcher(private val matcher: JMatcher) {
//    actual fun find(): Boolean = matcher.find()
//    actual fun group(groupName: String): String? = matcher.group(groupName)
//}
//
//actual class Pattern(private val pattern: JPattern) {
//    actual fun matcher(firstLine: String): Matcher = Matcher(pattern.matcher(firstLine))
//    actual fun asPredicate(): Predicate = Predicate(pattern.asPredicate()::test)
//
//    actual companion object {
//        actual val UNICODE_CHARACTER_CLASS: Int = java.util.regex.Pattern.UNICODE_CHARACTER_CLASS
//        actual val CASE_INSENSITIVE: Int = java.util.regex.Pattern.CASE_INSENSITIVE
//    }
//}
//
//actual class Predicate(private val predicate: (String) -> Boolean) : (String) -> Boolean {
//    override fun invoke(p1: String): Boolean = predicate(p1)
//    actual fun test(str: String): Boolean = predicate(str)
//}
//
//actual fun String.toPattern(): Pattern = Pattern(JPattern.compile(this))
//
//actual fun String.toPattern(flags: Int): Pattern = Pattern(JPattern.compile(this, flags))