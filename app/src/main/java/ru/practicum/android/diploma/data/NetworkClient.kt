package ru.practicum.android.diploma.data

import ru.practicum.android.diploma.data.dto.RequestDto
import ru.practicum.android.diploma.data.dto.responses.Response

interface NetworkClient {
    suspend fun doRequest(dto: RequestDto): Response
}
