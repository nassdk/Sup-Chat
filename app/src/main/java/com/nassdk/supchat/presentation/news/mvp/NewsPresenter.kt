package com.nassdk.supchat.presentation.news.mvp

import android.util.Log
import com.arellomobile.mvp.InjectViewState
import com.example.domain.interactor.NewsInteractor
import com.nassdk.supchat.global.BasePresenter
import io.reactivex.rxkotlin.plusAssign
import io.reactivex.rxkotlin.subscribeBy
import org.koin.core.inject

@InjectViewState
class NewsPresenter : BasePresenter<NewsView>() {

    private val newsInteractor: NewsInteractor by inject()

    override fun onFirstViewAttach() = loadNews()

    private fun loadNews() {

        subscriptions += newsInteractor.getNews()
                .doOnSubscribe    { viewState.showLoading() }
                .doAfterTerminate { viewState.hideLoading() }
                .subscribeBy(
                        onSuccess = {
                            viewState.showNews(it.articles)
                        },
                        onError = {
                            Log.e("ERROR", it.message ?: "")
                        })
    }
}