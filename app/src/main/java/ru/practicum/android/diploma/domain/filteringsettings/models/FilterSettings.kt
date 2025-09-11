package ru.practicum.android.diploma.domain.filteringsettings.models

data class FilterSettings(
    val areaId: Int? = null,
    val industryId: Int? = null,
    val salary: Int? = null,
    val onlyWithSalary: Boolean = false
)
