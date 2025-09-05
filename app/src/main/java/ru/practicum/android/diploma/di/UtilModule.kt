package ru.practicum.android.diploma.di

import org.koin.dsl.module
import ru.practicum.android.diploma.domain.search.mapper.VacancyPreviewMapper
import ru.practicum.android.diploma.util.SalaryFormatter

val utilModule = module {

    single {
        SalaryFormatter(get())
    }

    single {
        VacancyPreviewMapper(get())
    }
}
