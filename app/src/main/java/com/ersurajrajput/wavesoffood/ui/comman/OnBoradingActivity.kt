package com.ersurajrajput.wavesoffood.ui.comman

import android.content.Intent
import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.ersurajrajput.wavesoffood.ui.user.LoginActivity
import com.ersurajrajput.wavesoffood.R
import com.ersurajrajput.wavesoffood.databinding.ActivityOnBoradingBinding
import com.ersurajrajput.wavesoffood.ui.res.ResLoginActivity

class OnBoradingActivity : AppCompatActivity() {
    private lateinit var binding: ActivityOnBoradingBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        binding = ActivityOnBoradingBinding.inflate(layoutInflater)
        setContentView(binding.root)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }


         binding.btnUser.setOnClickListener {
             startActivity(Intent(this, LoginActivity::class.java))
             finish()
         }
        binding.btnRestorent.setOnClickListener {
            startActivity(Intent(this, ResLoginActivity::class.java))
            finish()
        }
    }
}