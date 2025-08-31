package ru.practicum.android.diploma.data.dto

data class FilterAreaDto(
    val id: Int,
    val name: String,
    val parentId: Int? = null,
    val areas: List<FilterAreaDto> = emptyList()
)
