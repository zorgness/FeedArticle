package com.example.feedarticle

import SHAREDPREF_NAME
import SHAREDPREF_SESSION
import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.*
import com.example.feedarticle.dataclass.CreaArticleDto
import com.example.feedarticle.dataclass.SessionDto
import com.example.feedarticle.dataclass.UpdateArticleDto
import com.squareup.moshi.Moshi
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory
import com.squareup.picasso.Picasso
import de.hdodenhof.circleimageview.CircleImageView
import getArticleById
import insertArticle
import updateArticle

class CreateOrEditActivity : AppCompatActivity(), AdapterView.OnItemSelectedListener {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_create_or_edit)

        val etUrlImg = findViewById<EditText>(R.id.et_article_url_img)
        val etTitle = findViewById<EditText>(R.id.et_article_title)
        val etDescription = findViewById<EditText>(R.id.et_article_description)
        val spinnerCategory = findViewById<Spinner>(R.id.spinner_category_form)
        val civImgPrev = findViewById<CircleImageView>(R.id.civ_article_img_preview)

        var articleId: Int? = null


        var session: SessionDto? = convertJsonToDto(
            applicationContext
                .getSharedPreferences(SHAREDPREF_NAME, Context.MODE_PRIVATE)
                .getString(SHAREDPREF_SESSION, null)

        )

        intent.getStringExtra(MainActivity.KEY_ARTICLE_ID)?.let {
            getArticleById(it.toLong(), session?.token!!, articleDtoCallback = {article->
                articleId = article?.id
                etUrlImg.setText(article?.urlImage)
                etTitle.setText(article?.titre)
                etDescription.setText(article?.descriptif)
                spinnerCategory.setSelection(article?.categorie!! - 1)
            })
        }


        //debug
        session?.let {
            Log.i("session", session.token)
        }


        // Create an ArrayAdapter using the string array and a default spinner layout
        ArrayAdapter.createFromResource(
            this,
            R.array.categories_form,
            android.R.layout.simple_spinner_item
        ).also { adapter ->
            // Specify the layout to use when the list of choices appears
            adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
            // Apply the adapter to the spinner
            spinnerCategory.adapter = adapter
            spinnerCategory.onItemSelectedListener = this
        }

        //onFocusChange to implement

        etUrlImg.onFocusChangeListener = View.OnFocusChangeListener { view, isFocus ->
            val urlImageToVisualize = etUrlImg.text.toString().trim { it <= ' ' }
            if (!urlImageToVisualize.isEmpty() && !isFocus) {
                Picasso.get()
                    .load(urlImageToVisualize)
                    .resize(300, 300)
                    .error(com.google.android.material.R.drawable.navigation_empty_icon)
                    .into(civImgPrev)

            } else if (urlImageToVisualize.isEmpty()) {
                Picasso.get()
                    .load(android.R.color.transparent)
                    .resize(300, 300)
                    .into(civImgPrev)
            }
        }


        findViewById<Button>(R.id.btn_new_edit_article).setOnClickListener {
            val urlImg = etUrlImg.text.toString()
            val title = etTitle.text.toString()
            val description = etDescription.text.toString()
            val idCategory = spinnerCategory.selectedItemId.toInt() + 1

            if (urlImg.isNotBlank() && title.isNotBlank() && description.isNotBlank()) {

                if (session != null) {

                   if(articleId != null)
                        updateArticle(
                            UpdateArticleDto(
                                articleId!!,
                                title,
                                description,
                                urlImg,
                                idCategory,
                                session.token
                            ), articleDtoCallback = {
                                //toast user if successful


                            }
                        ) else
                        insertArticle(
                            CreaArticleDto(
                                session.id,
                                title,
                                description,
                                urlImg,
                                idCategory,
                                session.token
                            ), articleDtoCallback = {
                                //toast user if successful


                            }
                        )




                    //
                    Toast.makeText(this@CreateOrEditActivity, "success", Toast.LENGTH_SHORT).show()
                    startActivity(Intent(this@CreateOrEditActivity,MainActivity::class.java))
                    finish()
                }

            } else {
                //ERROR
            }

        }
    }


    ////////////////////////////////////////////////////////////////

    fun convertJsonToDto(jsonStr: String?): SessionDto? {
        return jsonStr?.let {
            Moshi.Builder().addLast(KotlinJsonAdapterFactory())
                .build().adapter(SessionDto::class.java).fromJson(it)
        }
    }

    override fun onItemSelected(p0: AdapterView<*>?, p1: View?, p2: Int, p3: Long) {}
    override fun onNothingSelected(p0: AdapterView<*>?) {}

}