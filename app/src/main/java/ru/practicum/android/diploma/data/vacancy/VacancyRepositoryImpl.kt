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
                val vacancyResponse = response as? VacancyResponse
                if (vacancyResponse != null) {
                    val data = VacancyMapper.mapToDomain(vacancyResponse.result)
                    Resource.Success(data)
                } else {
                    Resource.Error(ResponseStatus.UNKNOWN_ERROR)
                }
            }
            else -> {
                Resource.Error(response.status)
            }
        }
    }
}
