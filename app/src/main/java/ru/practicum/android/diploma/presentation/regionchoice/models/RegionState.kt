package ru.practicum.android.diploma.presentation.regionchoice.models

import ru.practicum.android.diploma.domain.areas.models.Area

sealed class RegionState {
    object Empty : RegionState()
    object Error : RegionState()
    data class Content(val areasList: List<Area>) : RegionState()
}
