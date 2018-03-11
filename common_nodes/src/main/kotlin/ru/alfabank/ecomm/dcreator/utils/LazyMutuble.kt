package ru.alfabank.ecomm.dcreator.utils

import kotlin.jvm.Volatile
import kotlin.properties.ReadWriteProperty
import kotlin.reflect.KProperty

private object UNINITIALIZED_VALUE

class LazyMutable<T>(val initializer: () -> T) : ReadWriteProperty<Any?, T> {
    @Volatile
    var value: Any? = UNINITIALIZED_VALUE

    @Suppress("UNCHECKED_CAST")
    override fun getValue(thisRef: Any?, property: KProperty<*>) = if (value == UNINITIALIZED_VALUE) {
        synchronized(this) {
            if (value == UNINITIALIZED_VALUE)
                initializer().also { value = it }
            else
                value as T
        }
    } else
        value as T

    override fun setValue(thisRef: Any?, property: KProperty<*>, value: T) = synchronized(this) {
        this.value = value
    }
}