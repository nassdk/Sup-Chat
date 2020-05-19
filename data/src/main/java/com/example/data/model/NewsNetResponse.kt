package com.example.data.model

import com.google.gson.annotations.SerializedName

data class NewsNetResponse(
        @SerializedName("status")       val status: String,
        @SerializedName("totalResults") val totalResults: Int,
        @SerializedName("articles")     val articles: List<ArticlesNetModel>
)