package com.example.wavesoffood.Helpers

import android.content.Context.MODE_PRIVATE
import android.content.Context
import android.content.Intent
import androidx.core.content.ContextCompat.startActivity
import com.example.wavesoffood.Models.ResModel
import com.example.wavesoffood.Models.Usermodel
import com.example.wavesoffood.SplashActivity
import com.example.wavesoffood.ui.res.ResHomeActivity
import com.example.wavesoffood.ui.users.UserHomeActivity

class SharedPrefHelper(var context : Context) {
    var sharedPreferences =context.getSharedPreferences("wavesoffood",MODE_PRIVATE)
    var editor = sharedPreferences.edit()

    fun delete(){


        editor.clear()
        editor.apply()
        var intent = Intent(context, SplashActivity::class.java)
        intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
        context.startActivity(intent)

    }
    fun saveUser(usermodel: Usermodel){
        editor.putBoolean("isLoggedIn",true)
        editor.putString("userName",usermodel.userName)
        editor.putString("userID",usermodel.id)
        editor.putString("userEmail",usermodel.userEmail)
        editor.putString("userImg",usermodel.userImg)
        editor.putString("uType","user")
        editor.apply()
        var intent = Intent(context, UserHomeActivity::class.java)
        intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
        context.startActivity(intent)
    }
    fun saveRes(resModel: ResModel){
        editor.putBoolean("isLoggedIn",true)
        editor.putString("resName",resModel.resName)
        editor.putString("resID",resModel.id)
        editor.putString("resEmail",resModel.resName)
        editor.putString("resImg",resModel.resName)
        editor.putString("uType","res")
        editor.apply()
        var intent = Intent(context, ResHomeActivity::class.java)
        intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
        context.startActivity(intent)
    }
    //user
    fun getUserName(): String{
        return sharedPreferences.getString("userName","userName").toString()
    }
    fun getUserEmail(): String{
        return sharedPreferences.getString("userEmail","userEmail").toString()
    }
    fun getUserId(): String{
        return sharedPreferences.getString("userID","userID").toString()

    }
    fun getUserImg(): String{
        return sharedPreferences.getString("userImg","userImg").toString()
    }
    fun getType(): String{
        return sharedPreferences.getString("uType","uType").toString()
    }


    //res
    fun getResName(): String{
        return sharedPreferences.getString("resName","resName").toString()
    }
    fun getResEmail(): String{
        return sharedPreferences.getString("resEmail","resEmail").toString()
    }
    fun getResId(): String{
        return sharedPreferences.getString("resID","resID").toString()

    }
    fun getResImg(): String{
        return sharedPreferences.getString("resImg","resImg").toString()
    }



}