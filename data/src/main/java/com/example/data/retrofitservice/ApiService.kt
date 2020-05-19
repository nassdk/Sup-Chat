package com.example.data.retrofitservice

import com.example.data.model.NewsNetResponse
import io.reactivex.Single
import retrofit2.http.GET
import retrofit2.http.Query

interface ApiService {

    @GET("everything")
    fun getNews(
            @Query("q") about: String = "bitcoin",
            @Query("apiKey") apiKey: String = "cbc0e7334762497abe9db7092e9b1d1c"
    ): Single<NewsNetResponse>
}