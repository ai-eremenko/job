package ru.practicum.android.diploma.domain.search.mapper

import ru.practicum.android.diploma.domain.search.models.VacancyPreview
import ru.practicum.android.diploma.domain.search.models.VacancyPreviewPresent
import ru.practicum.android.diploma.util.SalaryFormatter

class VacancyPreviewMapper(private val salaryFormatter: SalaryFormatter) {

    fun mapToPresentation(domain: VacancyPreview): VacancyPreviewPresent {
        return VacancyPreviewPresent(
            id = domain.id,
            name = domain.name,
            url = domain.employerLogo,
            salary = salaryFormatter.formatSalary(
                domain.salaryFrom,
                domain.salaryTo,
                domain.salaryCurrency
            ),
            addressCity = domain.addressCity,
            employerName = domain.employerName
        )
    }
}
