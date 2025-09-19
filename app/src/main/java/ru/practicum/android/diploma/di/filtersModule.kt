package ru.practicum.android.diploma.di

import org.koin.dsl.module
import ru.practicum.android.diploma.domain.filters.FiltersInteractor
import ru.practicum.android.diploma.domain.filters.FiltersSharedInteractor
import ru.practicum.android.diploma.domain.filters.FiltersSharedInteractorSave
import ru.practicum.android.diploma.domain.filters.impl.FiltersInteractorImpl
import ru.practicum.android.diploma.domain.filters.impl.FiltersSharedInteractorImpl
import ru.practicum.android.diploma.domain.filters.impl.FiltersSharedInteractorSaveImpl

val filtersModule = module {
    single<FiltersInteractor> { FiltersInteractorImpl(get()) }
    single<FiltersSharedInteractor> { FiltersSharedInteractorImpl(get()) }
    single<FiltersSharedInteractorSave> { FiltersSharedInteractorSaveImpl(get()) }
}
