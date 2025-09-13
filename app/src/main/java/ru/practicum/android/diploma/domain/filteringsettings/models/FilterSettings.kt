package ru.practicum.android.diploma.domain.filteringsettings.models

data class FilterSettings(
    val areaId: Int? = null,
    val areaName: String? = null,
    val industryId: Int? = null,
    val industryName: String? = null,
    val salary: Int? = null,
    val onlyWithSalary: Boolean = false
)
