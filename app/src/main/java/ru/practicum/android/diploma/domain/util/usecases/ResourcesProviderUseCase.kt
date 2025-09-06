package ru.practicum.android.diploma.domain.util.usecases

import ru.practicum.android.diploma.domain.util.ResourcesProviderRepository

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
