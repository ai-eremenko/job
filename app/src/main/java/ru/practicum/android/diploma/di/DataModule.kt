package ru.practicum.android.diploma.di

import android.content.Context
import androidx.room.Room
import com.google.gson.Gson
import org.koin.android.ext.koin.androidContext
import org.koin.dsl.module
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import ru.practicum.android.diploma.data.NetworkClient
import ru.practicum.android.diploma.data.db.AppDatabase
import ru.practicum.android.diploma.data.network.RetrofitClient
import ru.practicum.android.diploma.data.network.VacanciesApi
import ru.practicum.android.diploma.data.storage.EXAMPLE_PREFERENCES
import ru.practicum.android.diploma.data.storage.Storage
import ru.practicum.android.diploma.util.NetworkManager

val dataModule = module {

    single {
        androidContext()
            .getSharedPreferences(EXAMPLE_PREFERENCES, Context.MODE_PRIVATE)
    }

    factory { Gson() }

    single {
        NetworkManager(androidContext())
    }

    single<NetworkClient> {
        RetrofitClient(get(), get())
    }

    single<VacanciesApi> {
        Retrofit.Builder()
            .baseUrl("https://practicum-diploma-8bc38133faba.herokuapp.com/")
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(VacanciesApi::class.java)
    }

    single<AppDatabase> {
        Room.databaseBuilder(
            androidContext(),
            AppDatabase::class.java,
            "favorite_vacancies.db"
        )
            .fallbackToDestructiveMigration()
            .build()
    }

    single {
        Storage(get(), get())
    }

    single {
        get<AppDatabase>().favoriteVacancyDao()
    }
}
