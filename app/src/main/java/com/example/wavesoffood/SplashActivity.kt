package com.example.wavesoffood

import android.Manifest
import android.content.Intent
import android.content.pm.PackageManager
import android.location.Address
import android.location.Geocoder
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.widget.ImageView
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import androidx.core.os.postDelayed
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.bumptech.glide.Glide
import com.cloudinary.android.MediaManager
import com.example.wavesoffood.Helpers.LoginChakerHelper
import com.example.wavesoffood.Models.FoodItemModel
import com.example.wavesoffood.Models.IngredientsModel
import com.example.wavesoffood.Models.OrderModel
import com.example.wavesoffood.ui.res.ResHomeActivity
import com.example.wavesoffood.ui.users.UserHomeActivity
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationServices
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.MapView
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.firebase.BuildConfig
import com.google.firebase.Firebase
import com.google.firebase.FirebaseApp
import com.google.firebase.database.database
import java.io.IOException
import java.util.Locale


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




//
//        initConfig()
//        handler.postDelayed({
//            var loginChakerHelper = LoginChakerHelper(applicationContext)
//            loginChakerHelper.loginRouteHelper()
//
//        }, 10000000)
//
    }
//
    private fun initConfig() {
        var config = HashMap<String, String>()
        config.put("cloud_name", com.example.wavesoffood.BuildConfig.cloudinary_cloud_name);
        config.put("api_key", com.example.wavesoffood.BuildConfig.cloudinary_api_key)
        config.put("api_secret", com.example.wavesoffood.BuildConfig.cloudinary_api_secret)
//        config.put("secure", true);
        MediaManager.init(this, config);
    }
}

