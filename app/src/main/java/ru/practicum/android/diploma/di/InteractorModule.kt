package ru.practicum.android.diploma.di

import org.koin.dsl.module
import ru.practicum.android.diploma.domain.search.SearchInteractor
import ru.practicum.android.diploma.domain.search.impl.SearchInteractorImpl
import ru.practicum.android.diploma.domain.sharing.SharingInteractor
import ru.practicum.android.diploma.domain.sharing.impl.SharingInteractorImpl
import ru.practicum.android.diploma.domain.util.usecases.ResourcesProviderInteractorImpl
import ru.practicum.android.diploma.domain.vacancy.VacancyInteractor
import ru.practicum.android.diploma.domain.vacancy.impl.VacancyInteractorImpl

val interactorModule = module {

    single<ResourcesProviderInteractorImpl> {
        ResourcesProviderInteractorImpl(get())
    }

    factory<SearchInteractor> {
        SearchInteractorImpl(get(), get())
    }

    factory<VacancyInteractor> {
        VacancyInteractorImpl(get(), get(), get())
    }

    factory<SharingInteractor> {
        SharingInteractorImpl(get(), get())
    }
}
