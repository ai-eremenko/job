package ru.practicum.android.diploma.data.db

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import ru.practicum.android.diploma.data.db.entity.FavoriteVacancyEntity
import ru.practicum.android.diploma.data.converter.Converters
import ru.practicum.android.diploma.data.db.dao.FavoriteVacancyDao

@Database(
    version = 7,
    entities = [
        FavoriteVacancyEntity::class
    ],
    exportSchema = false
)

@TypeConverters(Converters::class)
abstract class AppDatabase : RoomDatabase() {

    abstract fun favoriteVacancyDao(): FavoriteVacancyDao
}
