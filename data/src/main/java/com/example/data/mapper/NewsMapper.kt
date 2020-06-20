package com.example.data.mapper

import com.example.data.model.ArticlesNetModel
import com.example.data.model.NewsNetResponse
import com.example.domain.model.ArticlesModel
import com.example.domain.model.NewsModel

class NewsMapper {
    fun map(item: NewsNetResponse) =
            NewsModel(
                    status = item.status,
                    totalResults = item.totalResults,
                    articles = mapArrayArticles(item.articles)
            )

    private fun mapArrayArticles(articles: List<ArticlesNetModel>) =
            articles.map {
                it.run {
                    ArticlesModel(
                            source = ArticlesModel.SourceModel(id = source.id, sourceName = source.sourceName),
                            author = author,
                            title = title,
                            description = description,
                            url = url,
                            urlToImage = urlToImage,
                            publishedAt = publishedAt,
                            content = content
                    )
                }
            }
}