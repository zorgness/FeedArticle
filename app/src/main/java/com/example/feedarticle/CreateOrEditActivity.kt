package com.example.feedarticle

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Spinner

class CreateOrEditActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_create_or_edit)

        val etUrlImg = findViewById<EditText>(R.id.et_article_url_img)
        val etTitle = findViewById<EditText>(R.id.et_article_title)
        val etDescription = findViewById<EditText>(R.id.et_article_description)
        val spinnerCategory = findViewById<Spinner>(R.id.spinner_category_form)


        findViewById<Button>(R.id.btn_new_edit_article).setOnClickListener {
            val urlImg = etUrlImg.text.toString()
            val title = etTitle.text.toString()
            val description = etDescription.text.toString()

        }
    }
}