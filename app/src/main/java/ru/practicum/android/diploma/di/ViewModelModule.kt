package ru.practicum.android.diploma.di

import org.koin.core.module.dsl.viewModelOf
import org.koin.core.module.dsl.viewModel
import org.koin.dsl.module
import ru.practicum.android.diploma.presentation.vacancy.VacancyViewModel
import ru.practicum.android.diploma.presentation.favorite.FavoriteViewModel

val viewModelModule = module {

    viewModelOf(::VacancyViewModel)
    viewModel {
        FavoriteViewModel(get())
    }
}
