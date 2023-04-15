import com.example.feedarticle.ApiService
import com.example.feedarticle.dataclass.SessionDto
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

fun register(login: String, mdp: String, loginCallback: (SessionDto) -> Unit) {
    var call: Call<SessionDto>? = ApiService.getApi().register(login, mdp)
    call?.enqueue(object : Callback<SessionDto> {
        override fun onResponse(call: Call<SessionDto>, response: Response<SessionDto>) {
            response.body()?.let {
                loginCallback(it)
            }
        }

        override fun onFailure(call: Call<SessionDto>, t: Throwable) {
            TODO("Not yet implemented")
        }

    })
}

fun login(login: String, mdp: String, loginCallback: (SessionDto) -> Unit) {
    var call: Call<SessionDto>? = ApiService.getApi().login(login, mdp)
    call?.enqueue(object : Callback<SessionDto> {
        override fun onResponse(call: Call<SessionDto>, response: Response<SessionDto>) {
            response.body()?.let {
                loginCallback(it)
            }
        }

        override fun onFailure(call: Call<SessionDto>, t: Throwable) {
            TODO("Not yet implemented")
        }

    })
}