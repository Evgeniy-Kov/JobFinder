package ru.practicum.android.diploma.di

import android.content.Context
import android.content.SharedPreferences
import org.koin.android.ext.koin.androidContext
import org.koin.dsl.module
import ru.practicum.android.diploma.App.Companion.PREFERENCES
import ru.practicum.android.diploma.data.repository.FavouriteVacancyRepositoryImpl
import ru.practicum.android.diploma.data.repository.PagingSourceRepositoryImpl
import ru.practicum.android.diploma.data.repository.SharedPreferencesRepositoryImpl
import ru.practicum.android.diploma.data.repository.VacancyDetailsRepositoryImpl
import ru.practicum.android.diploma.data.repository.VacancySearchRepositoryImpl
import ru.practicum.android.diploma.domain.api.FavouriteVacancyRepository
import ru.practicum.android.diploma.domain.api.PagingSourceRepository
import ru.practicum.android.diploma.domain.api.SharedPreferencesRepository
import ru.practicum.android.diploma.domain.api.VacancyDetailsRepository
import ru.practicum.android.diploma.domain.api.VacancySearchRepository

val repositoryModule = module {
    single<FavouriteVacancyRepository> {
        FavouriteVacancyRepositoryImpl(get(), get())
    }

    single<VacancySearchRepository> {
        VacancySearchRepositoryImpl(get())
    }

    single<VacancyDetailsRepository> {
        VacancyDetailsRepositoryImpl(get())
    }

    single<PagingSourceRepository> {
        PagingSourceRepositoryImpl(get())
    }

    single<SharedPreferences> {
        androidContext().getSharedPreferences(PREFERENCES, Context.MODE_PRIVATE)
    }

    single<SharedPreferencesRepository> {
        SharedPreferencesRepositoryImpl(get())
    }
}
