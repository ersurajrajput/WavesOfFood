package com.example.wavesoffood.ui.user

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

class SignupActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_signup)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
        val et_name = findViewById<EditText>(R.id.et_name)
        val et_email = findViewById<EditText>(R.id.et_email)
        val et_pass = findViewById<EditText>(R.id.et_pass)
        val et_cpass = findViewById<EditText>(R.id.et_cpass)
        val tv_login = findViewById<TextView>(R.id.tv_login)

        val btn_signup = findViewById<Button>(R.id.btn_signup)

        btn_signup.setOnClickListener{
            val name = et_name.text.toString().trim()
            val email = et_email.text.toString().trim()
            val pass = et_pass.text.toString().trim()
            val cpass = et_cpass.text.toString().trim()
            if (name.isEmpty() || email.isEmpty() || pass.isEmpty() || cpass.isEmpty()){
                Toast.makeText(applicationContext,"all fields are required",Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }
            if (!pass.equals(cpass)){
                Toast.makeText(applicationContext,"Password Do Not Match",Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }
            val intent = Intent(applicationContext,MainActivity::class.java)
            startActivity(intent)


        }
        tv_login.setOnClickListener{
            val intent = Intent(applicationContext,LoginActivity::class.java)
            startActivity(intent)
        }
    }
}