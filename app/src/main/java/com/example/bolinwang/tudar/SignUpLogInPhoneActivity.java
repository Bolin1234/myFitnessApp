package com.example.bolinwang.tudar;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

//added import
import android.support.annotation.NonNull;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import android.content.Intent;
import android.text.TextUtils;

public class SignUpLogInPhoneActivity extends AppCompatActivity {
    EditText phoneLogIn;
    EditText verCode;
    Button verCodeBtn;
    Button logInButton;
    FirebaseAuth auth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup_login);
        auth = FirebaseAuth.getInstance();

        phoneLogIn = (EditText) findViewById(R.id.PhoneSignUp);
        verCode = (EditText) findViewById(R.id.VerifyCode);
        verCodeBtn = (Button) findViewById(R.id.GetVerCodeBtn);
        logInButton = (Button) findViewById(R.id.LogInButton);

        logInButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(SignUpLogInPhoneActivity.this, MainActivity.class);
                startActivity(intent);
                finish();
            }
        });


    }
}