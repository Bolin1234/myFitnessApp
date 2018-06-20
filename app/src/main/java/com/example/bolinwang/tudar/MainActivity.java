package com.example.bolinwang.tudar;

<<<<<<< HEAD
//
import android.support.v7.app.AppCompatActivity;
=======
/*import android.support.v7.app.AppCompatActivity;
>>>>>>> parent of 4b6a2b9... SignUpFinished
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.google.firebase.auth.FirebaseAuth;

public class  MainActivity extends AppCompatActivity {
   // TextView txt;
    //Button btn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        txt = (TextView) findViewById(R.id.nameLogin);
        txt = (TextView) findViewById(R.id.phoneNoLogin);
        btn = (Button) findViewById(R.id.signinButton);

        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                txt.setText("");
            }
        });
    }


}*/

import android.content.Intent;
        import android.os.Bundle;
        import android.support.annotation.NonNull;
        import android.support.v7.app.AppCompatActivity;
        import android.text.TextUtils;
        import android.view.View;
        import android.widget.Button;
        import android.widget.EditText;

        import com.google.android.gms.tasks.OnCompleteListener;
        import com.google.android.gms.tasks.Task;
        import com.google.firebase.auth.AuthResult;
        import com.google.firebase.auth.FirebaseAuth;



public class MainActivity extends AppCompatActivity {
    private EditText nameLogin;
    private EditText phoneNoLogin;
    private Button signinButton;
    private FirebaseAuth auth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        auth = FirebaseAuth.getInstance();

        nameLogin = (EditText) findViewById(R.id.NameLogin);
        phoneNoLogin = (EditText) findViewById(R.id.PhoneNoLogin);
        signinButton = (Button) findViewById(R.id.SigninButton);

        String nameLoginUpload = nameLogin.getText().toString().trim();
        String phoneNoLoginUpload = phoneNoLogin.getText().toString().trim();

        auth.createUserWithEmailAndPassword(nameLoginUpload, phoneNoLoginUpload);
    }
}

