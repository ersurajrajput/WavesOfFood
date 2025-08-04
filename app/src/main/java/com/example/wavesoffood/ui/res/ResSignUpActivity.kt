package com.example.wavesoffood.ui.res

import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.wavesoffood.R
import com.example.wavesoffood.databinding.ActivityResLoginBinding
import com.example.wavesoffood.databinding.ActivityResSignUpBinding

class ResSignUpActivity : AppCompatActivity() {
    private lateinit var binding: ActivityResSignUpBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        binding = ActivityResSignUpBinding.inflate(layoutInflater)
        setContentView(binding.root)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
        binding.tvLogin.setOnClickListener {
            var intent = Intent(applicationContext, ResLoginActivity::class.java)
            startActivity(intent)
        }
        binding.ivGoogle.setOnClickListener {
            Toast.makeText(applicationContext,"Comming Soong", Toast.LENGTH_SHORT).show()
        }
        binding.ivFacebook.setOnClickListener {
            Toast.makeText(applicationContext,"Comming Soong", Toast.LENGTH_SHORT).show()
        }
        binding.ivTwitter.setOnClickListener {
            Toast.makeText(applicationContext,"Comming Soong", Toast.LENGTH_SHORT).show()
        }
    }
}