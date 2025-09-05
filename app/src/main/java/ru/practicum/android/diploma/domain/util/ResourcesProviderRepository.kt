package ru.practicum.android.diploma.domain.util

interface ResourcesProviderRepository {
    fun getString(resId: Int): String
    fun getString(resId: Int, vararg formatArgs: Any): String
}
