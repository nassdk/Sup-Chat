package com.nassdk.supchat.presentation.news.ui

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.domain.model.ArticlesModel
import com.nassdk.supchat.R

class NewsAdapter : RecyclerView.Adapter<NewsAdapter.NewsViewHolder>() {

    private var data = arrayListOf<ArticlesModel>()

    fun setData(articles: List<ArticlesModel>) {

        data.run {
            clear()
            addAll(articles)
        }
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) = NewsViewHolder(
            view = LayoutInflater.from(parent.context).inflate(R.layout.item_news, parent, false)
    )

    override fun getItemCount() = data.size
    override fun onBindViewHolder(holder: NewsViewHolder, position: Int) {
        holder.bind(data[position])
    }

    inner class NewsViewHolder(view: View) : RecyclerView.ViewHolder(view) {

        fun bind(article: ArticlesModel) {

        }
    }
}