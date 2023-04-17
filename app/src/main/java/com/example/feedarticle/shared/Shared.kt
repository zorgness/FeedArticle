import android.content.Context
import android.widget.Toast
import com.example.feedarticle.dataclass.SessionDto
import com.google.gson.Gson
import com.google.gson.GsonBuilder
import com.squareup.moshi.Moshi
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory

fun convertJsonToDto(jsonStr: String?): SessionDto? {
    return jsonStr?.let {
        Moshi.Builder().addLast(KotlinJsonAdapterFactory())
            .build().adapter(SessionDto::class.java).fromJson(it)
    }
}

fun convertDtoToJsonStr(session: SessionDto): String {
    val gson = Gson()
    val gsonPretty = GsonBuilder().setPrettyPrinting().create()
    return gson.toJson(session)
}

fun getCategoryById(id: Int): String {
    when(id) {
        1 -> "Sport"
        2 -> "Manga"
        else -> "Divers"
    }.let {
        return it
    }
}

fun Context.myToast(message: String) {
    Toast.makeText(this, message, Toast.LENGTH_SHORT).show()
}

fun responseStatusArticle(status: Int?, action: String): String {
    when(status) {
        1 -> "article $action"
        0 -> "article not $action"
        -1 -> "parameters problem"
        -5 -> "unauthorized"
        else -> "something went wrong"
    }.let {
        return it
    }
}