package com.example.bolinwang.tudar;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class TrainerChangeName extends AppCompatActivity {
    EditText editTextName;
    Button btnConfirm;
    DatabaseReference mDatabaseUser;
    String uid;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_trainer_change_name);

        mDatabaseUser = FirebaseDatabase.getInstance().getReference("User");
        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        if(user != null)
            uid = user.getUid();

        editTextName = findViewById(R.id.editTextName);
        btnConfirm = findViewById(R.id.btnConfirm);

        btnConfirm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(!TextUtils.isEmpty(editTextName.getText().toString())){
                    String name = editTextName.getText().toString();
                    mDatabaseUser.child("Trainer").child(uid).child("UserData").child("Name").setValue(name);
                    Intent intentNameChanged = new Intent(TrainerChangeName.this, TrainerProfileEdit.class);
                    startActivity(intentNameChanged);
                    finish();

                }else{
                    Toast.makeText(getApplicationContext(), "名字不能为空！", Toast.LENGTH_SHORT).show();
                }
            }
        });


        if(getSupportActionBar()!=null){
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setDisplayShowHomeEnabled(true);
        }
    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if(item.getItemId() == android.R.id.home){
            Intent intentBack = new Intent(TrainerChangeName.this, TrainerProfileEdit.class);
            startActivity(intentBack);
            finish();
        }
        return super.onOptionsItemSelected(item);
    }
}
