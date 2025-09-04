package com.ersurajrajput.wavesoffood.ui.res

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.util.Patterns
import android.view.View
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.ersurajrajput.wavesoffood.R
import com.ersurajrajput.wavesoffood.ui.res.ResRejisterActivity
import com.ersurajrajput.wavesoffood.databinding.ActivityResLoginBinding
import com.ersurajrajput.wavesoffood.helpers.LoginHelper
import com.ersurajrajput.wavesoffood.helpers.ResSharedRefHelper
import com.ersurajrajput.wavesoffood.helpers.UserSharedRefHelper
import com.ersurajrajput.wavesoffood.models.ResModel
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener

class ResLoginActivity : AppCompatActivity() {
    private lateinit var binding: ActivityResLoginBinding
    private lateinit var resRefHelper: ResSharedRefHelper
    private lateinit var loginHelper: LoginHelper
    private lateinit var auth: FirebaseAuth
    private lateinit var db: FirebaseDatabase


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        binding = ActivityResLoginBinding.inflate(layoutInflater)
        setContentView(binding.root)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        //init
        auth = FirebaseAuth.getInstance()
        loginHelper = LoginHelper(this)
        resRefHelper = ResSharedRefHelper(this)
        db = FirebaseDatabase.getInstance()
        var resDb = db.getReference("res")


        binding.tvNewUser.setOnClickListener {
            startActivity(Intent(this, ResRejisterActivity::class.java))
            finish()
        }
        binding.btnLogin.setOnClickListener {

            var resEmail = binding.etEmail.text.toString().trim()
            var resPassword = binding.etPass.text.toString().trim()
            if (!inputValid(email = resEmail, password = resPassword)){
                return@setOnClickListener
            }else{
                binding.mainContainer.visibility = View.GONE
                binding.animationView.visibility = View.VISIBLE
                binding.animationView.setAnimation(R.raw.hand_loding)
                binding.animationView.playAnimation()

                auth.signInWithEmailAndPassword(resEmail,resPassword).addOnCompleteListener { task ->
                    if (task.isSuccessful){
                         var uid = auth.currentUser?.uid
                        if (!uid.isNullOrEmpty()){
                            resDb.child(uid).addListenerForSingleValueEvent(object : ValueEventListener{
                                override fun onDataChange(snapshot: DataSnapshot) {
                                    if (snapshot.exists()){
                                       var resModel = snapshot.getValue(ResModel::class.java)
                                        if (resModel != null) {


                                            resRefHelper.saveRes(resModel)

                                            var intent = Intent(this@ResLoginActivity, ResMainActivity::class.java)
                                            startActivity(intent)
                                            finish()
                                        }
                                    }else{
                                        Toast.makeText(this@ResLoginActivity,"no user by this email", Toast.LENGTH_SHORT).show()

                                    }
                                }

                                override fun onCancelled(error: DatabaseError) {
                                    Toast.makeText(this@ResLoginActivity,error.message, Toast.LENGTH_SHORT).show()
                                }

                            })
                        }

                    }else{
                        binding.mainContainer.visibility = View.VISIBLE
                        binding.animationView.visibility = View.GONE
                        Toast.makeText(this,task.exception?.message, Toast.LENGTH_SHORT).show()
                    }
                }
            }


//            resRefHelper.saveRes(resModel)
//            startActivity(Intent(this, ResMainActivity::class.java))
//            finish()

        }
    }
    fun inputValid( email: String,  password: String): Boolean{
        if (email.isEmpty()){
            Toast.makeText(this,"all field are required", Toast.LENGTH_SHORT).show()
            return false
        }
        if (password.isEmpty()){
            Toast.makeText(this,"all field are required", Toast.LENGTH_SHORT).show()
            return false
        }
        if (!Patterns.EMAIL_ADDRESS.matcher(email).matches()){
            Toast.makeText(this,"Enter Valid Email", Toast.LENGTH_SHORT).show()
            return false
        }
        return true
    }
}
