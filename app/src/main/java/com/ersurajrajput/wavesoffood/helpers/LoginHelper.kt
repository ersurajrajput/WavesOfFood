package com.ersurajrajput.wavesoffood.helpers

import android.content.Context
import android.content.Intent
import com.ersurajrajput.wavesoffood.ui.comman.OnBoradingActivity
import com.ersurajrajput.wavesoffood.ui.res.ResMainActivity
import com.ersurajrajput.wavesoffood.ui.user.MainActivity

class LoginHelper(var context: Context) {
    var sharedPreferences = context.getSharedPreferences("WavesOfFood", Context.MODE_PRIVATE)
    var type: String = sharedPreferences.getString("type","")!!
    var loggedIn: Boolean = sharedPreferences.getBoolean("IsLoggedIn",false)
    public fun LoggedIn(){
        if (!loggedIn){
            var intent = Intent(context, OnBoradingActivity::class.java)
            intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
            context.startActivity(intent)

        }
    }
    public fun RouteHelper(){
        var intent: Intent
        if (loggedIn){

        if (type=="user"){
            intent = Intent(context, MainActivity::class.java)

        }else{
            intent = Intent(context, ResMainActivity::class.java)
        }
        }
        else{
            intent = Intent(context, OnBoradingActivity::class.java)
        }
        intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
        context.startActivity(intent)



    }
}