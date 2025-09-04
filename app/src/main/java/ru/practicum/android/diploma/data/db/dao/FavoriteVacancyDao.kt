package ru.practicum.android.diploma.data.db.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import kotlinx.coroutines.flow.Flow
import ru.practicum.android.diploma.data.db.entity.FavoriteVacancyEntity

@Dao
interface FavoriteVacancyDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertVacancy(vacancy: FavoriteVacancyEntity)

    @Query("DELETE FROM favorite_vacancies WHERE id = :id")
    suspend fun deleteVacancy(id: String)

    @Query("SELECT * FROM favorite_vacancies ORDER BY id DESC")
    fun getVacancies(): Flow<List<FavoriteVacancyEntity>>

    @Query("SELECT * FROM favorite_vacancies WHERE id = :id")
    fun getVacancyById(id: String): FavoriteVacancyEntity?

    @Query("SELECT EXISTS(SELECT 1 FROM favorite_vacancies WHERE id = :id)")
    fun isVacancyFavorite(id: String): Flow<Boolean>

    @Query("SELECT id FROM favorite_vacancies")
    fun getFavoriteIds(): Flow<List<String>>
}
