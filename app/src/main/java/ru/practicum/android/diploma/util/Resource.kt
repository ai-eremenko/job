package ru.practicum.android.diploma.util

sealed class Resource<T>(val data: T? = null, val message: ResponseStatus? = null) {
    class Success<T>(data: T) : Resource<T>(data)
    class Error<T>(message: ResponseStatus, data: T? = null) : Resource<T>(data, message)
}
