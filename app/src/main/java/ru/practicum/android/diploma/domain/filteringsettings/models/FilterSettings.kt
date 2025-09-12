package ru.practicum.android.diploma.domain.filteringsettings.models

data class FilterSettings(
    val areaId: Int? = null,
    val areaName: String? = null,
    val industryId: Int? = null,
    val industryName: String? = null,
    val salary: Int? = null,
    val onlyWithSalary: Boolean = false
) {
    fun hasActiveFilters(): Boolean {
        return areaId != null || industryId != null || salary != null || onlyWithSalary
    }

    fun isEmpty(): Boolean {
        return areaId == null &&
                areaName == null &&
                industryId == null &&
                industryName == null &&
                salary == null &&
                !onlyWithSalary
    }
}
