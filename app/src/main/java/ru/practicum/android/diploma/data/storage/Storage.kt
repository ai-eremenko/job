package ru.practicum.android.diploma.data.storage

import android.content.SharedPreferences
import androidx.core.content.edit
import com.google.gson.Gson
import ru.practicum.android.diploma.domain.areas.models.Area
import ru.practicum.android.diploma.domain.filteringsettings.models.FilterSettings

const val EXAMPLE_PREFERENCES = "example_preferences"
const val FILTERS = "filters"

class Storage(
    val sharedPreferences: SharedPreferences,
    private val gson: Gson,
) {
    fun getFilterOptions(): FilterSettings {
        val json = sharedPreferences.getString(FILTERS, null)
        return if (json.isNullOrEmpty()) {
            FilterSettings()
        } else {
            gson.fromJson(json, FilterSettings::class.java) ?: FilterSettings()
        }
    }

    fun saveFilterOptions(filter: FilterSettings) {
        val json = gson.toJson(filter)
        sharedPreferences.edit { putString(FILTERS, json) }
    }

    fun getCountry(isCurrent: Boolean): Area? {
        val filter = getFilterOptions()
        return if (isCurrent) filter.currentCountry else filter.savedCountry
    }

    fun getRegion(isCurrent: Boolean): Area? {
        val filter = getFilterOptions()
        return if (isCurrent) filter.currentRegion else filter.savedRegion
    }

    fun saveCountry(country: Area?, isCurrent: Boolean) {
        val filter = getFilterOptions()
        if (isCurrent) filter.currentCountry = country else filter.savedCountry = country
        saveFilterOptions(filter)
    }

    fun saveRegion(region: Area?, isCurrent: Boolean) {
        val filter = getFilterOptions()
        if (isCurrent) filter.currentRegion = region else filter.savedRegion = region
        saveFilterOptions(filter)
    }

}
