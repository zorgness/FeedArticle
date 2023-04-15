package com.example.feedarticle

import android.media.Image
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.*
import com.example.feedarticle.dataclass.CreaArticleDto
import com.squareup.picasso.Picasso
import de.hdodenhof.circleimageview.CircleImageView
import insertArticle

class CreateOrEditActivity : AppCompatActivity(), AdapterView.OnItemSelectedListener {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_create_or_edit)

        val etUrlImg = findViewById<EditText>(R.id.et_article_url_img)
        val etTitle = findViewById<EditText>(R.id.et_article_title)
        val etDescription = findViewById<EditText>(R.id.et_article_description)
        val spinnerCategory = findViewById<Spinner>(R.id.spinner_category_form)
        val civImgPrev = findViewById<CircleImageView>(R.id.civ_article_img_preview)

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
            val idCategory = spinnerCategory.selectedItemId

            println("trololol "+idCategory)

            if(urlImg.isNotBlank() && title.isNotBlank() && description.isNotBlank()) {

                //insertArticle(CreaArticleDto())

            } else {
                //ERROR
            }

        }
    }

    override fun onItemSelected(p0: AdapterView<*>?, p1: View?, p2: Int, p3: Long) {}
    override fun onNothingSelected(p0: AdapterView<*>?) {}

}