package ru.practicum.android.diploma.domain.filteringsettings.models

import ru.practicum.android.diploma.domain.areas.models.Area

data class FilterSettings(
    var currentCountry: Area? = null,
    var savedCountry: Area? = null,
    var currentRegion: Area? = null,
    var savedRegion: Area? = null,
    val countryId: Int? = null,
    val countryName: String? = null,
    val areaId: Int? = null,
    val areaName: String? = null,
    val industryId: Int? = null,
    val industryName: String? = null,
    val salary: Int? = null,
    val onlyWithSalary: Boolean = false
)
