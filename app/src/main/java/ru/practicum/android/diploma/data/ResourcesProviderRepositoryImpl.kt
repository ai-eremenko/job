package ru.practicum.android.diploma.data

import android.content.Context
import android.graphics.drawable.Drawable
import androidx.core.content.ContextCompat
import ru.practicum.android.diploma.domain.util.ResourcesProviderRepository

class ResourcesProviderRepositoryImpl(
    private val context: Context
) : ResourcesProviderRepository {
    override fun getString(resId: Int): String {
        return context.getString(resId)
    }

    override fun getString(resId: Int, vararg formatArgs: Any): String {
        return context.getString(resId, *formatArgs)
    }

    override fun getDrawable(resId: Int): Drawable? {
        return ContextCompat.getDrawable(context, resId)
    }
}
