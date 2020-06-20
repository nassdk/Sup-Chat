package com.example.di.module

import com.example.data.repositoryimpl.NewsRepositoryImpl
import com.example.di.SchedulersProviderImpl
import com.example.domain.common.SchedulersProvider
import com.example.domain.interactor.NewsInteractor
import com.example.domain.repository.NewsRepository
import org.koin.dsl.module

val domainModule = module {

    single <SchedulersProvider> { SchedulersProviderImpl() }
    single <NewsRepository>     { NewsRepositoryImpl(get(), get()) }

    single { NewsInteractor(get(), get()) }
}