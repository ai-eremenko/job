package ru.practicum.android.diploma.data.filteringsettings

import android.util.Log
import ru.practicum.android.diploma.domain.filteringsettings.FilterRepository
import ru.practicum.android.diploma.domain.filteringsettings.models.FilterSettings

class FilterRepositoryImpl : FilterRepository {
    override fun getFilterOptions(): FilterSettings {
        return FilterSettings()
    }

    override fun saveFilterOptions(filter: FilterSettings) {
        Log.d("","TODO")
    }

//
//    override fun clearFilterOptions() {
//    }
}
