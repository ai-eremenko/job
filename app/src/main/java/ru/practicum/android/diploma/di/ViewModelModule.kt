package ru.practicum.android.diploma.di

import org.koin.core.module.dsl.viewModel
import org.koin.core.module.dsl.viewModelOf
import org.koin.dsl.module
import ru.practicum.android.diploma.presentation.countrychoice.CountryViewModel
import ru.practicum.android.diploma.presentation.favorite.FavoriteViewModel
import ru.practicum.android.diploma.presentation.filteringsettings.FilterViewModel
import ru.practicum.android.diploma.presentation.filteringsettings.SharedViewModel
import ru.practicum.android.diploma.presentation.industrychoice.IndustryChoiceViewModel
import ru.practicum.android.diploma.presentation.regionchoice.RegionViewModel
import ru.practicum.android.diploma.presentation.root.RootViewModel
import ru.practicum.android.diploma.presentation.search.SearchViewModel
import ru.practicum.android.diploma.presentation.vacancy.VacancyViewModel
import ru.practicum.android.diploma.presentation.workplacechoice.WorkplaceViewModel

val viewModelModule = module {

    viewModel { parameters ->
        VacancyViewModel(parameters.get(), get(), get(), get(), get())
    }

    viewModel {
        SearchViewModel(get(), get())
    }

    viewModel {
        FavoriteViewModel(get())
    }
    viewModelOf(::RootViewModel)

    viewModel {
        FilterViewModel(get())
    }

    viewModel {
        SharedViewModel()
    }

    viewModel {
        WorkplaceViewModel(get(), get())
    }

    viewModel {
        CountryViewModel(get())
    }

    viewModel {
        IndustryChoiceViewModel(get())
    }

    viewModel { parameters ->
        RegionViewModel(parameters.get(), get())
    }
}
