package ru.alfabank.ecomm.dcreator.common

import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test as JTest

actual typealias Test = JTest

actual fun assertEquals(expect: Any?, actual: Any?) {
    assertEquals(expect, actual)
}