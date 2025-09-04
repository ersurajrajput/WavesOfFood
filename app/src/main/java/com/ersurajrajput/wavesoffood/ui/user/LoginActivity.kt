package com.ersurajrajput.wavesoffood.ui.user

import android.content.Intent
import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.ersurajrajput.wavesoffood.R
import com.ersurajrajput.wavesoffood.ui.user.RegisterActivity
import com.ersurajrajput.wavesoffood.databinding.ActivityLoginBinding
import com.ersurajrajput.wavesoffood.models.UserModel

class LoginActivity : AppCompatActivity() {
    private lateinit var binding: ActivityLoginBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        binding = ActivityLoginBinding.inflate(layoutInflater)
        setContentView(binding.root)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        binding.tvNewUser.setOnClickListener {
            startActivity(Intent(this, RegisterActivity::class.java))
        }
        var userModeled = UserModel()


        binding.btnLogin.setOnClickListener {
            userModeled.userName = binding.etEmail.text.toString()

            startActivity(Intent(this, MainActivity::class.java))
            finish()
        }

    }
}