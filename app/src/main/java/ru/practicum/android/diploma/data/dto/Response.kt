package ru.practicum.android.diploma.data.dto

enum class ResponseStatus {
    SUCCESS, // Успешный запрос (200)
    NOT_FOUND, // Ничего не найдено (404)
    SERVER_ERROR, // Внутренняя ошибка сервера (500)
    UNKNOWN_ERROR, // Неизвестная ошибка
    NO_INTERNET,      // Нет интернета
}

open class Response {
    var status: ResponseStatus = ResponseStatus.UNKNOWN_ERROR
}
