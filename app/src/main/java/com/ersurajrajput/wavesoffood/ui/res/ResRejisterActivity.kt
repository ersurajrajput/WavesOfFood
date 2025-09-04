package com.ersurajrajput.wavesoffood.ui.res

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.ersurajrajput.wavesoffood.R
import com.ersurajrajput.wavesoffood.databinding.ActivityResRejisterBinding
import com.ersurajrajput.wavesoffood.helpers.ResSharedRefHelper
import com.ersurajrajput.wavesoffood.models.ResModel
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInClient
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.google.firebase.Firebase
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.GoogleAuthProvider
import com.google.firebase.auth.auth
import com.google.firebase.database.FirebaseDatabase

class ResRejisterActivity : AppCompatActivity() {
    private lateinit var binding: ActivityResRejisterBinding
    private lateinit var resModel: ResModel
    private lateinit var auth: FirebaseAuth
    private lateinit var database: FirebaseDatabase
    private lateinit var sharedRefHelper: ResSharedRefHelper
    private lateinit var googleSignInClient: GoogleSignInClient


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
        var googleSignInOptions = GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN).requestIdToken(
            getString(R.string.default_web_client_id)).requestEmail().build()

        //initialization
        auth = Firebase.auth
        database = FirebaseDatabase.getInstance()
        sharedRefHelper = ResSharedRefHelper(this)
        googleSignInClient = GoogleSignIn.getClient(this, googleSignInOptions)


        binding.btnGoogle.setOnClickListener {
            var signinIntent = googleSignInClient.signInIntent
            launcher.launch(signinIntent)
        }
        binding.btnRegister.setOnClickListener {
            val resName = binding.etResName.text.toString().trim()
            val resEmail = binding.etEmail.text.toString().trim()
            val resPass = binding.etPass.text.toString().trim()
            val userName = binding.etUserName.text.toString().trim()
            if (resName.isEmpty() || resEmail.isEmpty() || resPass.isEmpty() || userName.isEmpty()){
                Toast.makeText(this, "Please fill all the fields", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }
            binding.mainContainer.visibility = View.GONE
            binding.animationView.visibility = View.VISIBLE
            resModel = ResModel("0", resName, resPass, userName, resEmail, resRating = 0.0, resBalance = 0.0,);
            rejister(resEmail,resPass)

        }


    }
    private fun rejister(resEmail: String,resPass: String){
        auth.createUserWithEmailAndPassword(resEmail,resPass).addOnCompleteListener { task ->
            if (task.isSuccessful){
                Toast.makeText(this,"Rejister Successfully", Toast.LENGTH_SHORT).show()
                var userId = FirebaseAuth.getInstance().currentUser!!.uid
                var myRes = database.getReference("res")
                resModel.resID = userId
                //save user data in firebase
                myRes.child(userId).setValue(resModel)
                sharedRefHelper.saveRes(resModel)
                startActivity(Intent(this, ResMainActivity::class.java))
                finish()
            }else{
                AlertDialog.Builder(this).setTitle("Error").setMessage(task.exception?.message).setPositiveButton("Ok"){ dialog, which ->
                    dialog.dismiss()
                }.show()
                Toast.makeText(this,task.exception?.message, Toast.LENGTH_SHORT).show()
                Log.d("TAG",task.exception.toString())
                binding.mainContainer.visibility = View.VISIBLE
                binding.animationView.visibility = View.GONE

            }
        }
    }
    private val launcher = registerForActivityResult(ActivityResultContracts.StartActivityForResult()){
        result ->
        if (result.resultCode == RESULT_OK){
            val task = GoogleSignIn.getSignedInAccountFromIntent(result.data)
            if (task.isSuccessful){
                val account = task.result
                val credential = GoogleAuthProvider.getCredential(account.idToken,null)
                auth.signInWithCredential(credential).addOnCompleteListener { task ->
                    if (task.isSuccessful){
                        Toast.makeText(this@ResRejisterActivity,"Rejister Successfully", Toast.LENGTH_SHORT).show()
                        Log.d("myTag",account.toString())
                        Log.d("myTag",account.email.toString())
                        Log.d("myTag",account.displayName.toString())
                        Log.d("myTag",account.photoUrl.toString())
                        Log.d("myTag",account.familyName.toString())
                        Log.d("myTag",account.givenName.toString())








                    }else{
                        Toast.makeText(this@ResRejisterActivity,task.exception?.message, Toast.LENGTH_SHORT).show()
                    }
                }
            }else{
                Toast.makeText(this@ResRejisterActivity,task.exception?.message, Toast.LENGTH_SHORT).show()
            }

            }
    }
}