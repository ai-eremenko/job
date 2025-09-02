package ru.practicum.android.diploma.data.dto

data class VacancyDto(
    val id: Int,
    val name: String,
    val description: String,
    val salary: SalaryDto?,
    val address: AddressDto?,
    val experience: ExperienceDto,
    val schedule: ScheduleDto,
    val employment: EmploymentDto,
    val contacts: ContactsDto?,
    val employer: EmployerDto,
    val area: FilterAreaDto,
    val skills: List<String>?,
    val url: String,
    val industry: FilterIndustryDto
)

data class SalaryDto(
    val from: Int?,
    val to: Int?,
    val currency: String,
)

data class AddressDto(
    val city: String,
    val street: String,
    val building: String,
    val fullAddress: String,
)

data class ExperienceDto(
    val id: String,
    val name: String,
)

data class ScheduleDto(
    val id: String,
    val name: String,
)

data class EmploymentDto(
    val id: String,
    val name: String,
)

data class ContactsDto(
    val id: String,
    val name: String,
    val email: String,
    val phone: List<String>
)

data class EmployerDto(
    val id: String,
    val name: String,
    val logo: String,
)

