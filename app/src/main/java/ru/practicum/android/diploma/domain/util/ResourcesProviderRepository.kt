package ru.practicum.android.diploma.domain.util

import android.graphics.drawable.Drawable

interface ResourcesProviderRepository {
    fun getString(resId: Int): String
    fun getString(resId: Int, vararg formatArgs: Any): String
    fun getDrawable(resId: Int): Drawable?
}
