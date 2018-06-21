package com.example.bolinwang.tudar;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

//added import
import android.support.annotation.NonNull;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import android.content.Intent;
import android.text.TextUtils;

public class SignUpInfo extends AppCompatActivity{
    private EditText plaintextAge;
    private EditText plaintextHeight;
    private EditText plaintextName;
    private EditText plaintextWeight;
    private Button btnFinish;
    private CheckBox checkBoxMale;
    private CheckBox checkBoxFemale;
    private Spinner spinnerFreq;
    private FirebaseAuth auth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup_info);
        auth = FirebaseAuth.getInstance();

        plaintextAge = (EditText) findViewById(R.id.PlainTextAge);
        plaintextHeight = (EditText) findViewById(R.id.PlainTextHeight);
        plaintextName = (EditText) findViewById(R.id.PlainTextName);
        plaintextHeight = (EditText) findViewById(R.id.PlainTextHeight);
        btnFinish = (Button) findViewById(R.id.BtnFinish);
        checkBoxMale = (CheckBox) findViewById(R.id.CheckBoxMale);
        checkBoxFemale = (CheckBox) findViewById(R.id.CheckBoxFemale);
        //spinner not define





        
        btnFinish.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(SignUpInfo.this, SignUpInfo.class);
                startActivity(intent);
                finish();
            }
        });

    }
}
