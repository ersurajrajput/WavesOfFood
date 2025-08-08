package com.example.wavesoffood.ui.res

import android.content.Intent
import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.wavesoffood.R
import com.example.wavesoffood.databinding.ActivityResHomeBinding
import com.example.wavesoffood.databinding.ActivityResSignUpBinding
import com.example.wavesoffood.ui.res.Fragments.ResFoodListFragment
import com.example.wavesoffood.ui.res.Fragments.ResHomeFragment

class ResHomeActivity : AppCompatActivity() {
    private lateinit var binding: ActivityResHomeBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        binding = ActivityResHomeBinding.inflate(layoutInflater)
        setContentView(binding.root)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
        supportFragmentManager.beginTransaction()
            .replace(binding.fagmentContainer.id, ResHomeFragment()).commit()





        binding.resBottomNav.setOnItemSelectedListener { menuItem ->
            when (menuItem.itemId) {
                R.id.menu_home -> {
                    // Handle home click
                    true
                }

                R.id.menu_list -> {
                    // Handle orders click
                    supportFragmentManager.beginTransaction().replace(
                        binding.fagmentContainer.id, ResFoodListFragment()
                    ).commit()

                    true
                }

                R.id.menu_add -> {
                    var intent = Intent(applicationContext, AddNewFoodItemActivity::class.java)
                    startActivity(intent)
                    true
                }

                R.id.menu_notification -> {
                    // Handle profile click
                    true
                }

                R.id.menu_user -> {
                    var intent = Intent(applicationContext, ResProfileActivity::class.java)
                    startActivity(intent)
                    true
                }

                else -> false
            }
        }


    }
}