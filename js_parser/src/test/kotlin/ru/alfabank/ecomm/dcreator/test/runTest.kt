package ru.alfabank.ecomm.dcreator.test

import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.promise

actual fun runTest(block: suspend () -> Unit): dynamic = GlobalScope.promise { block() }