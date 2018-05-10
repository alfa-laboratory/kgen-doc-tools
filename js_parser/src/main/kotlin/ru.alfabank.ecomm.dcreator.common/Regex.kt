package ru.alfabank.ecomm.dcreator.common

import ru.alfabank.ecomm.dcreator.js.types.RegexpResult
import ru.alfabank.ecomm.dcreator.js.types.namedRegexp

actual class Matcher(private val pattern: Pattern, private val line: String) {
    private var findResult: RegexpResult? = null

    actual fun find(): Boolean {
        findResult = pattern.regexp.exec(line)

        return findResult != null
    }

    actual fun group(groupName: String): String? = findResult?.group(groupName)
}

actual class Pattern(private val regex: String, private val flags: Int? = null) {
    //TODO add flags
    val regexp = namedRegexp(regex, "g")

    actual fun matcher(firstLine: String): Matcher = Matcher(this, firstLine)
    actual fun asPredicate(): Predicate = Predicate(this)

    actual companion object {
        actual val UNICODE_CHARACTER_CLASS: Int = 0
        actual val CASE_INSENSITIVE: Int = 1
    }
}

actual class Predicate(val pattern: Pattern) {
    actual operator fun invoke(str: String): Boolean = pattern.regexp.test(str)

    actual fun test(str: String): Boolean = pattern.regexp.test(str)
}

actual fun String.toPattern(): Pattern = Pattern(this)

actual fun String.toPattern(flags: Int): Pattern = Pattern(this, flags)