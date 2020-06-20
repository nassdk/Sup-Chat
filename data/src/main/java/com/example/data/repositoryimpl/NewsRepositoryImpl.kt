package com.example.data.repositoryimpl

import android.util.Log
import com.example.data.mapper.NewsMapper
import com.example.data.retrofitservice.ApiService
import com.example.domain.common.SchedulersProvider
import com.example.domain.repository.NewsRepository
import io.reactivex.schedulers.Schedulers

class NewsRepositoryImpl(
        private val api: ApiService,
        private val newsMapper: NewsMapper
) : NewsRepository {

    override fun getNews() =
            api.getNews().map {
                newsMapper.map(it)
            }
                    .subscribeOn(Schedulers.io())
}