package com.ersurajrajput.wavesoffood.ui.res

import android.content.Intent
import android.os.Bundle
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

class ResLoginActivity : AppCompatActivity() {
    private lateinit var binding: ActivityResLoginBinding
    private lateinit var resRefHelper: ResSharedRefHelper
    private lateinit var loginHelper: LoginHelper

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
        loginHelper = LoginHelper(this)
        resRefHelper = ResSharedRefHelper(this)


        binding.tvNewUser.setOnClickListener {
            startActivity(Intent(this, ResRejisterActivity::class.java))
            finish()
        }
        binding.btnLogin.setOnClickListener {
            var resModel = ResModel(resName = "ll")
            resRefHelper.saveRes(resModel)
            startActivity(Intent(this, ResMainActivity::class.java))
            finish()
        }
    }
}