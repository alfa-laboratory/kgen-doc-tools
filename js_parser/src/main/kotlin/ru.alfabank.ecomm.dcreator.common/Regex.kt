package ru.alfabank.ecomm.dcreator.common

import ru.alfabank.ecomm.dcreator.js.types.Regexp
import ru.alfabank.ecomm.dcreator.js.types.RegexpResult
import ru.alfabank.ecomm.dcreator.js.types.namedRegexp

actual class Matcher(private val regexp: Regexp, private val line: String) {
    private var findResult: RegexpResult? = null

    actual fun find(): Boolean {
        findResult = regexp.exec(line)

        return findResult != null
    }

    actual fun group(groupName: String): String? = findResult?.group(groupName)
}

actual class Pattern(regexString: String, flags: Int? = null) {
    private val regexp: () -> Regexp = {
        when (flags) {
            UNICODE_CHARACTER_CLASS -> namedRegexp(regexString, "g", "u")
            CASE_INSENSITIVE -> namedRegexp(regexString, "g", "i")
            else -> namedRegexp(regexString, "g")
        }
    }

    actual fun matcher(firstLine: String): Matcher = Matcher(regexp(), firstLine)
    actual fun asPredicate(): Predicate = Predicate(regexp)

    actual companion object {
        actual val UNICODE_CHARACTER_CLASS: Int = 0
        actual val CASE_INSENSITIVE: Int = 1
    }
}

actual class Predicate(val regexp: () -> Regexp) {
    actual operator fun invoke(str: String): Boolean = regexp().test(str)

    actual fun test(str: String): Boolean = regexp().test(str)
}

actual fun String.toPattern(): Pattern = Pattern(this)

actual fun String.toPattern(flags: Int): Pattern = Pattern(this, flags)