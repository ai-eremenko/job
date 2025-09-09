package ru.practicum.android.diploma.domain.util.usecases

import android.graphics.drawable.Drawable
import ru.practicum.android.diploma.domain.util.ResourcesProviderInteractor
import ru.practicum.android.diploma.domain.util.ResourcesProviderRepository

class ResourcesProviderInteractorImpl(
    private val repository: ResourcesProviderRepository
) : ResourcesProviderInteractor {
    override fun getString(resId: Int): String {
        return repository.getString(resId)
    }

    override fun getString(resId: Int, vararg formatArgs: Any): String {
        return repository.getString(resId, *formatArgs)
    }

    override fun getDrawable(resId: Int): Drawable? {
        return repository.getDrawable(resId)
    }
}
