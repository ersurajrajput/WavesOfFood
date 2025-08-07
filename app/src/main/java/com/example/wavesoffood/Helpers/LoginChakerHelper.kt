package com.example.wavesoffood.Helpers

import android.content.Context
import android.content.Context.MODE_PRIVATE
import android.content.Intent
import com.example.wavesoffood.OnBoardingActivity
import com.example.wavesoffood.SplashActivity
import com.example.wavesoffood.ui.res.ResHomeActivity
import com.example.wavesoffood.ui.users.UserHomeActivity


class LoginChakerHelper(var context: Context) {
    var sharedPreferences =context.getSharedPreferences("wavesoffood",MODE_PRIVATE)
    fun isLoggedIn(): Boolean{return  sharedPreferences.getBoolean("isLoggedIn",false)}
    fun loginChake(){
        if (!sharedPreferences.getBoolean("isLoggedIn",false)){
            var intent = Intent(context, SplashActivity::class.java)
            intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
            context.startActivity(intent)
        }
    }
    fun loginRouteHelper(){
        if (sharedPreferences.getBoolean("isLoggedIn", false)) {
            when (sharedPreferences.getString("uType", null)) {
                "user" -> {
                    val intent = Intent(context, UserHomeActivity::class.java)
                    intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
                    context.startActivity(intent)
                    return
                }
                "res" -> {
                    val intent = Intent(context, ResHomeActivity::class.java)
                    intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
                    context.startActivity(intent)
                    return
                }
            }

            // If uType is null or something else, go to onboarding

        }else{
            val intent = Intent(context, OnBoardingActivity::class.java)
            intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
            context.startActivity(intent)
        }
    }

}



