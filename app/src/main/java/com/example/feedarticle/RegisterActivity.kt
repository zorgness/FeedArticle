package com.example.feedarticle

import SHAREDPREF_SESSION
import SHAREDPREF_NAME
import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import com.example.feedarticle.dataclass.SessionDto
import com.google.gson.Gson
import com.google.gson.GsonBuilder
import register

class RegisterActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_register)

        val etLogin = findViewById<EditText>(R.id.et_user_login)
        val etPassword = findViewById<EditText>(R.id.et_user_password)
        val etPwdConfirm = findViewById<EditText>(R.id.et_user_password_confirm)




        findViewById<Button>(R.id.btn_submit_register).setOnClickListener {
            val login = etLogin.text.toString()
            val password = etPassword.text.toString()
            val confirm = etPwdConfirm.text.toString()

            if(login.isNotBlank() && password.isNotBlank()) {
                if(validatePassword(password, confirm)) {
                    register(login, password, loginCallback = {
                        with(it) {
                            applicationContext.getSharedPreferences(SHAREDPREF_NAME, Context.MODE_PRIVATE)
                                .edit()
                                .putString(SHAREDPREF_SESSION, convertDtoToJsonStr(this))
                                .apply()
                            startActivity(Intent(this@RegisterActivity, MainActivity::class.java))
                            finish()
                        }
                    })

                } else {
                    //error message password and confirm not the same
                }
            } else {
                //error message field is empty
            }
        }
    }
    private fun validatePassword(password: String, confirm: String): Boolean {
        return password == confirm
    }


    ////////////////////////////////////////////////////////////////
    fun convertDtoToJsonStr(session: SessionDto): String {
        val gson = Gson()
        val gsonPretty = GsonBuilder().setPrettyPrinting().create()
        return gson.toJson(session)
    }
}