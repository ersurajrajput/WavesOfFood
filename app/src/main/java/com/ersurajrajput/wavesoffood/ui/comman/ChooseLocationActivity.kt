package com.ersurajrajput.wavesoffood.ui.comman

import android.os.Bundle
import android.widget.ArrayAdapter
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.ersurajrajput.wavesoffood.R
import com.ersurajrajput.wavesoffood.databinding.ActivityChooseLocationBinding

class ChooseLocationActivity : AppCompatActivity() {
    private lateinit var binding: ActivityChooseLocationBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        binding = ActivityChooseLocationBinding.inflate(layoutInflater)
        setContentView(binding.root)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
        var city = ArrayList<String>()
        city.add("mathura")
        city.add("agra")
        city.add("noida")
        city.add("gurugram")


        val adapter = ArrayAdapter(this, android.R.layout.simple_list_item_1, city)

        binding.locationAutoComplete.setAdapter(adapter)
    }
}