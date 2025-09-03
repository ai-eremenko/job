package ru.practicum.android.diploma.data.mappers

import ru.practicum.android.diploma.data.dto.responses.VacanciesResponse
import ru.practicum.android.diploma.data.dto.responses.VacancyPreviewDto
import ru.practicum.android.diploma.domain.models.VacanciesSearchResult
import ru.practicum.android.diploma.domain.models.VacancyPreview

object VacancyMapper {
    fun mapToDomain(response: VacanciesResponse): VacanciesSearchResult {
        return VacanciesSearchResult(
            found = response.found,
            pages = response.pages,
            page = response.page,
            items = response.items.map { mapVacancyToDomain(it) }
        )
    }

    private fun mapVacancyToDomain(dto: VacancyPreviewDto): VacancyPreview {
        return VacancyPreview(
            id = dto.id,
            name = dto.name,
            salaryCurrency = dto.salary?.currency,
            salaryFrom = dto.salary?.from?.toString(),
            salaryTo = dto.salary?.to?.toString(),
            addressCity = dto.address?.city,
            employerName = dto.employer.name
        )
    }
}
