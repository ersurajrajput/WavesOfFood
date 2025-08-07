package com.example.wavesoffood.ui.res

import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.wavesoffood.Helpers.SharedPrefHelper
import com.example.wavesoffood.R
import com.example.wavesoffood.SplashActivity
import com.example.wavesoffood.databinding.ActivityResLoginBinding
import com.example.wavesoffood.databinding.ActivityResProfileBinding
import com.example.wavesoffood.databinding.ActivityResSignUpBinding

class ResProfileActivity : AppCompatActivity() {
    private lateinit var binding: ActivityResProfileBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        binding = ActivityResProfileBinding.inflate(layoutInflater)
        setContentView(binding.root)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
        var sharedPreferences = getSharedPreferences("wavesoffood",MODE_PRIVATE)
        binding.lrPersonalInfo.setOnClickListener {
            Toast.makeText(applicationContext,"coming soon", Toast.LENGTH_SHORT).show()

        }
        binding.rlSettings.setOnClickListener {
            Toast.makeText(applicationContext,"coming soon", Toast.LENGTH_SHORT).show()

        }
        binding.rlWithdrawalHistory.setOnClickListener {
            Toast.makeText(applicationContext,"coming soon", Toast.LENGTH_SHORT).show()

        }
        binding.rlNoOfOrders.setOnClickListener {
            Toast.makeText(applicationContext,"coming soon", Toast.LENGTH_SHORT).show()

        }
        binding.rlUserReviews.setOnClickListener {
            Toast.makeText(applicationContext,"coming soon", Toast.LENGTH_SHORT).show()

        }
        binding.rlLogOut.setOnClickListener {
            var sharedPrefHelper = SharedPrefHelper(applicationContext)
            sharedPrefHelper.delete()
            Toast.makeText(applicationContext,"Logged Out", Toast.LENGTH_SHORT).show()
        }



    }
}