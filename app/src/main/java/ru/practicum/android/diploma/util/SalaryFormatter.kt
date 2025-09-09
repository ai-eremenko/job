package ru.practicum.android.diploma.util

import ru.practicum.android.diploma.R
import ru.practicum.android.diploma.domain.util.ResourcesProviderRepository
import java.text.NumberFormat
import java.util.Currency
import java.util.Locale

class SalaryFormatter(private val resourcesProvider: ResourcesProviderRepository) {

    fun formatSalary(from: String?, to: String?, currency: String?): String {
        val currencySymbol = getCurrencySymbol(currency)

        return when {
            from != null && to != null -> resourcesProvider.getString(
                R.string.salary_from_to,
                formatNumber(from),
                formatNumber(to),
                currencySymbol
            )

            from != null -> resourcesProvider.getString(
                R.string.salary_from,
                formatNumber(from),
                currencySymbol
            )

            to != null -> resourcesProvider.getString(
                R.string.salary_to,
                formatNumber(to),
                currencySymbol
            )

            else -> resourcesProvider.getString(R.string.salary_not)
        }
    }

    private fun formatNumber(numberString: String): String {
        return numberString.toIntOrNull()?.let { number ->
            NumberFormat.getNumberInstance(Locale.getDefault()).format(number)
        } ?: numberString
    }

    private fun getCurrencySymbol(currencyCode: String?): String {
        if (currencyCode.isNullOrBlank()) return ""

        val upperCode = currencyCode.uppercase()

        val currency = Currency.getAvailableCurrencies().firstOrNull {
            it.currencyCode == upperCode
        }

        return currency?.getSymbol(Locale.getDefault()) ?: upperCode
    }
}
