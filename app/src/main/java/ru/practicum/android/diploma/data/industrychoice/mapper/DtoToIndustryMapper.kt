package ru.practicum.android.diploma.data.industrychoice.mapper

import ru.practicum.android.diploma.data.dto.FilterIndustryDto
import ru.practicum.android.diploma.domain.industrychoice.models.Industry

object DtoToIndustryMapper {
    fun map(dto: FilterIndustryDto): Industry {
        return Industry(
            dto.id,
            dto.name
        )
    }
}
