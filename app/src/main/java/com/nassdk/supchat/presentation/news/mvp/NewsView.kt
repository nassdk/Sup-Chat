package com.nassdk.supchat.presentation.news.mvp

import com.arellomobile.mvp.viewstate.strategy.OneExecutionStateStrategy
import com.arellomobile.mvp.viewstate.strategy.StateStrategyType
import com.example.domain.model.ArticlesModel
import com.nassdk.supchat.global.BaseView

interface NewsView : BaseView {

    @StateStrategyType(OneExecutionStateStrategy::class)
    fun showNews(articles: List<ArticlesModel>)
}