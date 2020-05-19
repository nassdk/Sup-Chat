package com.example.domain.repository

import com.example.domain.model.NewsModel
import io.reactivex.Single

interface NewsRepository {
    fun getNews(): Single<NewsModel>
}