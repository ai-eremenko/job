package ru.practicum.android.diploma.presentation.vacancy

import android.graphics.drawable.Drawable
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch
import ru.practicum.android.diploma.R
import ru.practicum.android.diploma.domain.favorite.FavoriteInteractor
import ru.practicum.android.diploma.domain.sharing.SharingInteractor
import ru.practicum.android.diploma.domain.sharing.models.SharingIntent
import ru.practicum.android.diploma.domain.util.ResourcesProviderInteractor
import ru.practicum.android.diploma.domain.vacancy.VacancyInteractor
import ru.practicum.android.diploma.domain.vacancy.models.VacancyPresent
import ru.practicum.android.diploma.presentation.vacancy.models.NavigationEventState
import ru.practicum.android.diploma.presentation.vacancy.models.VacancyScreenState
import ru.practicum.android.diploma.util.Resource
import ru.practicum.android.diploma.util.ResponseStatus

class VacancyViewModel(
    private val vacancyId: String,
    private val vacancyInteractor: VacancyInteractor,
    private val resourcesProvider: ResourcesProviderInteractor,
    private val favoriteInteractor: FavoriteInteractor,
    private val sharingInteractor: SharingInteractor
) : ViewModel() {

    private var favoriteJob: Job? = null
    private lateinit var currentVacancy: VacancyPresent

    private var screenStateLiveData =
        MutableLiveData<VacancyScreenState>(VacancyScreenState.Loading)

    fun getScreenStateLiveData(): LiveData<VacancyScreenState> = screenStateLiveData

    private val navigationEvents = MutableLiveData<NavigationEventState>()
    fun getNavigationEvents(): LiveData<NavigationEventState> = navigationEvents

    init {
        loadVacancyModel(vacancyId)
    }

    private fun loadVacancyModel(vacancyId: String) {
        viewModelScope.launch {
            when (val model = vacancyInteractor.getVacancyById(vacancyId)) {
                is Resource.Success -> {
                    if (model.data != null) {
                        currentVacancy = model.data
                        changeFavoriteState()
                        screenStateLiveData.postValue(VacancyScreenState.Content(currentVacancy))
                    } else {
                        screenStateLiveData.postValue(VacancyScreenState.ErrorNotFound)
                    }
                }

                is Resource.Error -> {
                    screenStateLiveData.postValue(VacancyScreenState.ErrorNotFound)
                }
            }
        }
    }

    private fun getIcon(isFavorite: Boolean): Drawable? {
        return if (isFavorite) {
            resourcesProvider.getDrawable(R.drawable.ic_favorites_on)
        } else {
            resourcesProvider.getDrawable(R.drawable.ic_favorites_off)
        }
    }

    fun onFavoriteClicked() {
        favoriteJob?.cancel()
        favoriteJob = viewModelScope.launch {
            favoriteInteractor.toggleFavorite(currentVacancy)
            currentVacancy = currentVacancy.copy(isFavorite = !currentVacancy.isFavorite)
            changeFavoriteState()
        }
    }

    private fun changeFavoriteState() {
        val icon = getIcon(currentVacancy.isFavorite)
        screenStateLiveData.postValue(
            VacancyScreenState.Favorite(icon)
        )
    }

    fun share() {
        val intent = sharingInteractor.shareVacancy(currentVacancy.id, currentVacancy.name)
        navigate(intent)
    }

    fun sendEmail() {
        val intent = sharingInteractor.sendOnEmail(currentVacancy.contacts!!.email)
        navigate(intent)
    }

    fun call(number: String) {
        val intent = sharingInteractor.call(number)
        navigate(intent)
    }

    private fun navigate(intent: SharingIntent) {
        navigationEvents.value = NavigationEventState(
            intent = intent.intent,
            errorMessage = intent.error
        )
    }
}
