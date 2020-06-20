package com.example.di.module

import com.example.data.mapper.NewsMapper
import com.example.data.retrofitservice.ApiService
import com.google.gson.Gson
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import org.koin.dsl.module
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit


val dataModule = module {

    single {

        val builder = OkHttpClient().newBuilder()
        builder.readTimeout(15, TimeUnit.SECONDS)
        builder.connectTimeout(10, TimeUnit.SECONDS)

//        if (BuildConfig.DEBUG) {
//        }


        val interceptor = HttpLoggingInterceptor()
        interceptor.level = HttpLoggingInterceptor.Level.BODY
        //Extra Headers

        //builder.addNetworkInterceptor().add(chain -> {
        //  Request request = chain.request().newBuilder().addHeader("Authorization", authToken).build();
        //  return chain.proceed(request);
        //});

        val client = OkHttpClient.Builder().addInterceptor(interceptor).build()

        Retrofit
                .Builder()
                .baseUrl("https://newsapi.org/v2/")
                .addConverterFactory(GsonConverterFactory.create(Gson()))
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .build()
    }

    factory<ApiService> { get<Retrofit>().create(ApiService::class.java) }
    factory {
        return@factory NewsMapper()
    }

}