package com.example.wavesoffood.user

import android.os.Bundle
import android.view.LayoutInflater
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.wavesoffood.R

class UserHomeActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_user_home)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
        /// catagory items
        val cat_container = findViewById<LinearLayout>(R.id.cat_container)
        val inflater = LayoutInflater.from(applicationContext)
        val categories = listOf("Pizza", "Burger", "Sushi", "Drinks", "Desserts")

        // restorents
        val restorent_conatiner = findViewById<LinearLayout>(R.id.restorents_conatiner)

        val restaurantNames = listOf(
            "Spice Villa",
            "Burger Hub",
            "Sushi World",
            "Café Bliss",
            "Tandoori Nights",
            "Pizza Planet",
        )
       val resMenu = listOf(
           "Burger - Chiken - Riche - Wings",
           "Pizza - Lassi - Rice - Wings",
           "Tea - coffie",
           "Burger - Chiken - Riche - Wings",
           "Pizza - Lassi - Rice - Wings",
           "Tea - coffie"

       )


        for (cat in  categories){
            val itemView = inflater.inflate(R.layout.cat_items,cat_container,false)
            val item_image = itemView.findViewById<ImageView>(R.id.iv_item_image)
            val item_name = itemView.findViewById<TextView>(R.id.tv_item_name)

            item_name.text = cat
            cat_container.addView(itemView)

        }
//        for (int i = 0){
//            val itemView = inflater.inflate(R.layout.restorent_items,restorent_conatiner,false)
//
//            val res_name = itemView.findViewById<TextView>(R.id.tv_restorentName)
//            val res_menu_items = itemView.findViewById<TextView>(R.id.tv_restorentMenu)
//
//            res_name.text = names
//            res_menu_items
//
//
//
//
//
//
//
//        }

    }
}