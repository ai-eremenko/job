package ru.practicum.android.diploma.di

import org.koin.dsl.module
import ru.practicum.android.diploma.domain.areas.AreasInteractor
import ru.practicum.android.diploma.domain.areas.impl.AreasInteractorImpl
import ru.practicum.android.diploma.domain.countrychoice.CountryUseCase
import ru.practicum.android.diploma.domain.favorite.FavoriteInteractor
import ru.practicum.android.diploma.domain.favorite.FavoriteInteractorImpl
import ru.practicum.android.diploma.domain.filteringsettings.FilterInteractor
import ru.practicum.android.diploma.domain.filteringsettings.impl.FilterInteractorImpl
import ru.practicum.android.diploma.domain.industrychoice.IndustryInteractor
import ru.practicum.android.diploma.domain.industrychoice.impl.IndustryInteractorImpl
import ru.practicum.android.diploma.domain.search.SearchInteractor
import ru.practicum.android.diploma.domain.search.impl.SearchInteractorImpl
import ru.practicum.android.diploma.domain.sharing.SharingInteractor
import ru.practicum.android.diploma.domain.sharing.impl.SharingInteractorImpl
import ru.practicum.android.diploma.domain.util.ResourcesProviderInteractor
import ru.practicum.android.diploma.domain.util.usecases.ResourcesProviderInteractorImpl
import ru.practicum.android.diploma.domain.vacancy.VacancyInteractor
import ru.practicum.android.diploma.domain.vacancy.impl.VacancyInteractorImpl

val interactorModule = module {

    single<ResourcesProviderInteractor> {
        ResourcesProviderInteractorImpl(get())
    }

    factory<SearchInteractor> {
        SearchInteractorImpl(get(), get())
    }

    factory<FavoriteInteractor> {
        FavoriteInteractorImpl(get(), get())
    }

    factory<VacancyInteractor> {
        VacancyInteractorImpl(get(), get(), get())
    }

    factory<SharingInteractor> {
        SharingInteractorImpl(get(), get())
    }

    factory<AreasInteractor> {
        AreasInteractorImpl(get())
    }

    factory<FilterInteractor> {
        FilterInteractorImpl(get())
    }

    factory<IndustryInteractor> {
        IndustryInteractorImpl(get(), get())
    }

    factory {
        CountryUseCase(get())
    }
}
