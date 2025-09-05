package ru.practicum.android.diploma.di

import org.koin.dsl.module
import ru.practicum.android.diploma.domain.favorite.FavoriteInteractor
import ru.practicum.android.diploma.domain.favorite.FavoriteInteractorImpl
import ru.practicum.android.diploma.domain.search.SearchInteractor
import ru.practicum.android.diploma.domain.search.impl.SearchInteractorImpl
import ru.practicum.android.diploma.domain.usecases.ResourcesProviderUseCase

val interactorModule = module {

    single<ResourcesProviderUseCase> {
        ResourcesProviderUseCase(get())
    }

    factory<SearchInteractor> {
        SearchInteractorImpl(get(), get())
    }

    factory<FavoriteInteractor> {
        FavoriteInteractorImpl(get())
    }
}
