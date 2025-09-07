package ru.practicum.android.diploma.data.favorite.impl

import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.withContext
import ru.practicum.android.diploma.data.db.AppDatabase
import ru.practicum.android.diploma.data.mappers.FavoriteVacancyMapper
import ru.practicum.android.diploma.domain.favorite.FavoriteRepository
import ru.practicum.android.diploma.domain.vacancy.models.VacancyPresent

class FavoriteRepositoryImpl(
    private val appDatabase: AppDatabase,
    private val favoriteVacancyMapper: FavoriteVacancyMapper,
) : FavoriteRepository {

    override suspend fun toggleFavorite(vacancy: VacancyPresent) {
        withContext(Dispatchers.IO) {
            val existingIds = appDatabase.favoriteVacancyDao().getFavoriteIds().first()
            if (existingIds.contains(vacancy.id)) {
                appDatabase.favoriteVacancyDao().removeById(vacancy.id)
            } else {
                appDatabase.favoriteVacancyDao().addToFavorite(favoriteVacancyMapper.toEntity(vacancy))
            }
        }
    }

    override fun getFavorite(): Flow<List<VacancyPresent>> {
        return appDatabase.favoriteVacancyDao().getFavorite().map { list ->
            list.map { favoriteVacancyMapper.toDomain(it) }
        }
    }

    override suspend fun getVacancyById(id: String): VacancyPresent? {
        return withContext(Dispatchers.IO) {
            val entity = appDatabase.favoriteVacancyDao().getVacancyById(id)
            entity?.let { favoriteVacancyMapper.toDomain(it) }
        }
    }
}
