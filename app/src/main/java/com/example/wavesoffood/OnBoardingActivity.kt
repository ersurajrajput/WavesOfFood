package com.example.wavesoffood

import android.content.Intent
import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.wavesoffood.databinding.ActivityOnBoardingBinding
import com.example.wavesoffood.ui.res.ResLoginActivity
import com.example.wavesoffood.ui.users.UserLoginActivity

class OnBoardingActivity : AppCompatActivity() {
    private lateinit var binding: ActivityOnBoardingBinding

    override fun onCreate(savedInstanceState: Bundle?) {

        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        binding = ActivityOnBoardingBinding.inflate(layoutInflater)
        setContentView(binding.root)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
        binding.btnUser.setOnClickListener {
            var intent = Intent(applicationContext, UserLoginActivity::class.java)
            startActivity(intent)
            finish()
        }
        binding.btnRes.setOnClickListener {
            var intent = Intent(applicationContext, ResLoginActivity::class.java)
            startActivity(intent)
            finish()
        }
    }
}