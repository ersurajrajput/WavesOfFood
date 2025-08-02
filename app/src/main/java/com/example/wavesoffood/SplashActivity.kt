package com.example.wavesoffood

import android.content.Intent
import android.os.Bundle
import com.example.wavesoffood.BuildConfig
import android.os.Handler
import android.os.Looper
import android.view.View
import android.widget.ImageView
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.os.BuildCompat
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.wavesoffood.ui.common.LoginActivity
import com.example.wavesoffood.ui.common.OnBoardingActivity
import com.example.wavesoffood.ui.restaurant.RestaurantHomeActivity
import com.example.wavesoffood.ui.user.UserHome

import com.google.firebase.Firebase
import com.google.firebase.FirebaseApp
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
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
        val sharedPreferences = getSharedPreferences("wavesoffood", MODE_PRIVATE)



//        FirebaseApp.initializeApp(applicationContext)
//        val db = FirebaseDatabase.getInstance()
//        val userDBRef = db.getReference("users")
//        val userData = mapOf(
//            "name" to "Suraj",
//            "email" to "suraj@example.com"
//        )
//
//        userDBRef.child("user1").setValue(userData)
//            .addOnSuccessListener {
//                Toast.makeText(this, "Data written successfully", Toast.LENGTH_SHORT).show()
//            }
//            .addOnFailureListener {
//                Toast.makeText(this, "Failed: ${it.message}", Toast.LENGTH_SHORT).show()
//            }
//
        Handler(Looper.getMainLooper()).postDelayed({
            // Fade-in animation for el1
            if (sharedPreferences.getBoolean("IsLoggedIn",false)){
                var userType = sharedPreferences.getString("user_type",null)
                if (userType!=null){
                    if (userType.equals("Restaurant")){
                        var intent = Intent(applicationContext, RestaurantHomeActivity::class.java)
                        startActivity(intent)
                        finish()
                    }else if (userType.equals("User")){
                        var intent = Intent(applicationContext, UserHome::class.java)
                        startActivity(intent)
                        finish()
                    }
                }

            }else{
                var intent = Intent(applicationContext, OnBoardingActivity::class.java)
                startActivity(intent)
                finish()
            }

        }, 2000)





    }

}