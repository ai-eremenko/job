package ru.practicum.android.diploma.data.mappers

import ru.practicum.android.diploma.data.dto.VacancyDto
import ru.practicum.android.diploma.data.dto.VacancyPreviewDto
import ru.practicum.android.diploma.data.dto.responses.VacanciesResponse
import ru.practicum.android.diploma.domain.search.models.VacanciesSearchResult
import ru.practicum.android.diploma.domain.search.models.VacancyPreview
import ru.practicum.android.diploma.domain.vacancy.models.Address
import ru.practicum.android.diploma.domain.vacancy.models.Contacts
import ru.practicum.android.diploma.domain.vacancy.models.Employer
import ru.practicum.android.diploma.domain.vacancy.models.Phone
import ru.practicum.android.diploma.domain.vacancy.models.Salary
import ru.practicum.android.diploma.domain.vacancy.models.Vacancy

object VacancyMapper {

    fun mapToDomain(response: VacanciesResponse): VacanciesSearchResult<VacancyPreview> {
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
            addressCity = dto.address.city,
            employerName = dto.employer.name,
            employerLogo = dto.employer.logo
        )
    }

    fun mapToDomain(dto: VacancyDto): Vacancy {
        return Vacancy(
            id = dto.id,
            name = dto.name,
            description = dto.description,
            salary = dto.salary?.let {
                Salary(
                    from = it.from,
                    to = it.to,
                    currency = it.currency
                )
            },
            address = Address(
                city = dto.address.city,
                raw = dto.address.raw
            ),
            experience = dto.experience.name,
            schedule = dto.schedule.name,
            employment = dto.employment.name,
            contacts = dto.contacts?.let {
                Contacts(
                    name = it.name,
                    email = it.email,
                    phones = it.phones?.map { phoneDto ->
                        Phone(
                            comment = phoneDto.comment,
                            formatted = phoneDto.formatted
                        )
                    }
                )
            },
            employer = Employer(
                name = dto.employer.name,
                logo = dto.employer.logo
            ),
            skills = dto.skills,
        )
    }
}
