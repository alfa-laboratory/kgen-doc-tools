package ru.alfabank.ecomm.dcreator.test

import kotlinx.coroutines.experimental.promise

actual fun runTest(block: suspend () -> Unit): dynamic = promise { block() }