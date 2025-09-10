package ru.practicum.android.diploma.di

import org.koin.core.module.dsl.viewModel
import org.koin.core.module.dsl.viewModelOf
import org.koin.dsl.module
import ru.practicum.android.diploma.presentation.favorite.FavoriteViewModel
import ru.practicum.android.diploma.presentation.root.RootViewModel
import ru.practicum.android.diploma.presentation.search.SearchViewModel
import ru.practicum.android.diploma.presentation.vacancy.VacancyViewModel

val viewModelModule = module {

    viewModel { parameters ->
        VacancyViewModel(parameters.get(), get(), get(), get(), get())
    }

    viewModel {
        SearchViewModel(get())
    }

    viewModel {
        FavoriteViewModel(get())
    }
    viewModelOf(::RootViewModel)
}
