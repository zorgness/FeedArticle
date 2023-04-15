package com.example.feedarticle

import SHAREDPREF_SESSION
import SHAREDPREF_NAME
import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.feedarticle.dataclass.SessionDto
import com.squareup.moshi.Moshi
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory
import getRemoteArticles


class MainActivity : AppCompatActivity() {

    private lateinit var recyclerView: RecyclerView
    private lateinit var articleAdapter: ArticleAdapter
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        recyclerView = findViewById(R.id.rv_article)
        recyclerView.layoutManager = LinearLayoutManager(this)
        articleAdapter = ArticleAdapter()
        recyclerView.adapter = articleAdapter


        var session: SessionDto? = convertJsonToDto(
            applicationContext
                .getSharedPreferences(SHAREDPREF_NAME, Context.MODE_PRIVATE)
                .getString(SHAREDPREF_SESSION, null)

        )

        fun refresh() {
            if (session != null) {
                getRemoteArticles(session.token){
                    articleAdapter.setArticles(it)
                }
            }
        }

        articleAdapter.onShowItemCallback = {
            //
            startActivity(Intent(this, DetailsActivity::class.java))
            finish()
        }

        with(applicationContext.getSharedPreferences(SHAREDPREF_NAME, Context.MODE_PRIVATE)) {
            findViewById<Button>(R.id.btn_logout).setOnClickListener {
                edit().remove(SHAREDPREF_SESSION).apply()
                startActivity(Intent(this@MainActivity, LoginActivity::class.java))
                finish()
            }
        }


        findViewById<Button>(R.id.btn_new_article).setOnClickListener {
            startActivity(Intent(this@MainActivity, CreateOrEditActivity::class.java))
            finish()
        }


        refresh()

    }

    fun convertJsonToDto(jsonStr: String?): SessionDto? {
        return jsonStr?.let {
            Moshi.Builder().addLast(KotlinJsonAdapterFactory())
                .build().adapter(SessionDto::class.java).fromJson(it)
        }
    }
}