package com.example.bolinwang.tudar;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
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
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.UserInfo;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import com.google.firebase.database.ValueEventListener;

import android.content.Intent;
import android.text.TextUtils;
import java.util.HashMap;
import java.util.Map;

public class SignUpInfo extends AppCompatActivity{
    private EditText plaintextAge;
    private EditText plaintextHeight;
    private EditText plaintextName;
    private EditText plaintextWeight;
    Button btnFinish;
    private CheckBox checkBoxMale;
    private CheckBox checkBoxFemale;
    ArrayAdapter<CharSequence> adapter;
    FirebaseAuth auth;
    private DatabaseReference mDatabase;
    boolean isFemale;
    int freq;
    String uid;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup_info);
        auth = FirebaseAuth.getInstance();
        mDatabase = FirebaseDatabase.getInstance().getReference("User");  //initialize database reference
        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();

        if(user != null) {
            uid = user.getUid();
        }


        plaintextAge = (EditText) findViewById(R.id.PlainTextAge);
        plaintextHeight = (EditText) findViewById(R.id.PlainTextHeight);
        plaintextName = (EditText) findViewById(R.id.PlainTextName);
        plaintextWeight = (EditText) findViewById(R.id.PlainTextWeight);
        btnFinish = (Button) findViewById(R.id.BtnFinish);
        checkBoxMale = (CheckBox) findViewById(R.id.CheckBoxMale);
        checkBoxFemale = (CheckBox) findViewById(R.id.CheckBoxFemale);

        checkBoxMale.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onCheckboxClicked(view);
            }
        });
        checkBoxFemale.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onCheckboxClicked(view);
            }
        });

        spinnerFreq();
        btnFinish.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String plaintextAgeUpload = plaintextAge.getText().toString().trim();
                String plaintextHeightString = plaintextHeight.getText().toString().trim();
                String plaintextNameUpload = plaintextName.getText().toString().trim();
                String plaintextWeightString = plaintextWeight.getText().toString().trim();
                int plaintextHeightUpload = Integer.parseInt(plaintextHeightString);
                int plaintextWeightUpload = Integer.parseInt(plaintextWeightString);

                if (TextUtils.isEmpty(plaintextAgeUpload)) {
                    Toast.makeText(getApplicationContext(), "Enter Age!", Toast.LENGTH_SHORT).show();
                    return;
                } else  mDatabase.child("Student").child(uid).child("UserData").child("Age").setValue(plaintextAgeUpload);
                mDatabase.child("Student").child(uid).child("UserData").child("Frequency").setValue(freq);
                if (TextUtils.isEmpty(plaintextHeightString)) {
                    Toast.makeText(getApplicationContext(), "Enter Height!", Toast.LENGTH_SHORT).show();
                    return;
                } else mDatabase.child("Student").child(uid).child("UserData").child("Height").setValue(plaintextHeightUpload);
                if (TextUtils.isEmpty(plaintextNameUpload)) {
                    Toast.makeText(getApplicationContext(), "Enter Name!", Toast.LENGTH_SHORT).show();
                    return;
                } else mDatabase.child("Student").child(uid).child("UserData").child("Name").setValue(plaintextNameUpload);
                if (TextUtils.isEmpty(plaintextWeightString)) {
                    Toast.makeText(getApplicationContext(), "Enter Weight!", Toast.LENGTH_SHORT).show();
                    return;
                } else mDatabase.child("Student").child(uid).child("UserData").child("Weight").setValue(plaintextWeightUpload);
                if(!(checkBoxMale.isChecked())&&!(checkBoxFemale.isChecked())){
                    Toast.makeText(getApplicationContext(), "select gender!", Toast.LENGTH_SHORT).show();
                    return;
                } else mDatabase.child("Student").child(uid).child("UserData").child("isFemale").setValue(isFemale);






                Intent intent = new Intent(SignUpInfo.this, SignUpInfo.class);
                startActivity(intent);
                finish();
            }
        });

    }
    //checkboxes
    public void onCheckboxClicked(View view){
        boolean checked = ((CheckBox) view).isChecked();
        switch(view.getId()){
            case R.id.CheckBoxMale:
                if(checked) {
                    checkBoxFemale.setChecked(false);
                    isFemale = false;
                    break;
                }
            case R.id.CheckBoxFemale:
                if(checked) {
                    checkBoxMale.setChecked(false);
                    isFemale = true;
                    break;
                }
        }
    }
    public void spinnerFreq(){
        Spinner spinnerFreq = (Spinner) findViewById(R.id.SpinnerFreq);
        //spinner adapter
        adapter = ArrayAdapter.createFromResource(this, R.array.FrequencyArray, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerFreq.setAdapter(adapter);
        spinnerFreq.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int position, long id) {
                freq = position;
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {
                //do nothing
            }
        });
    }

}

