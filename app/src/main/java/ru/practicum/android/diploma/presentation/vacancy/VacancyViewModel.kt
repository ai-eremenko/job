package ru.practicum.android.diploma.presentation.vacancy

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch
import ru.practicum.android.diploma.R
import ru.practicum.android.diploma.domain.favorite.FavoriteInteractor
import ru.practicum.android.diploma.domain.sharing.SharingInteractor
import ru.practicum.android.diploma.domain.util.ResourcesProviderInteractor
import ru.practicum.android.diploma.domain.vacancy.VacancyInteractor
import ru.practicum.android.diploma.domain.vacancy.models.VacancyPresent
import ru.practicum.android.diploma.presentation.util.IntentCreator
import ru.practicum.android.diploma.presentation.vacancy.models.NavigationEventState
import ru.practicum.android.diploma.presentation.vacancy.models.VacancyScreenState
import ru.practicum.android.diploma.util.Resource

class VacancyViewModel(
    private val vacancyId: String,
    private val vacancyInteractor: VacancyInteractor,
    private val resourcesProvider: ResourcesProviderInteractor,
    private val favoriteInteractor: FavoriteInteractor,
    private val sharingInteractor: SharingInteractor
) : ViewModel() {

    private var favoriteJob: Job? = null
    private var currentVacancy: VacancyPresent? = null

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
                        screenStateLiveData.postValue(VacancyScreenState.Content(model.data))
                    } else {
                        screenStateLiveData.postValue(
                            VacancyScreenState.Error(
                                resourcesProvider.getDrawable(R.drawable.img_not_found),
                                resourcesProvider.getString(R.string.vacancy_not_found)
                            )
                        )
                    }
                }

                is Resource.Error -> {
                    screenStateLiveData.postValue(
                        VacancyScreenState.Error(
                            resourcesProvider.getDrawable(R.drawable.img_screen_vacancy_error_placeholder),
                            resourcesProvider.getString(R.string.server_error)
                        )
                    )
                }
            }
        }
    }

    fun onFavoriteClicked() {
        currentVacancy?.let { vacancy ->
            val updatedVacancy = vacancy.copy(isFavorite = !vacancy.isFavorite)
            currentVacancy = updatedVacancy

            favoriteJob?.cancel()
            favoriteJob = viewModelScope.launch {
                favoriteInteractor.toggleFavorite(updatedVacancy)
                changeFavoriteState()
            }
        }
    }

    private fun changeFavoriteState() {
        val vacancy = currentVacancy ?: return
        screenStateLiveData.postValue(
            VacancyScreenState.Favorite(vacancy.isFavorite)
        )
    }

    fun share() {
        val vacancy = currentVacancy ?: return
        val shareData = sharingInteractor.shareVacancy(vacancy.urlLink, vacancy.name)
        navigationEvents.value = NavigationEventState(
            intent = IntentCreator.createShareIntent(shareData.first),
            errorMessage = shareData.second
        )
    }

    fun sendEmail() {
        val vacancy = currentVacancy ?: return
        val emailData = sharingInteractor.sendOnEmail(vacancy.contacts!!.email)
        navigationEvents.value = NavigationEventState(
            intent = IntentCreator.createEmailIntent(emailData.first),
            errorMessage = emailData.second
        )
    }

    fun call(number: String) {
        val phoneData = sharingInteractor.call(number)
        navigationEvents.value = NavigationEventState(
            intent = IntentCreator.createPhoneIntent(phoneData.first),
            errorMessage = phoneData.second
        )
    }
}
