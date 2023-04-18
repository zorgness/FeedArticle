import android.util.Log
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
            //TODO("Not yet implemented")
        }

    })
}

fun getArticleById(id: Long, token: String, articleDtoCallback: (ArticleDto?) -> Unit) {
    var call: Call<GetArticleDto>? = ApiService.getApi().getArticle(id, token)
    call?.enqueue(object : Callback<GetArticleDto> {
        override fun onResponse(call: Call<GetArticleDto>, response: Response<GetArticleDto>) {
            response.body()?.let {
                articleDtoCallback.invoke(it.article)
            }

        }

        override fun onFailure(call: Call<GetArticleDto>, t: Throwable) {
            //TODO("Not yet implemented")
        }
    })
}

fun insertArticle(newArticle: CreaArticleDto, articleDtoCallback: (StatusDto) -> Unit, errorCallback: (StatusDto) -> Unit) {
    var call: Call<StatusDto>? = ApiService.getApi().newArticle(newArticle)

    call?.enqueue(object : Callback<StatusDto> {
        override fun onResponse(call: Call<StatusDto>, response: Response<StatusDto>) {
            response.body()?.let {
                if(response.isSuccessful)
                    articleDtoCallback.invoke(it)
                else
                    errorCallback.invoke(it)
            }
        }

        override fun onFailure(call: Call<StatusDto>, t: Throwable) {
            //TODO("Not yet implemented")
        }

    })
}

fun updateArticle(updatedArticle: UpdateArticleDto, articleDtoCallback: (StatusDto) -> Unit) {
    var call: Call<StatusDto>? = ApiService.getApi().updateArticle(updatedArticle)

    call?.enqueue(object : Callback<StatusDto> {
        override fun onResponse(call: Call<StatusDto>, response: Response<StatusDto>) {
            response.body()?.let {

                if(response.isSuccessful)
                    articleDtoCallback.invoke(it)
                else
                    Log.i("StatusDto", "Error updating article")
            }
        }

        override fun onFailure(call: Call<StatusDto>, t: Throwable) {
            //TODO("Not yet implemented")
        }

    })
}

fun deleteArticle(idArticle: Long, token: String, articleDtoCallback: (StatusDto) -> Unit) {
    var call: Call<StatusDto>? = ApiService.getApi().deleteCountry(idArticle, token)

    call?.enqueue(object : Callback<StatusDto> {
        override fun onResponse(call: Call<StatusDto>, response: Response<StatusDto>) {
            response.body()?.let {
                articleDtoCallback.invoke(it)
            }
        }

        override fun onFailure(call: Call<StatusDto>, t: Throwable) {
            //TODO("Not yet implemented")
        }

    })
}