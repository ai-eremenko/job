package ru.practicum.android.diploma.di

import org.koin.android.ext.koin.androidContext
import org.koin.dsl.module
import ru.practicum.android.diploma.data.ResourcesProviderRepositoryImpl
import ru.practicum.android.diploma.domain.ResourcesProviderRepository

val repositoryModule = module {

    single <ResourcesProviderRepository> {
        ResourcesProviderRepositoryImpl(androidContext())
    }
}
