package ru.practicum.android.diploma.data.network

import retrofit2.HttpException
import ru.practicum.android.diploma.BuildConfig
import ru.practicum.android.diploma.data.NetworkClient
import ru.practicum.android.diploma.data.dto.RequestDto
import ru.practicum.android.diploma.data.dto.responses.AreasResponse
import ru.practicum.android.diploma.data.dto.responses.IndustriesResponse
import ru.practicum.android.diploma.data.dto.responses.Response
import ru.practicum.android.diploma.data.dto.responses.VacanciesResponse
import ru.practicum.android.diploma.data.dto.responses.VacancyResponse
import ru.practicum.android.diploma.util.NetworkManager
import ru.practicum.android.diploma.util.ResponseStatus
import java.net.SocketTimeoutException

class RetrofitClient(
    private val api: VacanciesApi,
    private val networkManager: NetworkManager
) : NetworkClient {

    override suspend fun doRequest(dto: RequestDto): Response {
        if (!networkManager.isConnected()) {
            return Response().apply { status = ResponseStatus.NO_INTERNET }
        }

        return try {
            val response = executeRequest(dto)
            response.apply { status = ResponseStatus.SUCCESS }
        } catch (e: SocketTimeoutException) {
            Response().apply {
                status = ResponseStatus.NO_INTERNET
                errorMessage = "Timeout: ${e.message}"
            }
        } catch (e: HttpException) {
            handleHttpException(e)
        }
    }

    private suspend fun executeRequest(dto: RequestDto): Response {
        val token = "Bearer ${BuildConfig.API_ACCESS_TOKEN}"

        return when (dto) {
            RequestDto.IndustriesRequest -> handleIndustriesRequest(token)
            RequestDto.AreasRequest -> handleAreasRequest(token)
            is RequestDto.VacanciesRequest -> handleVacanciesRequest(token, dto)
            is RequestDto.VacancyRequest -> handleVacancyRequest(token, dto)
        }
    }

    private suspend fun handleIndustriesRequest(token: String): Response {
        val industries = api.getIndustries(token)
        return IndustriesResponse(industries)
    }

    private suspend fun handleAreasRequest(token: String): Response {
        val areas = api.getAreas(token)
        return AreasResponse(areas)
    }

    private suspend fun handleVacanciesRequest(
        token: String,
        request: RequestDto.VacanciesRequest
    ): Response {
        val vacanciesDto = api.getVacancies(
            token,
            request.expression,
            request.page,
            request.options,
            request.onlyWithSalary
        )
        return VacanciesResponse(
            found = vacanciesDto.found,
            pages = vacanciesDto.pages,
            page = vacanciesDto.page,
            items = vacanciesDto.items
        )
    }

    private suspend fun handleVacancyRequest(
        token: String,
        request: RequestDto.VacancyRequest
    ): Response {
        return try {
            val vacancyDto = api.getVacancyById(token, request.id)
            VacancyResponse(vacancyDto).apply {
                status = ResponseStatus.SUCCESS
            }
        } catch (e: HttpException) {
            handleHttpException(e)
        }
    }

    private fun handleHttpException(e: HttpException): Response {
        return when (e.code()) {
            HTTP_NOT_FOUND -> Response().apply { status = ResponseStatus.NOT_FOUND }
            HTTP_SERVER_ERROR -> Response().apply { status = ResponseStatus.SERVER_ERROR }
            else -> Response().apply {
                status = ResponseStatus.UNKNOWN_ERROR
                errorMessage = "HTTP error: ${e.code()} - ${e.message()}"
            }
        }
    }

    companion object {
        const val HTTP_NOT_FOUND = 404
        const val HTTP_SERVER_ERROR = 500
    }
}
