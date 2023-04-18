package com.example.feedarticle

import SHAREDPREF_SESSION
import SHAREDPREF_NAME
import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.Button
import android.widget.Spinner
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.feedarticle.dataclass.SessionDto
import com.squareup.moshi.Moshi
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory
import convertJsonToDto
import getRemoteArticles
import myToast


class MainActivity : AppCompatActivity(){

    companion object {
        const val KEY_ARTICLE_ID = "key article id"
    }

    private lateinit var recyclerView: RecyclerView
    private lateinit var articleAdapter: ArticleAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        recyclerView = findViewById(R.id.rv_article)
        recyclerView.layoutManager = LinearLayoutManager(this)
        articleAdapter = ArticleAdapter()
        recyclerView.adapter = articleAdapter

        val spinnerCategory = findViewById<Spinner>(R.id.spinner_category)

        val session: SessionDto? = convertJsonToDto(
            applicationContext
                .getSharedPreferences(SHAREDPREF_NAME, Context.MODE_PRIVATE)
                .getString(SHAREDPREF_SESSION, null)

        )


        fun sendListToAdapter() {
            session?.let  {
                getRemoteArticles(it.token, articleDtoCallback = {list->
                    if(spinnerCategory.selectedItemPosition > 0) {
                        articleAdapter.setArticles(list.filter { el ->
                            el.categorie == spinnerCategory.selectedItemPosition})
                    } else
                        articleAdapter.setArticles(list)
                }, errorCallback = {error->
                    myToast(error.toString())
                } )
            }
        }


        ArrayAdapter.createFromResource(
            this,
            R.array.categories,
            android.R.layout.simple_spinner_item
        ).also { adapter ->
            adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
            spinnerCategory.adapter = adapter
            spinnerCategory.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
                override fun onItemSelected(p0: AdapterView<*>?, p1: View?, p2: Int, p3: Long) {
                    sendListToAdapter()
                }
                override fun onNothingSelected(p0: AdapterView<*>?) {}
            }
        }

        //SHOW DETAILS
        articleAdapter.onShowItemCallback = {
            //
            startActivity(Intent(this, DetailsActivity::class.java).apply {
                putExtra(KEY_ARTICLE_ID,it.id.toString())
            })

            finish()
        }


        //NEW ARTICLE
        findViewById<Button>(R.id.btn_new_article).setOnClickListener {
            startActivity(Intent(this@MainActivity, CreateOrEditActivity::class.java))
            finish()
        }

        //LOGOUT
        with(applicationContext.getSharedPreferences(SHAREDPREF_NAME, Context.MODE_PRIVATE)) {
            findViewById<Button>(R.id.btn_logout).setOnClickListener {
                edit().remove(SHAREDPREF_SESSION).apply()
                startActivity(Intent(this@MainActivity, LoginActivity::class.java))
                finish()
            }
        }


        sendListToAdapter()

    }
}