package com.example.bolinwang.tudar;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.constraint.ConstraintLayout;
import android.support.design.widget.BottomNavigationView;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

public class TrainerMainActivity extends AppCompatActivity {

    private TextView mTextMessage;

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

        mTextMessage = (TextView) findViewById(R.id.message);
        BottomNavigationView navigation = (BottomNavigationView) findViewById(R.id.NavigationTrainer);
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
        //Button btnExercise = (Button)findViewById(R.id.BtnExercise);
        //Button btnChat = (Button)findViewById(R.id.BtnChat);
        //Button btnVidChat = (Button)findViewById(R.id.BtnVidChat);
        //Button btnSchedule = (Button)findViewById(R.id.BtnSchedule);

    }
    private void notificationTrainer(){
        //  ConstraintLayout layoutNotiTra = findViewById(R.id.layoutNotiTra);
        //  layoutNotiTra.bringToFront();


        ConstraintLayout layoutAnsTra = findViewById(R.id.LayoutAnsTra);
        layoutAnsTra.setVisibility(ConstraintLayout.GONE);
        ConstraintLayout layoutPrivTra = findViewById(R.id.LayoutPrivTra);
        layoutPrivTra.setVisibility(ConstraintLayout.GONE);
        ConstraintLayout layoutNotiTra = findViewById(R.id.LayoutNotiTra);
        layoutNotiTra.setVisibility(ConstraintLayout.VISIBLE);
        ConstraintLayout layoutArtiTra = findViewById(R.id.LayoutArtiTra);
        layoutArtiTra.setVisibility(ConstraintLayout.GONE);

    }
    private void answerQuestionTrainer(){
        ConstraintLayout layoutAnsTra = findViewById(R.id.LayoutAnsTra);
        //layoutAnsTra.bringToFront();
        layoutAnsTra.setVisibility(ConstraintLayout.VISIBLE);
        ConstraintLayout layoutPrivTra = findViewById(R.id.LayoutPrivTra);
        layoutPrivTra.setVisibility(ConstraintLayout.GONE);
        ConstraintLayout layoutNotiTra = findViewById(R.id.LayoutNotiTra);
        layoutNotiTra.setVisibility(ConstraintLayout.GONE);
        ConstraintLayout layoutArtiTra = findViewById(R.id.LayoutArtiTra);
        layoutArtiTra.setVisibility(ConstraintLayout.GONE);
    }

}
