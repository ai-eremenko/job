package ru.practicum.android.diploma.domain.areas.models

data class Area(
    val id: Int,
    val name: String,
    val parentId: Int? = null,
    val areas: List<Area> = emptyList()
)
