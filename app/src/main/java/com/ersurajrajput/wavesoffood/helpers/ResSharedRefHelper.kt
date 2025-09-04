package com.ersurajrajput.wavesoffood.helpers

import android.content.Context
import com.ersurajrajput.wavesoffood.models.ResModel

class ResSharedRefHelper(var context: Context) {
    var sharedPreferences = context.getSharedPreferences("WavesOfFood", Context.MODE_PRIVATE)
    public fun saveRes(resModel: ResModel){
        val editor = sharedPreferences.edit()
        editor.putString("resId",resModel.resID)
        editor.putString("resName",resModel.resName)
        editor.putString("resEmail",resModel.resEmail)
        editor.putString("resPhone",resModel.resMobile)
        editor.putString("resAddress",resModel.resAddress)
        editor.putString("resImg",resModel.resImg)
        editor.putBoolean("IsLoggedIn",true)
        editor.putString("type","res")
        editor.apply()

    }
    public fun clearRes(){
        val editor = sharedPreferences.edit()
        editor.clear()
        editor.apply()
    }
    public fun getResName(): String{
        return sharedPreferences.getString("resName","")!!

    }
    public fun getResEmail(): String{
        return sharedPreferences.getString("resEmail","")!!


    }
    public fun getResPhone(): String{
        return sharedPreferences.getString("resPhone","")!!
    }
    public fun getResAddress(): String{
        return sharedPreferences.getString("resAddress","")!!
    }
    public fun getResImg(): String{
        return sharedPreferences.getString("resImg","")!!

    }
    public fun getResToken(): String{
        return sharedPreferences.getString("resToken","")!!
    }
    public fun isLoggedIn(): Boolean{
        return sharedPreferences.getBoolean("IsLoggedIn",false)
    }
    public fun getType(): String{
        return sharedPreferences.getString("type","")!!
    }
    public fun getResId(): String{
        return sharedPreferences.getString("resId","")!!
    }

}