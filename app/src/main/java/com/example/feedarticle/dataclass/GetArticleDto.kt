package com.example.feedarticle.dataclass


import com.squareup.moshi.Json

data class GetArticleDto(
    @Json(name = "status")
    val status: String,
    @Json(name = "article")
    val article: ArticleDto?
)