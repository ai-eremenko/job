package ru.practicum.android.diploma.domain.useсases

import ru.practicum.android.diploma.domain.ResourcesProviderRepository

class ResourcesProviderUseCase(
    private val repository: ResourcesProviderRepository
) {
    fun getString(resId: Int): String {
        return repository.getString(resId)
    }

    fun getString(resId: Int, vararg formatArgs: Any): String {
        return repository.getString(resId, *formatArgs)
    }
}
