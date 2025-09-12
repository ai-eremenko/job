package ru.practicum.android.diploma.di

import org.koin.android.ext.koin.androidContext
import org.koin.dsl.module
import ru.practicum.android.diploma.data.ResourcesProviderRepositoryImpl
import ru.practicum.android.diploma.data.favorite.impl.FavoriteRepositoryImpl
import ru.practicum.android.diploma.data.filteringsettings.FilterRepositoryImpl
import ru.practicum.android.diploma.data.industrychoice.IndustryRepositoryImpl
import ru.practicum.android.diploma.data.search.SearchRepositoryImpl
import ru.practicum.android.diploma.data.sharing.SharingRepositoryImpl
import ru.practicum.android.diploma.data.vacancy.VacancyRepositoryImpl
import ru.practicum.android.diploma.domain.favorite.FavoriteRepository
import ru.practicum.android.diploma.domain.filteringsettings.FilterRepository
import ru.practicum.android.diploma.domain.industrychoice.IndustryRepository
import ru.practicum.android.diploma.domain.search.SearchRepository
import ru.practicum.android.diploma.domain.sharing.SharingRepository
import ru.practicum.android.diploma.domain.util.ResourcesProviderRepository
import ru.practicum.android.diploma.domain.vacancy.VacancyRepository

val repositoryModule = module {

    single<ResourcesProviderRepository> {
        ResourcesProviderRepositoryImpl(androidContext())
    }

    factory<SearchRepository> {
        SearchRepositoryImpl(get(), get())
    }

    factory<FavoriteRepository> {
        FavoriteRepositoryImpl(get(), get())
    }

    factory<VacancyRepository> {
        VacancyRepositoryImpl(get())
    }

    factory<SharingRepository> {
        SharingRepositoryImpl(get())
    }

    factory<FilterRepository> {
        FilterRepositoryImpl(get())
    }

    factory<IndustryRepository> {
        IndustryRepositoryImpl(get())
    }
}
