package ru.alfabank.ecomm.dcreator.test

import kotlinx.coroutines.experimental.runBlocking

actual fun runTest(block: suspend () -> Unit) = runBlocking { block() }