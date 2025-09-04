package ru.practicum.android.diploma.data.network

import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import retrofit2.HttpException
import ru.practicum.android.diploma.BuildConfig
import ru.practicum.android.diploma.data.NetworkClient
import ru.practicum.android.diploma.data.dto.RequestDto
import ru.practicum.android.diploma.data.dto.responses.AreasResponse
import ru.practicum.android.diploma.data.dto.responses.IndustriesResponse
import ru.practicum.android.diploma.data.dto.responses.Response
import ru.practicum.android.diploma.data.dto.responses.ResponseStatus
import ru.practicum.android.diploma.data.dto.responses.VacanciesResponse
import ru.practicum.android.diploma.util.NetworkManager

class RetrofitClient(
    private val api: VacanciesApi,
    private val networkManager: NetworkManager
) : NetworkClient {

    override suspend fun doRequest(dto: RequestDto): Response {
        if (!networkManager.isConnected()) Response().apply { status = ResponseStatus.NO_INTERNET }

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

                    is RequestDto.VacanciesRequest -> {
                        val vacanciesDto = api.getVacancies(
                            token,
                            dto.expression,
                            dto.page
                        )
                        VacanciesResponse(
                            found = vacanciesDto.found,
                            pages = vacanciesDto.pages,
                            page = vacanciesDto.page,
                            items = vacanciesDto.items
                        )
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
