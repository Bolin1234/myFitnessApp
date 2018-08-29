package com.example.bolinwang.tudar;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class TrainerWaiting extends AppCompatActivity {
    FirebaseAuth auth;
    DatabaseReference mDatabase;
    String uid;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_trainer_waiting);
        auth = FirebaseAuth.getInstance();
        mDatabase = FirebaseDatabase.getInstance().getReference("User");  //initialize database reference
        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        if (user != null) {
            uid = user.getUid();
            //check if verified or if info completed
            mDatabase.child("Trainer").child(uid).child("LoginInfo").child("verified").addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                    if (dataSnapshot.getValue().toString().equals("true")) {
                        //Toast.makeText(getApplicationContext(), "Verified!", Toast.LENGTH_SHORT).show();
                        Intent intentTrainerMain = new Intent(TrainerWaiting.this, TrainerMainActivity.class);
                        startActivity(intentTrainerMain);
                        finish();
                    }/* else {
                        //Toast.makeText(getApplicationContext(), "Wait Until Being Verified!", Toast.LENGTH_SHORT).show();
                        Intent intentTrainerWaiting = new Intent(TrainerWaiting.this, TrainerWaiting.class);
                        startActivity(intentTrainerWaiting);
                        finish();
                    }*/


                }

                @Override
                public void onCancelled(@NonNull DatabaseError databaseError) {
                }
            });
        }
    }
}