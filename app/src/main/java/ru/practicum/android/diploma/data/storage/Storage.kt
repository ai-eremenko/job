package ru.practicum.android.diploma.data.storage

import android.content.SharedPreferences
import com.google.gson.Gson
import ru.practicum.android.diploma.domain.filteringsettings.models.FilterSettings

const val EXAMPLE_PREFERENCES = "example_preferences"
const val FILTERS = "filters"

class Storage(
    val sharedPreferences: SharedPreferences,
    private val gson: Gson,
) {
    fun getFilterOptions(): FilterSettings {
        val json = sharedPreferences.getString(FILTERS, null) ?: return FilterSettings()
        return gson.fromJson(json, FilterSettings::class.java)
    }

    fun saveFilterOptions(filter: FilterSettings) {
        sharedPreferences.edit()
            .putString(FILTERS, createJsonFromFilter(filter))
            .apply()
    }

    private fun createJsonFromFilter(filter: FilterSettings): String {
        return gson.toJson(filter)
    }
}

