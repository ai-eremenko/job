package ru.practicum.android.diploma.domain.filteringsettings.models

data class FilterPresent(
    val area: String?,
    val industry: String?,
    val salary: Int?,
    val onlyWithSalary: Boolean
)
