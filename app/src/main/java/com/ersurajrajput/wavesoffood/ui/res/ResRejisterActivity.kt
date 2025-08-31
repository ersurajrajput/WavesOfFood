package com.ersurajrajput.wavesoffood.ui.res

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.ersurajrajput.wavesoffood.R
import com.ersurajrajput.wavesoffood.databinding.ActivityResRejisterBinding
import com.ersurajrajput.wavesoffood.models.ResModel
import com.google.firebase.Firebase
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.auth
import com.google.firebase.database.FirebaseDatabase

class ResRejisterActivity : AppCompatActivity() {
    private lateinit var binding: ActivityResRejisterBinding
    private lateinit var resModel: ResModel
    private lateinit var auth: FirebaseAuth
    private lateinit var database: FirebaseDatabase

//    private lateinit var
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        binding = ActivityResRejisterBinding.inflate(layoutInflater)
        setContentView(binding.root)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        //initialization
        auth = Firebase.auth
        database = FirebaseDatabase.getInstance()
        binding.btnRegister.setOnClickListener {
            val resName = binding.etResName.text.toString().trim()
            val resEmail = binding.etEmail.text.toString().trim()
            val resPass = binding.etPass.text.toString().trim()
            val userName = binding.etUserName.text.toString().trim()
            if (resName.isEmpty() || resEmail.isEmpty() || resPass.isEmpty() || userName.isEmpty()){
                Toast.makeText(this, "Please fill all the fields", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }
            resModel = ResModel("0", resName, resPass, userName, resEmail);
            rejister(resEmail,resPass)

        }


    }
    fun rejister(resEmail: String,resPass: String){
        auth.createUserWithEmailAndPassword(resEmail,resPass).addOnCompleteListener { task ->
            if (task.isSuccessful){
                Toast.makeText(this,"Rejister Successfully", Toast.LENGTH_SHORT).show()
                var userId = FirebaseAuth.getInstance().currentUser!!.uid
                var myRes = database.getReference("res")
                myRes.child(userId).setValue(resModel)
                startActivity(Intent(this, ResMainActivity::class.java))
                finish()
            }else{
                AlertDialog.Builder(this).setTitle("Error").setMessage(task.exception?.message).setPositiveButton("Ok"){ dialog, which ->
                    dialog.dismiss()
                }.show()
                Toast.makeText(this,task.exception?.message, Toast.LENGTH_SHORT).show()
                Log.d("TAG",task.exception.toString())

            }
        }
    }
}