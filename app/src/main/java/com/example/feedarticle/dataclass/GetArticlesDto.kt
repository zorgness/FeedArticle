package com.example.feedarticle.dataclass


import com.squareup.moshi.Json

data class GetArticlesDto(
    @Json(name = "status")
    val status: String,
    @Json(name = "articles")
    val articles: List<ArticleDto> = listOf()
)