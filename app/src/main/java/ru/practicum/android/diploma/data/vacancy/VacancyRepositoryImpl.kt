package ru.practicum.android.diploma.data.vacancy

import ru.practicum.android.diploma.data.NetworkClient
import ru.practicum.android.diploma.data.dto.RequestDto
import ru.practicum.android.diploma.data.dto.responses.VacancyResponse
import ru.practicum.android.diploma.data.mappers.VacancyMapper
import ru.practicum.android.diploma.domain.vacancy.VacancyRepository
import ru.practicum.android.diploma.domain.vacancy.models.Vacancy
import ru.practicum.android.diploma.util.Resource
import ru.practicum.android.diploma.util.ResponseStatus

class VacancyRepositoryImpl(
    private val networkClient: NetworkClient
) : VacancyRepository {

    override suspend fun getVacancyById(id: String): Resource<Vacancy> {
        val response = networkClient.doRequest(RequestDto.VacancyRequest(id))
        return when (response.status) {
            ResponseStatus.SUCCESS -> {
                if (response is VacancyResponse) {
                    val data = VacancyMapper.mapToDomain(response.result)
                    Resource.Success(data)
                } else {
                    Resource.Error(ResponseStatus.NOT_FOUND)
                }
            }
            else -> {
                Resource.Error(response.status)
            }
        }
    }
}
