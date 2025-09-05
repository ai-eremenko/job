package ru.practicum.android.diploma.di

import org.koin.android.ext.koin.androidContext
import org.koin.dsl.module
import ru.practicum.android.diploma.data.ResourcesProviderRepositoryImpl
import ru.practicum.android.diploma.data.search.SearchRepositoryImpl
import ru.practicum.android.diploma.domain.ResourcesProviderRepository
import ru.practicum.android.diploma.domain.search.SearchRepository

val repositoryModule = module {

    single<ResourcesProviderRepository> {
        ResourcesProviderRepositoryImpl(androidContext())
    }

    factory<SearchRepository> {
        SearchRepositoryImpl(get())
    }
}
