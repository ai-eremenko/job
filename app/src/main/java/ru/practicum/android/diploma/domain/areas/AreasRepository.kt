package ru.practicum.android.diploma.domain.areas

import ru.practicum.android.diploma.domain.areas.models.Area
import ru.practicum.android.diploma.util.Resource

interface AreasRepository {
    suspend fun getAreas(): Resource<List<Area>>
}
