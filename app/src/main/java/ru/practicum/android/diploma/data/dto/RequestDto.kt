package ru.practicum.android.diploma.data.dto

sealed interface RequestDto {
    data object AreasRequest : RequestDto
    data object IndustriesRequest : RequestDto
    data class VacanciesRequest(
        val expression: String,
        val page: Int,
        val options: Map<String, Int>,
        val onlyWithSalary: Boolean
    ) : RequestDto
    data class VacancyRequest(
        val id: String
    ) : RequestDto
}
