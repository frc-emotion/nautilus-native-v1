package org.nautilusapp.nautilus

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
    data class Success<T>(val data: T) : Result<T, Nothing>

    data class Error<E>(val error: E) : Result<Nothing, E>

    companion object {
        fun <T> unwrapOrNull(result: Result<T, *>): T? {
            return when (result) {
                is Success -> result.data
                is Error -> null
            }
        }
    }

    fun unwrapOrNull(): T? {
        return when (this) {
            is Success -> this.data
            is Error -> null
        }
    }

    fun unwrapOrHandle(handler: (E) -> Unit): T? {
        return when (this) {
            is Success -> this.data
            is Error -> handler(this.error).let { null }
        }
    }
}

data class OkOrMessage(
    val success: Boolean,
    val message: String?
)