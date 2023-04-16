package com.example.feedarticle

import SHAREDPREF_NAME
import SHAREDPREF_SESSION
import android.content.Context
import android.content.Intent
import android.nfc.NfcAdapter.OnTagRemovedListener
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import com.example.feedarticle.dataclass.SessionDto
import com.squareup.moshi.Moshi
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory
import com.squareup.picasso.Picasso
import deleteArticle
import getArticleById

class DetailsActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_details)

        val tvTitle = findViewById<TextView>(R.id.tv_title_details)
        val tvCategory = findViewById<TextView>(R.id.tv_category_details)
        val tvDescription  = findViewById<TextView>(R.id.tv_description_details)
        val ivArticle = findViewById<ImageView>(R.id.iv_details)

        var session: SessionDto? = convertJsonToDto(
            applicationContext
                .getSharedPreferences(SHAREDPREF_NAME, Context.MODE_PRIVATE)
                .getString(SHAREDPREF_SESSION, null)

        )

        intent.getStringExtra(MainActivity.KEY_ARTICLE_ID)?.let {
            println(it)
            getArticleById(it.toLong(), session?.token!!, articleDtoCallback = {article->
                tvCategory.text =  getCategoryById(article?.categorie!!)
                tvTitle.text = article.titre
                tvDescription.text = article.descriptif

                if (article.urlImage.isEmpty()) {
                    Picasso.get()
                        .load(android.R.color.transparent)
                        .resize(300, 300)
                        .into(ivArticle)
                } else {
                    Picasso.get()
                        .load(article.urlImage).error(R.drawable.ic_launcher_foreground)
                        .resize(300, 300)
                        .into(ivArticle)
                }

                findViewById<Button>(R.id.btn_edit_article).setOnClickListener {
                    ////////////////////////////////
                    startActivity(Intent(this, CreateOrEditActivity::class.java).apply {
                        putExtra(MainActivity.KEY_ARTICLE_ID, article.id.toString())
                    })
                    finish()
                }

                findViewById<Button>(R.id.btn_delete_article).setOnClickListener {

                    deleteArticle(article.id.toLong(), session.token, articleDtoCallback = {

                    })

                    ////////////////////////////////
                    startActivity(Intent(this, MainActivity::class.java))
                    finish()
                }

            })
        }


    }


////////////////////////////////////////////////////////////////
    fun convertJsonToDto(jsonStr: String?): SessionDto? {
        return jsonStr?.let {
            Moshi.Builder().addLast(KotlinJsonAdapterFactory())
                .build().adapter(SessionDto::class.java).fromJson(it)
        }
    }

    fun getCategoryById(id: Int): String {
        when(id) {
            0 -> "Sport"
            1 -> "Manga"
            else -> "Divers"
        }.let {
            return it
        }
    }
}