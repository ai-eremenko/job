package ru.practicum.android.diploma.data.network

import android.content.Context
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import retrofit2.HttpException
import ru.practicum.android.diploma.BuildConfig
import ru.practicum.android.diploma.data.NetworkClient
import ru.practicum.android.diploma.data.dto.AreasResponse
import ru.practicum.android.diploma.data.dto.IndustriesResponse
import ru.practicum.android.diploma.data.dto.RequestDto
import ru.practicum.android.diploma.data.dto.Response
import ru.practicum.android.diploma.util.ResponseStatus

class RetrofitClient(
    private val api: VacanciesApi,
    private val context: Context
) : NetworkClient {

    override suspend fun doRequest(dto: RequestDto): Response {
        // добавить проверку на наличие интернета

        return withContext(Dispatchers.IO) {
            try {
                val token = BuildConfig.API_ACCESS_TOKEN
                val response = when (dto) {
                    RequestDto.IndustriesRequest -> {
                        val industries = api.getIndustries(token)
                        IndustriesResponse(industries)
                    }

                    RequestDto.AreasRequest -> {
                        val areas = api.getAreas(token)
                        AreasResponse(areas)
                    }
                }
                response.apply { status = ResponseStatus.SUCCESS }
            } catch (e: HttpException) {
                when (e.code()) {
                    HTTP_NOT_FOUND -> Response().apply { status = ResponseStatus.NOT_FOUND }
                    HTTP_SERVER_ERROR -> Response().apply { status = ResponseStatus.SERVER_ERROR }
                    else -> Response().apply { status = ResponseStatus.UNKNOWN_ERROR }
                }
            }
        }
    }

    companion object {
        const val HTTP_NOT_FOUND = 404
        const val HTTP_SERVER_ERROR = 500
    }
}
