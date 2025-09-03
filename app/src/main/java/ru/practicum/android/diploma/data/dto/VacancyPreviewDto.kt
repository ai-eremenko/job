package ru.practicum.android.diploma.data.dto

data class VacancyPreviewDto(
    val id: String,
    val name: String,
    val salary: SalaryDto?,
    val address: AddressDto?,
    val employer: EmployerPreviewDto
)

data class EmployerPreviewDto(
    val name: String
)
