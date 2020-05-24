package com.nassdk.supchat.presentation.news.mvp

import com.arellomobile.mvp.InjectViewState
import com.example.domain.interactor.NewsInteractor
import com.nassdk.supchat.domain.global.BasePresenter
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.rxkotlin.plusAssign
import io.reactivex.rxkotlin.subscribeBy
import org.koin.core.inject

@InjectViewState
class NewsPresenter : BasePresenter<NewsView>() {

    private val newsInteractor: NewsInteractor by inject()
    private val subscriptions = CompositeDisposable()

    override fun onFirstViewAttach() = loadNews()

    private fun loadNews() {

//        subscriptions += newsInteractor.getNews()
//                .doOnSubscribe    { viewState.showLoading() }
//                .doAfterTerminate { viewState.hideLoading() }
//                .subscribeBy(
//                        onSuccess = { viewState.showNews(it.articles) },
//                        onError = { //TODO
//                            })
    }

    override fun onDestroy() = subscriptions.dispose()
}