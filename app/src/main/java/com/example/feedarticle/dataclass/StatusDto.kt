package com.example.feedarticle.dataclass


import com.squareup.moshi.Json

data class StatusDto(
    @Json(name = "status")
    val status: Int
)