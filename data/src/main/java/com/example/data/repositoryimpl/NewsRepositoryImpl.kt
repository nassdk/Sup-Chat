package com.example.data.repositoryimpl

import com.example.data.mapper.NewsMapper
import com.example.data.retrofitservice.ApiService
import com.example.domain.common.SchedulersProvider
import com.example.domain.repository.NewsRepository

class NewsRepositoryImpl(
        private val api: ApiService,
        private val newsMapper: NewsMapper,
        private val schedulers: SchedulersProvider
) : NewsRepository {

    override fun getNews() =
            api.getNews().map { newsMapper.map(it) }
                    .subscribeOn(schedulers.io)
}