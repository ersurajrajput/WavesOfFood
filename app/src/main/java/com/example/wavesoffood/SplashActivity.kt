package com.example.wavesoffood

import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.widget.ImageView
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.os.postDelayed
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.bumptech.glide.Glide
import com.example.wavesoffood.Models.FoodItemModel
import com.example.wavesoffood.Models.IngredientsModel
import com.example.wavesoffood.ui.res.ResHomeActivity
import com.example.wavesoffood.ui.users.UserHomeActivity
import com.google.firebase.BuildConfig
import com.google.firebase.Firebase
import com.google.firebase.FirebaseApp
import com.google.firebase.database.database


class SplashActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_splash)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
        var handler = Handler(Looper.getMainLooper())
        FirebaseApp.initializeApp(applicationContext)
        val sharedPreferences = getSharedPreferences("wavesoffood", MODE_PRIVATE)

//        var db = Firebase.database(com.example.wavesoffood.BuildConfig.FIREBASE_DB_URL)
//        var dbTestRef = db.getReference("foodItem")
//        var ingredientsModel = IngredientsModel("i1","name","img")
//        var foodItemModel = FoodItemModel("f1","name","lunch",200,"sample","res1")
//
//        var kid = dbTestRef.push().key.toString()
//        dbTestRef.child(kid).setValue(foodItemModel)

       


        handler.postDelayed({
            if (sharedPreferences.getBoolean("isLoggedIn",false)){
                if (sharedPreferences.getString("uType",null).equals("res")){
                    var intent = Intent(applicationContext, ResHomeActivity::class.java)
                    startActivity(intent)
                    finish()
                }
                else if (sharedPreferences.getString("uType",null).equals("user")){
                    var intent = Intent(applicationContext, UserHomeActivity::class.java)
                    startActivity(intent)
                    finish()
                }
            }else{
                var intent = Intent(applicationContext, OnBoardingActivity::class.java)
                startActivity(intent)
                finish()
            }


        },2000)

    }
}

