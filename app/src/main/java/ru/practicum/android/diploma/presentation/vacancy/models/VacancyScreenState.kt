package ru.practicum.android.diploma.presentation.vacancy.models

import android.graphics.drawable.Drawable
import ru.practicum.android.diploma.domain.vacancy.models.VacancyPresent

sealed class VacancyScreenState {
    object Loading : VacancyScreenState()
    data class Content(
        val vacancyModel: VacancyPresent
    ) : VacancyScreenState()

    data class Favorite(val isFavorite: Boolean) : VacancyScreenState()

    data class Error(
        val errorImg: Drawable?,
        val errorText: String
    ) : VacancyScreenState()
}
