package com.example.bolinwang.tudar;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
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
    private ArrayAdapter<CharSequence> adapter;
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
        spinnerFreq = (Spinner) findViewById(R.id.SpinnerFreq);

        //spinner adapter
        adapter = ArrayAdapter.createFromResource(this, R.array.FrequencyArray, android.R.layout.simple_spinner_item);//
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerFreq.setAdapter(adapter);








        
        btnFinish.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //upload spinnerFreq to firebase
               // String.valueOf(spinnerFreq.getSelectedItem());


                Intent intent = new Intent(SignUpInfo.this, SignUpInfo.class);
                startActivity(intent);
                finish();
            }
        });

    }

}

