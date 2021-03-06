package com.example.bolinwang.tudar;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
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
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.ValueEventListener;


import android.content.Intent;
import android.text.TextUtils;




public class SignUpLogInActivity extends AppCompatActivity {
    private EditText emailSignUp;
    private EditText passwordSignUp;
    Button signUpButton;
    Button logInButton;
    ArrayAdapter<CharSequence> adapter;
    private FirebaseAuth auth;
    private DatabaseReference mDatabase;
    public boolean isStudent;
    String uid;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup_login);
        auth = FirebaseAuth.getInstance();
        mDatabase = FirebaseDatabase.getInstance().getReference("User");  //initialize database reference
        if(auth.getCurrentUser()!= null){       //if already logged in, then no need to login again
            mDatabase = FirebaseDatabase.getInstance().getReference("User");  //initialize database reference
            FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
            uid = user.getUid();
            //check if the uid exist in student or trainer. Based on whether it's a student or a trainer, we go to differnet pages.
            checkIdentity();              //note here we prevent auto login
        }

        emailSignUp =  findViewById(R.id.EmailSignUp);
        passwordSignUp = findViewById(R.id.PasswordSignUp);
        signUpButton = findViewById(R.id.SignUpButton);
        logInButton = findViewById(R.id.LogInButton);
        spinnerIsStudent();
        logInButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String emailLogin = emailSignUp.getText().toString();
                final String passwordLogin = passwordSignUp.getText().toString();
                if (TextUtils.isEmpty(emailLogin)) {
                    Toast.makeText(getApplicationContext(), "Enter email address!", Toast.LENGTH_SHORT).show();
                    return;
                    }
                    if (TextUtils.isEmpty(passwordLogin)) {
                    Toast.makeText(getApplicationContext(), "Enter password!", Toast.LENGTH_SHORT).show();
                    return;
                    }
                    //authenticate user
                auth.signInWithEmailAndPassword(emailLogin, passwordLogin)
                        .addOnCompleteListener(SignUpLogInActivity.this, new OnCompleteListener<AuthResult>() {
                            @Override
                            public void onComplete(@NonNull Task<AuthResult> task) {
                                // If sign in fails, display a message to the user. If sign in succeeds
                                // the auth state listener will be notified and logic to handle the
                                // signed in user can be handled in the listener
                                if (!task.isSuccessful()) {
                                    // there was an error
                                    if (passwordLogin.length() < 6) {
                                        passwordSignUp.setError(getString(R.string.minimum_password));
                                    } else {
                                        Toast.makeText(SignUpLogInActivity.this, getString(R.string.auth_failed), Toast.LENGTH_LONG).show();
                                    }
                                }
                                else {
                                    checkIdentity();
                                }
                            }
                });
            }
        });

        signUpButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final String emailSignUpUpload = emailSignUp.getText().toString().trim();
                final String passwordSignUpUpload = passwordSignUp.getText().toString().trim();

                if (TextUtils.isEmpty(emailSignUpUpload)) {
                    Toast.makeText(getApplicationContext(), "Enter email address!", Toast.LENGTH_SHORT).show();
                    return;
                }

                if (TextUtils.isEmpty(passwordSignUpUpload)) {
                    Toast.makeText(getApplicationContext(), "Enter password!", Toast.LENGTH_SHORT).show();
                    return;
                }

                if (passwordSignUpUpload.length() < 6) {
                    Toast.makeText(getApplicationContext(), "Password too short, enter minimum 6 characters!", Toast.LENGTH_SHORT).show();
                    return;
                }

                auth.createUserWithEmailAndPassword(emailSignUpUpload, passwordSignUpUpload)
                        .addOnCompleteListener(SignUpLogInActivity.this, new OnCompleteListener<AuthResult>() {
                            @Override
                            public void onComplete(@NonNull Task<AuthResult> task) {

                                // If sign in fails, display a message to the user. If sign in succeeds
                                // the auth state listener will be notified and logic to handle the
                                // signed in user can be handled in the listener.
                                Toast.makeText(SignUpLogInActivity.this, "createUserWithEmail:onComplete:" + task.isSuccessful(), Toast.LENGTH_SHORT).show();
                                if (!task.isSuccessful()) {
                                        Toast.makeText(SignUpLogInActivity.this, "Authentication failed." + task.getException(),
                                            Toast.LENGTH_SHORT).show();
                                } else {
                                    auth.signInWithEmailAndPassword(emailSignUpUpload, passwordSignUpUpload)
                                            .addOnCompleteListener(SignUpLogInActivity.this, new OnCompleteListener<AuthResult>() {
                                                @Override
                                                public void onComplete(@NonNull Task<AuthResult> task) {
                                                    // If sign in fails, display a message to the user. If sign in succeeds
                                                    // the auth state listener will be notified and logic to handle the
                                                    // signed in user can be handled in the listener
                                                    if (task.isSuccessful()) {
                                                        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
                                                        String uid = user.getUid();
                                                        if(isStudent){
                                                            mDatabase.child("Student").child(uid).child("LoginInfo").child("isStudent").setValue(true);
                                                            mDatabase.child("Student").child(uid).child("LoginInfo").child("contact").setValue(emailSignUpUpload);
                                                            mDatabase.child("Student").child(uid).child("LoginInfo").child("password").setValue(passwordSignUpUpload);
                                                            Intent intentSignUp = new Intent(SignUpLogInActivity.this, SignUpInfo.class);
                                                            startActivity(intentSignUp);
                                                        }
                                                        else {
                                                            mDatabase.child("Trainer").child(uid).child("LoginInfo").child("isStudent").setValue(false);
                                                            mDatabase.child("Trainer").child(uid).child("LoginInfo").child("contact").setValue(emailSignUpUpload);
                                                            mDatabase.child("Trainer").child(uid).child("LoginInfo").child("password").setValue(passwordSignUpUpload);
                                                            Intent intentTrainerCertify = new Intent(SignUpLogInActivity.this, TrainerCertify.class);
                                                            startActivity(intentTrainerCertify);
                                                        }
                                                        finish();
                                                    }
                                                    else{
                                                        if (passwordSignUpUpload.length() < 6) {
                                                            passwordSignUp.setError(getString(R.string.minimum_password));
                                                        } else {
                                                            Toast.makeText(SignUpLogInActivity.this, getString(R.string.auth_failed), Toast.LENGTH_LONG).show();
                                                        }
                                                    }
                                                }
                                            });
                                }
                            }
                        });

            }
        });
    }
    public void spinnerIsStudent(){
        final Spinner spinnerIsStudent = findViewById(R.id.SpinnerIsStudent);
        //spinner adapter
        adapter = ArrayAdapter.createFromResource(this, R.array.IsStudentArray, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerIsStudent.setAdapter(adapter);
        spinnerIsStudent.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int position, long id) {
                isStudent = true;
                if(position == 1) isStudent = false;

            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {
                //do nothing
            }
        });
    }
    private void checkIdentity(){
        //check if the uid exist in student or trainer. Based on whether it's a student or a trainer, we go to differnet pages.
            mDatabase.child("Student").addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                    if(dataSnapshot.child(uid).exists()) {
                        Toast.makeText(getApplicationContext(), "Student!", Toast.LENGTH_SHORT).show();
                        startActivity(new Intent(SignUpLogInActivity.this, SignUpInfo.class));
                    }
                    else {
                        Toast.makeText(getApplicationContext(), "Trainer!", Toast.LENGTH_SHORT).show();
                        startActivity(new Intent(SignUpLogInActivity.this, TrainerCertify.class));
                    }
                    finish();
                }
                @Override
                public void onCancelled(@NonNull DatabaseError databaseError) {

                }
            });

    }

}
