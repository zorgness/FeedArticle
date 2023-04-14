package com.example.feedarticle.dataclass


import com.squareup.moshi.Json

data class LoginDto(
    @Json(name = "status")
    val status: Int,
    @Json(name = "id")
    val id: Int,
    @Json(name = "token")
    val token: String
)