package com.example.domain.model

data class ArticlesModel(
        val source: SourceModel,
        val author: String?,
        val title: String,
        val description: String,
        val url: String,
        val urlToImage: String,
        val publishedAt: String,
        val content: String?
) {
    data class SourceModel(
            val id: String?,
            val sourceName: String
    )
}