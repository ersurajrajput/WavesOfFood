package com.ersurajrajput.wavesoffood.helpers

import android.content.Context
import com.ersurajrajput.wavesoffood.models.UserModel

class UserSharedRefHelper(var context: Context) {
    var sharedPreferences = context.getSharedPreferences("WavesOfFood", Context.MODE_PRIVATE)
    public fun saveuser(userModel: UserModel){
        val editor = sharedPreferences.edit()
        editor.putString("userName",userModel.userName)
        editor.putString("userEmail",userModel.userEmail)
        editor.putString("userPhone",userModel.userMobile)
        editor.putString("userAddress",userModel.userAddress)
        editor.putString("userImg",userModel.userImg)
        editor.putString("type","user")
        editor.putBoolean("IsLoggedIn",true)
        editor.apply()

    }
    public fun clearUser(){
        val editor = sharedPreferences.edit()
        editor.clear()
        editor.apply()
    }
    public fun getUserName(): String{
        return sharedPreferences.getString("userName","")!!

    }
    public fun getUserEmail(): String{
        return sharedPreferences.getString("userEmail","")!!


    }
    public fun getUserPhone(): String{
        return sharedPreferences.getString("userPhone","")!!
    }
    public fun getUserAddress(): String{
        return sharedPreferences.getString("userAddusers","")!!
    }
    public fun getUserImg(): String{
        return sharedPreferences.getString("userImg","")!!

    }
    public fun getUserToken(): String{
        return sharedPreferences.getString("userToken","")!!
    }
    public fun isLoggedIn(): Boolean{
        return sharedPreferences.getBoolean("IsLoggedIn",false)
    }
    public fun getType(): String{
        return sharedPreferences.getString("type","")!!
    }
}