package ru.practicum.android.diploma.di

import org.koin.android.ext.koin.androidContext
import org.koin.dsl.module
import ru.practicum.android.diploma.data.ResourcesProviderRepositoryImpl
import ru.practicum.android.diploma.data.favorite.impl.FavoriteRepositoryImpl
import ru.practicum.android.diploma.data.search.SearchRepositoryImpl
import ru.practicum.android.diploma.domain.favorite.FavoriteRepository
import ru.practicum.android.diploma.data.vacancy.VacancyRepositoryImpl
import ru.practicum.android.diploma.domain.search.SearchRepository
import ru.practicum.android.diploma.domain.util.ResourcesProviderRepository
import ru.practicum.android.diploma.domain.vacancy.VacancyRepository

val repositoryModule = module {

    single<ResourcesProviderRepository> {
        ResourcesProviderRepositoryImpl(androidContext())
    }

    factory<SearchRepository> {
        SearchRepositoryImpl(get())
    }

    factory<FavoriteRepository> {
        FavoriteRepositoryImpl(get(), get())
    }

    factory<VacancyRepository> {
        VacancyRepositoryImpl(get())
    }
}
