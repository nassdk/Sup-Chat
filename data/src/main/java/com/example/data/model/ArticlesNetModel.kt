package com.example.data.model

import com.google.gson.annotations.SerializedName

data class ArticlesNetModel(
        @SerializedName("source")      val source: SourceNetModel,
        @SerializedName("author")      val author: String,
        @SerializedName("title")       val title: String,
        @SerializedName("description") val description: String,
        @SerializedName("url")         val url: String,
        @SerializedName("urlToImage")  val urlToImage: String,
        @SerializedName("publishedAt") val publishedAt: String,
        @SerializedName("content")     val content: String
) {
    data class SourceNetModel(
            @SerializedName("id")      val id: String?,
            @SerializedName("name")    val sourceName: String
    )
}