package ru.practicum.android.diploma.domain.industrychoice.impl

import ru.practicum.android.diploma.domain.filteringsettings.FilterRepository
import ru.practicum.android.diploma.domain.filteringsettings.models.FilterSettings
import ru.practicum.android.diploma.domain.industrychoice.IndustryInteractor
import ru.practicum.android.diploma.domain.industrychoice.IndustryRepository
import ru.practicum.android.diploma.domain.industrychoice.models.Industry
import ru.practicum.android.diploma.util.Resource

class IndustryInteractorImpl(
    val repository: IndustryRepository,
    val filterRepository: FilterRepository
) : IndustryInteractor {
    override suspend fun getIndustries(): Resource<List<Industry>> {
        return repository.getIndustries()
    }

    override fun saveIndustry(industry: Industry) {
        val filter = filterRepository.getFilterOptions() ?: FilterSettings()
        val currentFilter = filter.copy(industryId = industry.id, industryName = industry.name)
        filterRepository.saveFilterOptions(currentFilter)
    }
}
