package ru.practicum.android.diploma.util

import ru.practicum.android.diploma.R
import ru.practicum.android.diploma.domain.util.ResourcesProviderRepository
import java.text.NumberFormat
import java.util.Locale

class SalaryFormatter(private val resourcesProvider: ResourcesProviderRepository) {

    fun formatSalary(from: String?, to: String?, currency: String?): String {
        return when {
            from != null && to != null -> resourcesProvider.getString(
                R.string.salary_from_to,
                formatNumber(from),
                formatNumber(to),
                currency ?: ""
            )

            from != null -> resourcesProvider.getString(
                R.string.salary_from,
                formatNumber(from),
                currency ?: ""
            )

            to != null -> resourcesProvider.getString(
                R.string.salary_to,
                formatNumber(to),
                currency ?: ""
            )

            else -> resourcesProvider.getString(R.string.salary_not)
        }
    }

    private fun formatNumber(numberString: String): String {
        return numberString.toIntOrNull()?.let { number ->
            NumberFormat.getNumberInstance(Locale.getDefault()).format(number)
        } ?: numberString
    }
}
