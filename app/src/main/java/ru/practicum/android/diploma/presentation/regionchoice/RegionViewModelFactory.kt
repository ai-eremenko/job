package ru.practicum.android.diploma.presentation.regionchoice

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import ru.practicum.android.diploma.domain.areas.AreasInteractor

class RegionViewModelFactory(
    private val interactor: AreasInteractor
) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(RegionViewModel::class.java)) {
            @Suppress("UNCHECKED_CAST")
            return RegionViewModel(interactor) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}
