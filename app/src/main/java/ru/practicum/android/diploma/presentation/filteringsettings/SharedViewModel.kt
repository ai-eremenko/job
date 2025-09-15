package ru.practicum.android.diploma.presentation.filteringsettings

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class SharedViewModel : ViewModel() {
    private val _filtersUpdated = MutableLiveData<Boolean>()
    val filtersUpdated: LiveData<Boolean> = _filtersUpdated

    fun notifyFiltersApplied() {
        _filtersUpdated.value = true
    }

    fun resetFiltersNotification() {
        _filtersUpdated.value = false
    }
}
