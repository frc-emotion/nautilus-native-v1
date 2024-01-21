package org.team2658.nautilus

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

sealed interface Result<T, E> {
    data class Success<T, E>(val data: T): Result<T, E>
    data class Error<T, E>(val message: E): Result<T, E>
}