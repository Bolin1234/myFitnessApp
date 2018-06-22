package com.example.bolinwang.tudar;


import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
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
import android.content.Intent;
import android.text.TextUtils;


public class SignUpLogInActivity extends AppCompatActivity {
    private EditText emailSignUp;
    private EditText passwordSignUp;
    private Button signUpButton;
    private Button logInButton;
    private Spinner spinnerIsTrainer;
    private ArrayAdapter<CharSequence> adapter;
    private FirebaseAuth auth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup_login);
        auth = FirebaseAuth.getInstance();

        if(auth.getCurrentUser()!= null){       //if already logged in, then no need to login again
            startActivity(new Intent(SignUpLogInActivity.this, SignUpInfo.class));
            finish();
        }

        emailSignUp = (EditText) findViewById(R.id.EmailSignUp);
        passwordSignUp = (EditText) findViewById(R.id.PasswordSignUp);
        signUpButton = (Button) findViewById(R.id.SignUpButton);
        logInButton = (Button) findViewById(R.id.LogInButton);
        spinnerIsTrainer = (Spinner) findViewById(R.id.SpinnerIsTrainer);

        //spinner adapter
        adapter = ArrayAdapter.createFromResource(this, R.array.IsTrainerArray, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerIsTrainer.setAdapter(adapter);

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
                                } else {
                                    Intent intentLogin = new Intent(SignUpLogInActivity.this, MainActivity.class);
                                    startActivity(intentLogin);
                                    finish();
                                }
                            }
                });
            }
        });

        signUpButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String emailSignUpUpload = emailSignUp.getText().toString().trim();
                String passwordSignUpUpload = passwordSignUp.getText().toString().trim();

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
                            Intent intentSignUp = new Intent(SignUpLogInActivity.this, SignUpInfo.class);
                            startActivity(intentSignUp);
                            finish();
                        }
                    }
                });
            }
        });
    }
}
