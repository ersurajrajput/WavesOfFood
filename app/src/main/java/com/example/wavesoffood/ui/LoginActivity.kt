package com.example.wavesoffood.ui

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.wavesoffood.R
import com.example.wavesoffood.R.id.tv_signup

class LoginActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_login)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
        val et_email = findViewById<EditText>(R.id.et_email)
        val et_pass = findViewById<EditText>(R.id.et_pass)
        val tv_forgetPass = findViewById<TextView>(R.id.tv_forgetPass)
        val tv_signup = findViewById<TextView>(R.id.tv_signup)
        val btn_login = findViewById<Button>(R.id.btn_login)

        btn_login.setOnClickListener {
            val email = et_email.text.toString().trim()
            val pass = et_pass.text.toString().trim()
            if (email.isEmpty()){
                et_email.error = "required"
                return@setOnClickListener
            }
            if (pass.isEmpty()){
                et_pass.error = "required"
                return@setOnClickListener
            }
            Toast.makeText(applicationContext,email+":"+pass,Toast.LENGTH_SHORT).show()
        }
        tv_forgetPass.setOnClickListener{
            val intent = Intent(applicationContext,ForgetPassActivity::class.java)
            startActivity(intent)
        }
        tv_signup.setOnClickListener{
            Toast.makeText(applicationContext,"coming soon",Toast.LENGTH_SHORT).show()

        }

    }
}