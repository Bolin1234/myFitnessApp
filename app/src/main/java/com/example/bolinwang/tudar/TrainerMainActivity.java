package com.example.bolinwang.tudar;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.constraint.ConstraintLayout;
import android.support.design.widget.BottomNavigationView;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.squareup.picasso.Picasso;

public class TrainerMainActivity extends AppCompatActivity {

    TextView mTextMessage;
    Button btnQuickAnswer;
    TextView textViewNotiName;
    ConstraintLayout constraintLayoutProfile;
    ImageView imageViewProfile;

    String uid;
    DatabaseReference mDatabaseUser;

    private BottomNavigationView.OnNavigationItemSelectedListener mOnNavigationItemSelectedListener
            = new BottomNavigationView.OnNavigationItemSelectedListener() {

        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem item) {
            switch (item.getItemId()) {
                case R.id.navigation_article_trainer:
                    articleTrainer();
                    return true;
                case R.id.navigation_answer_question_trainer:
                    answerQuestionTrainer();
                    return true;
                case R.id.navigation_private_training_trainer:
                    privateTrainingTrainer();
                    return true;
                case R.id.navigation_notifications_trainer:
                    notificationTrainer();
                    return true;
            }
            return false;
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_trainer_main);
        btnQuickAnswer = findViewById(R.id.BtnQuickAnswer);
        mTextMessage =  findViewById(R.id.message);
        textViewNotiName = findViewById(R.id.textViewNotiName);
        constraintLayoutProfile = findViewById(R.id.constraintLayoutProfile);
        imageViewProfile = findViewById(R.id.imageViewProfile);
        BottomNavigationView navigation = findViewById(R.id.NavigationTrainer);

        mDatabaseUser = FirebaseDatabase.getInstance().getReference("User");
        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        if(user != null)
            uid = user.getUid();
        mDatabaseUser.child("Trainer").child(uid).child("LoginInfo").child("ProfilePicLink").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if(dataSnapshot.getValue() != null){
                    String profilePicLink = dataSnapshot.getValue().toString();
                    Picasso.get().load(profilePicLink).into(imageViewProfile);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
        mDatabaseUser.child("Trainer").child(uid).child("UserData").child("Name").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if(dataSnapshot.getValue() != null) {
                    String trainerName = dataSnapshot.getValue().toString();
                    textViewNotiName.setText(trainerName);
                }else{
                    textViewNotiName.setText("点击编辑资料");
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });


        navigation.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener);

    }
    private void articleTrainer(){
        ConstraintLayout layoutAnsTra = findViewById(R.id.LayoutAnsTra);
        layoutAnsTra.setVisibility(ConstraintLayout.GONE);
        ConstraintLayout layoutPrivTra = findViewById(R.id.LayoutPrivTra);
        layoutPrivTra.setVisibility(ConstraintLayout.GONE);
        ConstraintLayout layoutNotiTra = findViewById(R.id.LayoutNotiTra);
        layoutNotiTra.setVisibility(ConstraintLayout.GONE);
        ConstraintLayout layoutArtiTra = findViewById(R.id.LayoutArtiTra);
        layoutArtiTra.setVisibility(ConstraintLayout.VISIBLE);
    }
    private void privateTrainingTrainer(){
        ConstraintLayout layoutAnsTra = findViewById(R.id.LayoutAnsTra);
        layoutAnsTra.setVisibility(ConstraintLayout.GONE);
        ConstraintLayout layoutPrivTra = findViewById(R.id.LayoutPrivTra);
        layoutPrivTra.setVisibility(ConstraintLayout.VISIBLE);
        ConstraintLayout layoutNotiTra = findViewById(R.id.LayoutNotiTra);
        layoutNotiTra.setVisibility(ConstraintLayout.GONE);
        ConstraintLayout layoutArtiTra = findViewById(R.id.LayoutArtiTra);
        layoutArtiTra.setVisibility(ConstraintLayout.GONE);

        Button btnDiet = findViewById(R.id.BtnDiet);
        btnDiet.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(TrainerMainActivity.this, "Diet Success", Toast.LENGTH_LONG).show();
            }
        });


    }
    private void notificationTrainer(){
        ConstraintLayout layoutAnsTra = findViewById(R.id.LayoutAnsTra);
        layoutAnsTra.setVisibility(ConstraintLayout.GONE);
        ConstraintLayout layoutPrivTra = findViewById(R.id.LayoutPrivTra);
        layoutPrivTra.setVisibility(ConstraintLayout.GONE);
        ConstraintLayout layoutNotiTra = findViewById(R.id.LayoutNotiTra);
        layoutNotiTra.setVisibility(ConstraintLayout.VISIBLE);
        ConstraintLayout layoutArtiTra = findViewById(R.id.LayoutArtiTra);
        layoutArtiTra.setVisibility(ConstraintLayout.GONE);

        constraintLayoutProfile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intentEditProfile = new Intent(TrainerMainActivity.this, TrainerProfileEdit.class);
                startActivity(intentEditProfile);
                finish();
            }
        });
    }
    private void answerQuestionTrainer(){
        ConstraintLayout layoutAnsTra = findViewById(R.id.LayoutAnsTra);
        layoutAnsTra.setVisibility(ConstraintLayout.VISIBLE);
        ConstraintLayout layoutPrivTra = findViewById(R.id.LayoutPrivTra);
        layoutPrivTra.setVisibility(ConstraintLayout.GONE);
        ConstraintLayout layoutNotiTra = findViewById(R.id.LayoutNotiTra);
        layoutNotiTra.setVisibility(ConstraintLayout.GONE);
        ConstraintLayout layoutArtiTra = findViewById(R.id.LayoutArtiTra);
        layoutArtiTra.setVisibility(ConstraintLayout.GONE);

        btnQuickAnswer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(TrainerMainActivity.this, TrainerQuickAnswerRecycler.class);
                startActivity(intent);
                finish();
            }
        });
    }

}
