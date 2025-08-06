package com.example.wavesoffood.ui.users

import android.content.SharedPreferences
import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.wavesoffood.R
import com.example.wavesoffood.databinding.ActivityUserHomeBinding

class UserHomeActivity : AppCompatActivity() {
    private lateinit var binding: ActivityUserHomeBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()

        binding = ActivityUserHomeBinding.inflate(layoutInflater)
        setContentView(binding.root)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
        val sharedPreferences = getSharedPreferences("wavesoffood", MODE_PRIVATE)
        binding.tvUserName.text = sharedPreferences.getString("userName","User")
    }
}