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
import getRemoteArticles


class MainActivity : AppCompatActivity(), AdapterView.OnItemSelectedListener {

    companion object {
        const val KEY_ARTICLE_ID = "key article id"
    }

    var callBack: ((Int) -> Unit)? = null
    private lateinit var recyclerView: RecyclerView
    private lateinit var articleAdapter: ArticleAdapter
    lateinit var spinnerCategory: Spinner
    var session: SessionDto? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        recyclerView = findViewById(R.id.rv_article)
        recyclerView.layoutManager = LinearLayoutManager(this)
        articleAdapter = ArticleAdapter()
        recyclerView.adapter = articleAdapter

        spinnerCategory = findViewById<Spinner>(R.id.spinner_category)




        var session: SessionDto? = convertJsonToDto(
            applicationContext
                .getSharedPreferences(SHAREDPREF_NAME, Context.MODE_PRIVATE)
                .getString(SHAREDPREF_SESSION, null)

        )


        fun refresh() {
            if (session != null) {
                getRemoteArticles(session.token){
                    if(spinnerCategory.selectedItemId.toInt() > 0) {
                        articleAdapter.setArticles(it.filter { el -> el.categorie == spinnerCategory.selectedItemId.toInt() })
                    } else
                        articleAdapter.setArticles(it)
                }
            }
        }

        // Create an ArrayAdapter using the string array and a default spinner layout
        ArrayAdapter.createFromResource(
            this,
            R.array.categories,
            android.R.layout.simple_spinner_item
        ).also { adapter ->
            // Specify the layout to use when the list of choices appears
            adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
            // Apply the adapter to the spinner
            spinnerCategory.adapter = adapter
            spinnerCategory.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
                override fun onItemSelected(p0: AdapterView<*>?, p1: View?, p2: Int, p3: Long) {
                    //refresh()
                    println("spinnerCategory")
                    refresh()
                }

                override fun onNothingSelected(p0: AdapterView<*>?) {
                    TODO("Not yet implemented")
                }

            }
        }

        articleAdapter.onShowItemCallback = {
            //
            startActivity(Intent(this, DetailsActivity::class.java).apply {
                putExtra(KEY_ARTICLE_ID,it.id.toString())
            })

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


    ////////////////////////////////////////////////////////////////
    fun convertJsonToDto(jsonStr: String?): SessionDto? {
        return jsonStr?.let {
            Moshi.Builder().addLast(KotlinJsonAdapterFactory())
                .build().adapter(SessionDto::class.java).fromJson(it)
        }
    }



    override fun onItemSelected(p0: AdapterView<*>?, p1: View?, p2: Int, p3: Long) {
        println("onItemSelected $p2")
        //refresh()


    }

    override fun onNothingSelected(p0: AdapterView<*>?) {
        //TODO("Not yet implemented")
    }
}