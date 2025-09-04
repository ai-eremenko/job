package ru.practicum.android.diploma.data.dto.responses

import ru.practicum.android.diploma.util.ResponseStatus

open class Response {
    var status: ResponseStatus = ResponseStatus.UNKNOWN_ERROR
    var errorMessage: String? = null
}
