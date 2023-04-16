package com.example.feedarticle

import SHAREDPREF_NAME
import SHAREDPREF_SESSION
import android.content.Context
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.ImageView
import android.widget.TextView
import com.example.feedarticle.dataclass.SessionDto
import com.squareup.moshi.Moshi
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory
import com.squareup.picasso.Picasso
import getArticleById

class DetailsActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_details)

        val tvTitle = findViewById<TextView>(R.id.tv_title_details)
        val tvDescription  = findViewById<TextView>(R.id.tv_description_details)
        val ivArticle = findViewById<ImageView>(R.id.iv_details)

        var session: SessionDto? = convertJsonToDto(
            applicationContext
                .getSharedPreferences(SHAREDPREF_NAME, Context.MODE_PRIVATE)
                .getString(SHAREDPREF_SESSION, null)

        )

        intent.getStringExtra(MainActivity.KEY_ARTICLE_ID)?.let {
            getArticleById(it.toLong(), session?.token!!, articleDtoCallback = {article->
                //etUrlImg.setText(article?.urlImage)
                tvTitle.text = article?.titre
                tvDescription.text = article?.descriptif
                Picasso.get()
                    .load(article?.urlImage)
                    .resize(300, 300)
                    .error(com.google.android.material.R.drawable.navigation_empty_icon)
                    .into(ivArticle)

            })
        }
    }


    fun convertJsonToDto(jsonStr: String?): SessionDto? {
        return jsonStr?.let {
            Moshi.Builder().addLast(KotlinJsonAdapterFactory())
                .build().adapter(SessionDto::class.java).fromJson(it)
        }
    }
}