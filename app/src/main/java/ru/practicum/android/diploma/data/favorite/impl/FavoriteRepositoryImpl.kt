package ru.practicum.android.diploma.data.favorite.impl

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import ru.practicum.android.diploma.data.db.dao.FavoriteVacancyDao
import ru.practicum.android.diploma.data.mappers.FavoriteVacancyMapper
import ru.practicum.android.diploma.domain.favorite.FavoriteRepository
import ru.practicum.android.diploma.domain.vacancy.models.VacancyPresent

class FavoriteRepositoryImpl(
    private val favoriteVacancyDao: FavoriteVacancyDao,
    private val favoriteVacancyMapper: FavoriteVacancyMapper,
) : FavoriteRepository {

    override suspend fun addToFavorite(vacancy: VacancyPresent) {
        favoriteVacancyDao.addToFavorite(favoriteVacancyMapper.toEntity(vacancy))
    }

    override suspend fun removeFromFavorite(vacancyId: String) {
        favoriteVacancyDao.removeById(vacancyId)
    }

    override fun getFavorite(): Flow<List<VacancyPresent>> {
        return favoriteVacancyDao.getFavorite().map { list ->
            list.map { favoriteVacancyMapper.toDomain(it) }.reversed()
        }
    }

    override suspend fun getVacancyById(id: String): VacancyPresent? {
        val entity = favoriteVacancyDao.getVacancyById(id)
        return entity?.let { favoriteVacancyMapper.toDomain(it) }
    }
}
