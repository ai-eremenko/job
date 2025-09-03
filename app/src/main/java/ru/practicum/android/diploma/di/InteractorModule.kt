package ru.practicum.android.diploma.di

import org.koin.dsl.module
import ru.practicum.android.diploma.domain.usecases.ResourcesProviderUseCase

val interactorModule = module {

    single<ResourcesProviderUseCase> {
        ResourcesProviderUseCase(get())
    }
}
