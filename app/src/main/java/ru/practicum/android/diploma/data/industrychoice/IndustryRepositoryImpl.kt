package ru.practicum.android.diploma.data.industrychoice

import ru.practicum.android.diploma.data.NetworkClient
import ru.practicum.android.diploma.data.dto.RequestDto
import ru.practicum.android.diploma.data.dto.responses.IndustriesResponse
import ru.practicum.android.diploma.data.industrychoice.mapper.DtoToIndustryMapper
import ru.practicum.android.diploma.domain.industrychoice.IndustryRepository
import ru.practicum.android.diploma.domain.industrychoice.models.Industry
import ru.practicum.android.diploma.util.Resource
import ru.practicum.android.diploma.util.ResponseStatus

class IndustryRepositoryImpl(
    private val networkClient: NetworkClient
) : IndustryRepository {
    override suspend fun getIndustries(): Resource<List<Industry>> {
        val response = networkClient.doRequest(RequestDto.IndustriesRequest)

        return when (response.status) {
            ResponseStatus.SUCCESS -> {
                Resource.Success((response as IndustriesResponse).result.map { dto ->
                    DtoToIndustryMapper.map(dto)
                })
            }

            else -> Resource.Error(response.status)
        }
    }
}
