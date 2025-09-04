package com.ersurajrajput.wavesoffood.ui.res

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.util.Patterns
import android.view.View
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.activity.result.contract.ActivityResultContracts
import androidx.activity.result.registerForActivityResult
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.ersurajrajput.wavesoffood.R
import com.ersurajrajput.wavesoffood.ui.res.ResRejisterActivity
import com.ersurajrajput.wavesoffood.databinding.ActivityResLoginBinding
import com.ersurajrajput.wavesoffood.helpers.LoginHelper
import com.ersurajrajput.wavesoffood.models.ResModel
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInClient
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.google.firebase.auth.AuthCredential
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.GoogleAuthProvider
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener

class ResLoginActivity : AppCompatActivity() {
    private lateinit var binding: ActivityResLoginBinding
    private lateinit var auth: FirebaseAuth
    private lateinit var database: FirebaseDatabase
    private lateinit var googleSignInClient: GoogleSignInClient


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
        database = FirebaseDatabase.getInstance()

        binding.btnGoogle.setOnClickListener {
            var googleSignInOptions =
                GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN).requestIdToken(
                    getString(R.string.default_web_client_id)
                ).requestEmail().build()
            googleSignInClient = GoogleSignIn.getClient(this, googleSignInOptions)

            GoogleLogin()
        }

        binding.tvNewUser.setOnClickListener {
            startActivity(Intent(this, ResRejisterActivity::class.java))
        }

        binding.btnLogin.setOnClickListener {
            if (!inputChacker()){
                return@setOnClickListener
            }
            emailPassRegister(binding.etEmail.text.toString(),binding.etPass.text.toString())
        }

    }

    private fun GoogleLogin() {
        var signinIntent = googleSignInClient.signInIntent
        googleSignInClient.signOut()
        launcher.launch(signinIntent)

    }

    private val launcher =
        registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { result ->
            if (result.resultCode == RESULT_OK) {
                val task = GoogleSignIn.getSignedInAccountFromIntent(result.data)
                if (task.isSuccessful) {
                    val account = task.result
                    val credential = GoogleAuthProvider.getCredential(account.idToken,null)
                    val email = account.email
                    auth.signInWithCredential(credential).addOnCompleteListener { task2->
                        if (task2.isSuccessful){
                            val result = task2.result
                            var isNewUser = result.additionalUserInfo?.isNewUser?:false
                            if (isNewUser){
                                showError("this Email is not registered")
                                auth.signOut()
                                googleSignInClient.signOut()
                            } else{
                                var sharedPreferences = getSharedPreferences("WavesOfFood", Context.MODE_PRIVATE)
                                var editor = sharedPreferences.edit()
                                editor.putString("type","res")
                                editor.apply()
                                updateUI()
                            }
                        }
                    }
                }

            }
        }


    private fun inputChacker(): Boolean {
        var resEmail = binding.etEmail.text.toString().trim()
        var resPass = binding.etPass.text.toString().trim()



        if (resEmail.isEmpty() || resPass.isEmpty()  ) {
            showError("All Fileds Are Required")
            return false
        }
        if (!Patterns.EMAIL_ADDRESS.matcher(resEmail).matches()) {
            showError("Enter A Valid Email Address")
            return false
        }
        if (resPass.length < 6) {
            showError("Password Must Be 6 Character Long")
            return false
        }

        return true

    }

    private fun emailPassRegister(email: String,pass: String) {

        auth.signInWithEmailAndPassword(email,pass)
            .addOnCompleteListener { task ->
                if (task.isSuccessful) {
                    var sharedPreferences = getSharedPreferences("WavesOfFood", Context.MODE_PRIVATE)
                    var editor = sharedPreferences.edit()
                    editor.putString("type","res")
                    editor.apply()
                    updateUI()
                    Toast.makeText(this@ResLoginActivity,"Succes",Toast.LENGTH_SHORT).show()


                } else {
                    showError(task.exception?.message.toString())
                }
            }
    }


    private fun updateUI() {
        var intent = Intent(this@ResLoginActivity, ResMainActivity::class.java)
        startActivity(intent)
        finish()
    }
    private fun showError(error: String) {
        AlertDialog.Builder(this@ResLoginActivity).setMessage(error)
            .setPositiveButton("Retry") { dialog, which ->
                dialog.dismiss()
            }.show()
    }

}
