package com.example.feedarticle.dataclass


import com.squareup.moshi.Json

data class ReturnDto(
    @Json(name = "return")
    val returnX: Int
)