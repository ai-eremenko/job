package ru.practicum.android.diploma.domain

interface ResourcesProviderRepository {
    fun getString(resId: Int): String
    fun getString(resId: Int, vararg formatArgs: Any): String
}
