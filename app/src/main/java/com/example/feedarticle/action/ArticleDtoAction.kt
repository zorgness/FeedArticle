import com.example.feedarticle.ApiService
import com.example.feedarticle.dataclass.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response


fun getRemoteArticles(token: String, articleDtoCallback: (List<ArticleDto>) -> Unit) {
    var call: Call<GetArticlesDto>? = ApiService.getApi().getAllArticles(token)
    call?.enqueue(object : Callback<GetArticlesDto> {
        override fun onResponse(call: Call<GetArticlesDto>, response: Response<GetArticlesDto>) {
            response.body()?.let {
                articleDtoCallback.invoke(it.articles)
            }
        }

        override fun onFailure(call: Call<GetArticlesDto>, t: Throwable) {
            TODO("Not yet implemented")
        }

    })
}

fun getArticle(id: Long, token: String, articleDtoCallback: (ArticleDto?) -> Unit) {
    var call: Call<GetArticleDto>? = ApiService.getApi().getArticle(id, token)
    call?.enqueue(object : Callback<GetArticleDto> {
        override fun onResponse(call: Call<GetArticleDto>, response: Response<GetArticleDto>) {
            response.body()?.let {
                articleDtoCallback.invoke(it.article)
            }

        }

        override fun onFailure(call: Call<GetArticleDto>, t: Throwable) {
            TODO("Not yet implemented")
        }
    })
}

fun insertArticle(newArticle: CreaArticleDto, articleDtoCallback: (ReturnDto?) -> Unit) {
    var call: Call<ReturnDto>? = ApiService.getApi().newArticle(newArticle)

    call?.enqueue(object : Callback<ReturnDto> {
        override fun onResponse(call: Call<ReturnDto>, response: Response<ReturnDto>) {
            response.body()?.let {
                articleDtoCallback.invoke(it)
            }
        }

        override fun onFailure(call: Call<ReturnDto>, t: Throwable) {
            TODO("Not yet implemented")
        }

    })
}

fun updateArticle(updatedArticle: UpdateArticleDto, articleDtoCallback: (ReturnDto?) -> Unit) {
    var call: Call<ReturnDto>? = ApiService.getApi().updateArticle(updatedArticle)

    call?.enqueue(object : Callback<ReturnDto> {
        override fun onResponse(call: Call<ReturnDto>, response: Response<ReturnDto>) {
            response.body()?.let {
                articleDtoCallback.invoke(it)
            }
        }

        override fun onFailure(call: Call<ReturnDto>, t: Throwable) {
            TODO("Not yet implemented")
        }

    })
}

fun deleteArticle(idArticle: Long,token: String, articleDtoCallback: (ReturnDto?) -> Unit) {
    var call: Call<ReturnDto>? = ApiService.getApi().deleteCountry(idArticle, token)

    call?.enqueue(object : Callback<ReturnDto> {
        override fun onResponse(call: Call<ReturnDto>, response: Response<ReturnDto>) {
            response.body()?.let {
                articleDtoCallback.invoke(it)
            }
        }

        override fun onFailure(call: Call<ReturnDto>, t: Throwable) {
            TODO("Not yet implemented")
        }

    })
}