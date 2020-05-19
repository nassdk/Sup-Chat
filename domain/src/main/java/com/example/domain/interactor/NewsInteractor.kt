package com.example.domain.interactor

import com.example.domain.common.SchedulersProvider
import com.example.domain.repository.NewsRepository

class NewsInteractor(
        private val repository: NewsRepository,
        private val schedulers: SchedulersProvider
) {
    fun getNews() = repository.getNews()
            .observeOn(schedulers.ui)
}