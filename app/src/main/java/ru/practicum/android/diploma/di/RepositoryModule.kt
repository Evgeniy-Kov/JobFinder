package ru.practicum.android.diploma.di

import org.koin.dsl.module
import ru.practicum.android.diploma.data.repository.FavouriteVacancyRepositoryImpl
import ru.practicum.android.diploma.data.repository.IndustriesRepositoryImpl
import ru.practicum.android.diploma.data.repository.PagingSourceRepositoryImpl
import ru.practicum.android.diploma.data.repository.VacancyDetailsRepositoryImpl
import ru.practicum.android.diploma.data.repository.VacancySearchRepositoryImpl
import ru.practicum.android.diploma.domain.api.FavouriteVacancyRepository
import ru.practicum.android.diploma.domain.api.IndustriesRepository
import ru.practicum.android.diploma.domain.api.PagingSourceRepository
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

    single<IndustriesRepository> {
        IndustriesRepositoryImpl(get())
    }
}
