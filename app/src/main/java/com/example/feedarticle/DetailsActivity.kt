package com.example.feedarticle

import SHAREDPREF_NAME
import SHAREDPREF_SESSION
import android.content.Context
import android.content.Intent
import android.nfc.NfcAdapter.OnTagRemovedListener
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import com.example.feedarticle.dataclass.SessionDto
import com.squareup.moshi.Moshi
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory
import com.squareup.picasso.Picasso
import convertJsonToDto
import deleteArticle
import getArticleById
import getCategoryById
import myToast
import responseStatusArticle

class DetailsActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_details)

        val tvTitle = findViewById<TextView>(R.id.tv_title_details)
        val tvCategory = findViewById<TextView>(R.id.tv_category_details)
        val tvDescription  = findViewById<TextView>(R.id.tv_description_details)
        val ivArticle = findViewById<ImageView>(R.id.iv_details)
        val btn_update = findViewById<Button>(R.id.btn_edit_article)
        val btn_delete = findViewById<Button>(R.id.btn_delete_article)


        var session: SessionDto? = convertJsonToDto(
            applicationContext
                .getSharedPreferences(SHAREDPREF_NAME, Context.MODE_PRIVATE)
                .getString(SHAREDPREF_SESSION, null)

        )

        intent.getStringExtra(MainActivity.KEY_ARTICLE_ID)?.let { it ->
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

                if(session.id != article.idU) {
                    btn_delete.visibility = View.GONE
                    btn_update.visibility = View.GONE
                }

                btn_update.setOnClickListener {
                    ////////////////////////////////
                    startActivity(Intent(this, CreateOrEditActivity::class.java).apply {
                        putExtra(MainActivity.KEY_ARTICLE_ID, article.id.toString())
                    })
                    finish()
                }

                btn_delete.setOnClickListener {

                    deleteArticle(article.id.toLong(), session.token, articleDtoCallback = {response->
                       myToast(responseStatusArticle(response.status, "deleted"))
                    })

                    ////////////////////////////////
                    startActivity(Intent(this, MainActivity::class.java))
                    finish()
                }

            })
        }
    }


}