package ru.practicum.android.diploma.util

import ru.practicum.android.diploma.R
import ru.practicum.android.diploma.domain.ResourcesProviderRepository

class SalaryFormatter(private val resourcesProvider: ResourcesProviderRepository) {

    fun formatSalary(from: String?, to: String?, currency: String?): String? {
        return when {
            from != null && to != null -> resourcesProvider.getString(
                R.string.salary_from_to,
                from,
                to,
                currency ?: ""
            )
            from != null -> resourcesProvider.getString(
                R.string.salary_from,
                from,
                currency ?: ""
            )
            to != null -> resourcesProvider.getString(
                R.string.salary_to,
                to,
                currency ?: ""
            )
            else -> resourcesProvider.getString(R.string.salary_not)
        }
    }
}
