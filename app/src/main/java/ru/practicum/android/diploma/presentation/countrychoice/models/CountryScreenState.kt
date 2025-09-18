package ru.practicum.android.diploma.presentation.countrychoice.models

import ru.practicum.android.diploma.domain.areas.models.Area

sealed interface CountryScreenState {
    object Empty : CountryScreenState
    data class Content(
        val list: List<Area>
    ) : CountryScreenState
}
