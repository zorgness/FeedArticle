package com.example.feedarticle

import SHAREDPREF_SESSION
import SHAREDPREF_NAME
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.JsonReader
import android.util.Log
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import com.example.feedarticle.dataclass.SessionDto
import com.google.gson.Gson
import com.google.gson.GsonBuilder
import login

class LoginActivity : AppCompatActivity() {


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)

        val etLogin = findViewById<EditText>(R.id.et_user_login)
        val etPassword = findViewById<EditText>(R.id.et_user_password)

        findViewById<TextView>(R.id.tv_register_login_activity).setOnClickListener {
            startActivity(Intent(this, RegisterActivity::class.java))
            finish()
        }

        findViewById<Button>(R.id.btn_submit_login).setOnClickListener {
            val login = etLogin.text.toString()
            val password = etPassword.text.toString()

            if(login.isNotBlank() && password.isNotBlank()) {

                    login(login, password, loginCallback = {
                        with(it) {
                            Log.i("login", convertDtoToJsonStr(it))
                            applicationContext.getSharedPreferences(SHAREDPREF_NAME, android.content.Context.MODE_PRIVATE)
                                .edit()
                                .putString(SHAREDPREF_SESSION, convertDtoToJsonStr(this))
                                .apply()
                            startActivity(Intent(this@LoginActivity, com.example.feedarticle.MainActivity::class.java))
                            finish()
                        }
                    })

            } else {
                //error message field is empty
            }
        }
    }

    //
    fun convertDtoToJsonStr(session: SessionDto): String {
        val gson = Gson()
        val gsonPretty = GsonBuilder().setPrettyPrinting().create()
        return gson.toJson(session)
    }

}