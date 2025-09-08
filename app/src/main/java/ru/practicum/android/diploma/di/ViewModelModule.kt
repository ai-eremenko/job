package ru.practicum.android.diploma.di

import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.core.module.dsl.viewModel
import org.koin.dsl.module
import ru.practicum.android.diploma.presentation.search.SearchViewModel
import ru.practicum.android.diploma.presentation.favorite.FavoriteViewModel


val viewModelModule = module {
    viewModel {
        SearchViewModel(get())
    }

    viewModel {
        FavoriteViewModel(get())
    }
}
