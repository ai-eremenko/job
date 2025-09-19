package ru.practicum.android.diploma.domain.areas.models

data class Area(
    val id: Int,
    val name: String,
    val parentId: Int? = null,
    val areas: List<Area> = emptyList()
)


sealed class AreasState {
    object Loading : AreasState()
    object Empty : AreasState()
    object Error : AreasState()
    data class Content(val areas: List<Area>) : AreasState()
}
