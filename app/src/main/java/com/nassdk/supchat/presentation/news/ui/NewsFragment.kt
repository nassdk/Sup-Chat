package com.nassdk.supchat.presentation.news.ui

import com.arellomobile.mvp.presenter.InjectPresenter
import com.example.domain.model.ArticlesModel
import com.nassdk.supchat.R
import com.nassdk.supchat.global.BaseFragment
import com.nassdk.supchat.presentation.news.mvp.NewsPresenter
import com.nassdk.supchat.presentation.news.mvp.NewsView
import kotlinx.android.synthetic.main.screen_news.*

class NewsFragment : BaseFragment(), NewsView {

    override val resourceLayout = R.layout.screen_news

    @InjectPresenter
    lateinit var presenter: NewsPresenter

    private val adapter: NewsAdapter by lazy { NewsAdapter() }

    override fun showNews(articles: List<ArticlesModel>) {
        adapter.setData(articles = articles)
        recViewNews.adapter = adapter
    }

    override fun onBackPressed() {}
}