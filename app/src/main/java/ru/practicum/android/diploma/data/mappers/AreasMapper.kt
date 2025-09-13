package ru.practicum.android.diploma.data.mappers

import ru.practicum.android.diploma.data.dto.FilterAreaDto
import ru.practicum.android.diploma.domain.areas.models.Area

object AreasMapper {

    fun List<FilterAreaDto>.mapToDomain(): List<Area> {
        return this.map {
            Area(
                id = it.id,
                name = it.name,
                parentId = it.parentId,
                areas = it.areas.mapToDomain()
            )
        }
    }
}
