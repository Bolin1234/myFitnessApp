package com.example.bolinwang.tudar;

import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.constraint.ConstraintLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.OnProgressListener;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;
import com.squareup.picasso.Picasso;

import java.io.IOException;

public class TrainerProfileEdit extends AppCompatActivity {
    ConstraintLayout constraintLayoutProfileName;
    ConstraintLayout constraintLayoutProfilePic;
    TextView textViewName;
    ImageView imageViewPrifilePic;
    String uid;
    DatabaseReference mDatabaseUser;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_trainer_profile_edit);
        constraintLayoutProfileName = findViewById(R.id.constraintLayoutProfileName);
        constraintLayoutProfilePic = findViewById(R.id.constraintLayoutProfilePic);
        textViewName = findViewById(R.id.textViewName);
        imageViewPrifilePic = findViewById(R.id.imageViewProfilePic);
        mDatabaseUser = FirebaseDatabase.getInstance().getReference("User");
        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();

        if(user != null)
            uid = user.getUid();

        mDatabaseUser.child("Trainer").child(uid).child("UserData").child("Name").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if(dataSnapshot.getValue()!= null){
                    textViewName.setText(dataSnapshot.getValue().toString());
                }

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
        mDatabaseUser.child("Trainer").child(uid).child("LoginInfo").child("ProfilePicLink").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if(dataSnapshot.getValue() != null){
                    String profilePicLink = dataSnapshot.getValue().toString();
                    Picasso.get().load(profilePicLink).into(imageViewPrifilePic);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

        constraintLayoutProfileName.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intentChangeName = new Intent(TrainerProfileEdit.this, TrainerChangeName.class);
                startActivity(intentChangeName);
                finish();
            }
        });

        constraintLayoutProfilePic.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intentChangeProfilePic = new Intent(TrainerProfileEdit.this, TrainerChangeProfilePic.class);
                startActivity(intentChangeProfilePic);
                finish();
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
            Intent intentBack = new Intent(TrainerProfileEdit.this, TrainerMainActivity.class);
            startActivity(intentBack);
            finish();
        }
        return super.onOptionsItemSelected(item);
    }

}
