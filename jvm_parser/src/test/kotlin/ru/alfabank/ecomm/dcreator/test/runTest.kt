package ru.alfabank.ecomm.dcreator.test

import kotlinx.coroutines.runBlocking

actual fun runTest(block: suspend () -> Unit) = runBlocking { block() }