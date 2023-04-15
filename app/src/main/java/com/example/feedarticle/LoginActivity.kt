package com.example.feedarticle

import SHAREDPREF_LOGIN_TOKEN
import SHAREDPREF_NAME
import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
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
                        with(it.token) {
                            applicationContext.getSharedPreferences(SHAREDPREF_NAME, android.content.Context.MODE_PRIVATE)
                                .edit()
                                .putString(SHAREDPREF_LOGIN_TOKEN, this)
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
}