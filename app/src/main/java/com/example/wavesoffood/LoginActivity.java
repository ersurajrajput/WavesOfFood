package com.example.wavesoffood;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class LoginActivity extends AppCompatActivity {
    EditText email,pass;
    TextView tv_newuser;
    Button btn_google,btn_facebook,btn_login;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_login);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
        email = findViewById(R.id.et_email);
        pass = findViewById(R.id.et_pass);
        btn_facebook = findViewById(R.id.btn_facebook);
        btn_google = findViewById(R.id.btn_google);
        btn_login = findViewById(R.id.btn_Login);
        tv_newuser = findViewById(R.id.tv_newuser);

        btn_login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (email.getText().toString().trim().isEmpty()){

                    email.setBackground(ContextCompat.getDrawable(LoginActivity.this, R.drawable.error_et_bg));

                    return ;
                }
                if (pass.getText().toString().trim().isEmpty()){

                    pass.setBackground(ContextCompat.getDrawable(LoginActivity.this, R.drawable.error_et_bg));

                    return ;
                }

                Intent i = new Intent(LoginActivity.this, MainActivity.class);
                startActivity(i);

            }
        });
        tv_newuser.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(LoginActivity.this, SignupActivity.class);
                startActivity(i);
            }
        });


    }
}