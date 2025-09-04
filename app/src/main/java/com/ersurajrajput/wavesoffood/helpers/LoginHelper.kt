package com.ersurajrajput.wavesoffood.helpers

import android.content.Context
import android.content.Intent
import com.ersurajrajput.wavesoffood.ui.comman.OnBoradingActivity
import com.ersurajrajput.wavesoffood.ui.res.ResMainActivity
import com.ersurajrajput.wavesoffood.ui.user.MainActivity
import com.google.firebase.auth.FirebaseAuth

class LoginHelper(var context: Context) {
     private lateinit var auth: FirebaseAuth
    var sharedPreferences = context.getSharedPreferences("WavesOfFood", Context.MODE_PRIVATE)

    public fun LoggedIn(){
        auth = FirebaseAuth.getInstance()
        if (auth.currentUser == null){
            var intent = Intent(context, OnBoradingActivity::class.java)
            intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
            context.startActivity(intent)

        }
    }
    public fun RouteHelper(){
        var intent: Intent
        auth = FirebaseAuth.getInstance()

        if (auth.currentUser !=null){

        if (sharedPreferences.getString("type","")=="user"){
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