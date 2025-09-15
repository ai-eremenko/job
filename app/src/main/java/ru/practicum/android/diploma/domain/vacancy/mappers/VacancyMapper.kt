package ru.practicum.android.diploma.domain.vacancy.mappers

import ru.practicum.android.diploma.domain.vacancy.models.Vacancy
import ru.practicum.android.diploma.domain.vacancy.models.VacancyPresent
import ru.practicum.android.diploma.util.SalaryFormatter
import ru.practicum.android.diploma.util.skillsFormatter

class VacancyMapper(private val salaryFormatter: SalaryFormatter) {
    fun mapToPresentation(domain: Vacancy): VacancyPresent {
        return VacancyPresent(
            id = domain.id,
            name = domain.name,
            description = domain.description,
            salary = salaryFormatter.formatSalary(
                domain.salary?.from?.toString(),
                domain.salary?.to?.toString(),
                domain.salary?.currency
            ),
            address = if (domain.address.raw.isNotEmpty()) domain.address.raw else domain.address.city,
            experience = domain.experience,
            schedule = domain.schedule,
            employment = domain.employment,
            contacts = domain.contacts,
            employerName = domain.employer.name,
            skills = if (domain.skills != null) skillsFormatter(domain.skills) else null,
            url = domain.employer.logo,
            isFavorite = false,
            urlLink = domain.urlLink
        )
    }
}
