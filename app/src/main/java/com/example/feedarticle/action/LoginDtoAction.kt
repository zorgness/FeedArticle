import com.example.feedarticle.ApiService
import com.example.feedarticle.dataclass.LoginDto
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

fun register(login: String, mdp: String, loginCallback: (LoginDto) -> Unit) {
    var call: Call<LoginDto>? = ApiService.getApi().register(login, mdp)
    call?.enqueue(object : Callback<LoginDto> {
        override fun onResponse(call: Call<LoginDto>, response: Response<LoginDto>) {
            response.body()?.let {
                loginCallback(it)
            }
        }

        override fun onFailure(call: Call<LoginDto>, t: Throwable) {
            TODO("Not yet implemented")
        }

    })
}

fun login(login: String, mdp: String, loginCallback: (LoginDto) -> Unit) {
    var call: Call<LoginDto>? = ApiService.getApi().login(login, mdp)
    call?.enqueue(object : Callback<LoginDto> {
        override fun onResponse(call: Call<LoginDto>, response: Response<LoginDto>) {
            response.body()?.let {
                loginCallback(it)
            }
        }

        override fun onFailure(call: Call<LoginDto>, t: Throwable) {
            TODO("Not yet implemented")
        }

    })
}