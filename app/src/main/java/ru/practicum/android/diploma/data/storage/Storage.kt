package ru.practicum.android.diploma.data.storage

import android.content.SharedPreferences
import androidx.core.content.edit
import com.google.gson.Gson
import ru.practicum.android.diploma.domain.filteringsettings.models.FilterSettings

const val EXAMPLE_PREFERENCES = "example_preferences"
const val FILTERS = "filters"

class Storage(
    val sharedPreferences: SharedPreferences,
    private val gson: Gson,
) {
    fun getFilterOptions(): FilterSettings? {
        val json = sharedPreferences.getString(FILTERS, null) ?: return null
        return gson.fromJson(json, FilterSettings::class.java)
    }

    fun saveFilterOptions(filter: FilterSettings?) {
        if (filter == null) {
            sharedPreferences.edit {
                putString(FILTERS, null)
            }
        } else {
            sharedPreferences.edit {
                putString(FILTERS, createJsonFromFilter(filter))
            }
        }
    }

    private fun createJsonFromFilter(filter: FilterSettings?): String? {
        return gson.toJson(filter)
    }
}
