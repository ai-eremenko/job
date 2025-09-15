package ru.practicum.android.diploma.domain.filteringsettings.models

data class FilterSettings(
    val countryId: Int? = null,
    val countryName: String? = null,
    val areaId: Int? = null,
    val areaName: String? = null,
    val industryId: Int? = null,
    val industryName: String? = null,
    val salary: Int? = null,
    val onlyWithSalary: Boolean = false
)
