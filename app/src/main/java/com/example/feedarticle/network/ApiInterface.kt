package com.example.feedarticle.network

import com.example.feedarticle.dataclass.*
import retrofit2.Call
import retrofit2.http.*

interface ApiInterface {

    @FormUrlEncoded
    @POST(ApiRoutes.REGISTER)
    fun register(@Field("login") login: String, @Field("mdp") mdp: String): Call<SessionDto>?

    @FormUrlEncoded
    @POST(ApiRoutes.LOGIN)
    fun login(@Field("login") login: String, @Field("mdp") mdp: String): Call<SessionDto>?

    @GET(ApiRoutes.ARTICLES)
    fun getAllArticles(@Query("token") token: String): Call<GetArticlesDto>?

    @GET(ApiRoutes.ARTICLE)
    fun getArticle(@Query("id") idArticle: Long, @Query("token") token: String): Call<GetArticleDto>?

    @POST(ApiRoutes.NEW)
    fun newArticle(@Body newCountry: CreaArticleDto): Call<StatusDto>?

    @POST(ApiRoutes.UPDATE)
    fun updateArticle(@Body updatedCountry: UpdateArticleDto): Call<StatusDto>?

    @FormUrlEncoded
    @POST(ApiRoutes.DELETE)
    fun deleteCountry(@Field("id") idArticle: Long, @Field("token") token: String): Call<StatusDto>?
}