package ru.practicum.android.diploma.di

import android.content.Context
import com.google.gson.Gson
import org.koin.android.ext.koin.androidContext
import org.koin.dsl.module
import ru.practicum.android.diploma.data.EXAMPLE_PREFERENCES
import ru.practicum.android.diploma.data.NetworkClient
import ru.practicum.android.diploma.data.network.RetrofitClient
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
}
