import com.example.feedarticle.ApiService
import com.example.feedarticle.dataclass.ArticleDto
import com.example.feedarticle.dataclass.GetArticlesDto
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response


fun getRemoteArticle(token: String, articlesDtoCallback: (List<ArticleDto>) -> Unit) {
    var call: Call<GetArticlesDto>? = ApiService.getApi().getAllArticles(token)
    call?.enqueue(object : Callback<GetArticlesDto> {
        override fun onResponse(call: Call<GetArticlesDto>, response: Response<GetArticlesDto>) {
            response.body()?.let {
                articlesDtoCallback.invoke(it.articles)
            }
        }

        override fun onFailure(call: Call<GetArticlesDto>, t: Throwable) {
            TODO("Not yet implemented")
        }

    })
}