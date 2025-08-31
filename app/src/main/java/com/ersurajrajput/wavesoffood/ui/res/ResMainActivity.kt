package com.ersurajrajput.wavesoffood.ui.res

import android.content.Intent
import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.ersurajrajput.wavesoffood.R
import com.ersurajrajput.wavesoffood.databinding.ActivityResMainBinding

class ResMainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityResMainBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        binding = ActivityResMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        binding.llAddFoodItem.setOnClickListener {
            startActivity(Intent(this, ResAddFoodItemActivity::class.java))
        }
        binding.llAllFoodItems.setOnClickListener {
            startActivity(Intent(this, ResAllFoodItemsActivity::class.java))

        }
        binding.llProfile.setOnClickListener {
            startActivity(Intent(this, ResProfileActivity::class.java))

        }



    }
}