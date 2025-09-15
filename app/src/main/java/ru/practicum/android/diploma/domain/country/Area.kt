package ru.practicum.android.diploma.domain.country

data class Area(
    val id: Int,
    val parentId: Int?, // null если это страна, иначе id страны для региона
    val name: String,
    val isSelected: Boolean = false
)
