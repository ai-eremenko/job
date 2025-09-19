package ru.practicum.android.diploma.data.areas

import ru.practicum.android.diploma.data.NetworkClient
import ru.practicum.android.diploma.data.dto.RequestDto
import ru.practicum.android.diploma.data.dto.responses.AreasResponse
import ru.practicum.android.diploma.data.mappers.AreasMapper.mapToDomain
import ru.practicum.android.diploma.domain.areas.AreasRepository
import ru.practicum.android.diploma.domain.areas.models.Area
import ru.practicum.android.diploma.util.Resource
import ru.practicum.android.diploma.util.ResponseStatus

class AreasRepositoryImpl(
    private val networkClient: NetworkClient
) : AreasRepository {

    private var cachedAreas: Resource<List<Area>>? = null

    override suspend fun getAreas(): Resource<List<Area>> {
        cachedAreas?.let {
            return it
        }

        val result = fetchAreas()

        if (result is Resource.Success) {
            cachedAreas = result
        }
        return result
    }

    private suspend fun fetchAreas(): Resource<List<Area>> {
        val response = networkClient.doRequest(RequestDto.AreasRequest)
        return when (response.status) {
            ResponseStatus.SUCCESS -> {
                if (response is AreasResponse) {
                    val data = response.result.mapToDomain()
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
