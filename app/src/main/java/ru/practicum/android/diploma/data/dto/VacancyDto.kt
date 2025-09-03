package ru.practicum.android.diploma.data.dto

data class VacancyDto(
    val id: String,
    val name: String,
    val description: String,
    val salary: SalaryDto?,
    val address: AddressDto?,
    val experience: ExperienceDto,
    val schedule: ScheduleDto,
    val employment: EmploymentDto,
    val contacts: ContactsDto?,
    val employer: EmployerDto,
    val skills: List<String>?,
    val url: String,
)

data class SalaryDto(
    val from: Int?,
    val to: Int?,
    val currency: String,
)

data class AddressDto(
    val city: String,
)

data class ExperienceDto(
    val name: String,
)

data class ScheduleDto(
    val name: String,
)

data class EmploymentDto(
    val name: String,
)

data class ContactsDto(
    val name: String,
    val email: String,
    val phones: List<PhoneDto>?
)

data class EmployerDto(
    val name: String,
    val logo: String,
)

data class PhoneDto(
    val comment: String?,
    val formatted: String
)

