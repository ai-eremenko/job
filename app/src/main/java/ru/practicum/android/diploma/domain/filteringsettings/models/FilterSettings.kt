package ru.practicum.android.diploma.domain.filteringsettings.models

data class FilterSettings(
    val areaId: Int? = null,
    val areaName: String?,
    val industryId: Int? = null,
    val industryName: String?,
    val salary: Int? = null,
    val onlyWithSalary: Boolean = false
)
