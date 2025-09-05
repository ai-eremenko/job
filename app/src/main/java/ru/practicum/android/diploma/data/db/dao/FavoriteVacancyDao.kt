package ru.practicum.android.diploma.data.db.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import kotlinx.coroutines.flow.Flow
import ru.practicum.android.diploma.data.db.entity.FavoriteVacancyEntity

@Dao
interface FavoriteVacancyDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun addToFavorite(vacancy: FavoriteVacancyEntity)

    @Delete
    fun removeFromFavorite(vacancy: FavoriteVacancyEntity)

    @Query("SELECT * FROM favorite_vacancies ORDER BY id DESC")
    fun getFavorite(): Flow<List<FavoriteVacancyEntity>>

    @Query("SELECT * FROM favorite_vacancies WHERE id = :id")
    fun getVacancyById(id: String): FavoriteVacancyEntity?

    @Query("SELECT id FROM favorite_vacancies")
    fun getFavoriteIds(): Flow<List<String>>

    @Query("DELETE FROM favorite_vacancies WHERE id = :id")
    fun removeById(id: String)
}
