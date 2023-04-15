package com.example.feedarticle

import SHAREDPREF_LOGIN_TOKEN
import SHAREDPREF_NAME
import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button


class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        with(applicationContext.getSharedPreferences(SHAREDPREF_NAME, Context.MODE_PRIVATE)) {
            findViewById<Button>(R.id.btn_logout).setOnClickListener {
                edit().remove(SHAREDPREF_LOGIN_TOKEN).apply()
                startActivity(Intent(this@MainActivity, LoginActivity::class.java))
                finish()
            }
        }


        findViewById<Button>(R.id.btn_new_article).setOnClickListener {
            startActivity(Intent(this@MainActivity, CreateOrEditActivity::class.java))
            finish()
        }

    }
}