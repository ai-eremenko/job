package ru.practicum.android.diploma.data.areas

import ru.practicum.android.diploma.data.NetworkClient
import ru.practicum.android.diploma.data.dto.RequestDto
import ru.practicum.android.diploma.data.dto.responses.AreasResponse
import ru.practicum.android.diploma.data.dto.responses.areas.AreasListDTO
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
        return cachedAreas ?: fetchAreas().also { cachedAreas = it }
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

    override suspend fun downloadAreas(): Pair<AreasListDTO?, Int> {
        val response = networkClient.doRequest(RequestDto.AreasRequest)
        return if (response is AreasResponse && response.status == ResponseStatus.SUCCESS) {
            val areasDTO = AreasListDTO(response.result.mapToDomain())
            areasDTO to 200
        } else {
            val statusCode = when (response.status) {
                ResponseStatus.SUCCESS -> 200
                ResponseStatus.NOT_FOUND -> 404
                ResponseStatus.SERVER_ERROR -> 500
                else -> 0
            }
            null to statusCode
        }
    }




    override suspend fun insertAreas(areas: List<Area>) {
        cachedAreas = Resource.Success(areas)

    }
}
