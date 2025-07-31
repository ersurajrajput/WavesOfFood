package com.example.wavesoffood.ui.user

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.wavesoffood.R

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
        val tv_singup = findViewById<TextView>(R.id.tv_singup)
        val btn_login = findViewById<Button>(R.id.btn_login)


        btn_login.setOnClickListener{
            val email = et_email.text.toString().trim()
            val pass = et_pass.text.toString().trim()
            if (email.isEmpty()){
                et_email.error = "require"
                return@setOnClickListener
            }
            if (pass.isEmpty()){
                et_pass.error = "required"
                return@setOnClickListener
            }
            val intent = Intent(applicationContext,MainActivity::class.java)
            startActivity(intent)
        }
        tv_singup.setOnClickListener{
            val intent = Intent(applicationContext,SignupActivity::class.java)
            startActivity(intent)
        }
        tv_forgetPass.setOnClickListener{
            val intent = Intent(applicationContext,ForgetPasswordActivity::class.java)
            startActivity(intent)
        }




    }
}