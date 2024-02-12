package org.nautilusapp.nautilus

import kotlin.jvm.JvmInline

fun String.toCapitalized(): String {
    return this.substring(0, 1).uppercase() + this.lowercase().substring(1)
}

data class KeyValue<T>(val key: String, val value: T)

fun <T> setKeyValueListItem(key: String, list: MutableList<KeyValue<T>>, value: T) {
    for ((index, item) in list.withIndex()) {
        if (item.key == key) {
            list[index] = KeyValue(key, value)
            return
        }
    }
}

sealed interface Result<out T, out E> {
    @JvmInline
    value class Success<T>(val data: T): Result<T, Nothing>
    @JvmInline
    value class Error<E>(val error: E): Result<Nothing, E>

    companion object {
        fun <T>unwrapOrNull(result: Result<T, *>): T? {
            return when(result) {
                is Success -> result.data
                is Error -> null
            }
        }
    }
}