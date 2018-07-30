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
import android.widget.TextView;
import android.widget.Toast;

public class StudentMainActivity extends AppCompatActivity {

    private TextView mTextMessage;
    Button btnQuickAsk;


    private BottomNavigationView.OnNavigationItemSelectedListener mOnNavigationItemSelectedListener
            = new BottomNavigationView.OnNavigationItemSelectedListener() {

        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem item) {
            switch (item.getItemId()) {
                case R.id.navigation_article_student:
                    articleStudent();
                    return true;
                case R.id.navigation_private_training_student:
                    privateTrainingStudent();
                    return true;
                case R.id.navigation_notifications_student:
                    notificationStudent();
                    return true;
                case R.id.navigation_ask_question_student:
                    askQuestionStudent();
                    //Toast.makeText(StudentMainActivity.this, "Ask Question Button Pressed", Toast.LENGTH_SHORT).show();
                    return true;
            }
            return false;
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_student_main);

        mTextMessage = findViewById(R.id.message);
        BottomNavigationView navigation = findViewById(R.id.NavigationStudent);
        navigation.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener);
        btnQuickAsk = findViewById(R.id.BtnQuickAsk);
        btnQuickAsk.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(StudentMainActivity.this, QuickAsk.class);
                startActivity(intent);
                finish();
            }
        });
    }


    private void articleStudent(){
        //ConstraintLayout layoutArtiStu = findViewById(R.id.LayoutArtiStu);
        //layoutArtiStu.bringToFront();
        ConstraintLayout layoutAskStu = findViewById(R.id.LayoutAskStu);
        layoutAskStu.setVisibility(ConstraintLayout.GONE);
        ConstraintLayout layoutPrivStu = findViewById(R.id.LayoutPrivStu);
        layoutPrivStu.setVisibility(ConstraintLayout.GONE);
        ConstraintLayout layoutNotiStu = findViewById(R.id.LayoutNotiStu);
        layoutNotiStu.setVisibility(ConstraintLayout.GONE);
        ConstraintLayout layoutArtiStu = findViewById(R.id.LayoutArtiStu);
        layoutArtiStu.setVisibility(ConstraintLayout.VISIBLE);
    }
    private void privateTrainingStudent(){
        //ConstraintLayout layoutPrivStu = findViewById(R.id.LayoutPrivStu);
        //layoutPrivStu.bringToFront();
        //layoutPrivStu.setVisibility(ConstraintLayout.VISIBLE);
        ConstraintLayout layoutAskStu = findViewById(R.id.LayoutAskStu);
        layoutAskStu.setVisibility(ConstraintLayout.GONE);
        ConstraintLayout layoutPrivStu = findViewById(R.id.LayoutPrivStu);
        layoutPrivStu.setVisibility(ConstraintLayout.VISIBLE);
        ConstraintLayout layoutNotiStu = findViewById(R.id.LayoutNotiStu);
        layoutNotiStu.setVisibility(ConstraintLayout.GONE);
        ConstraintLayout layoutArtiStu = findViewById(R.id.LayoutArtiStu);
        layoutArtiStu.setVisibility(ConstraintLayout.GONE);

        Button btnDiet = findViewById(R.id.BtnDiet);
        btnDiet.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(StudentMainActivity.this, "Diet Success", Toast.LENGTH_LONG).show();
            }
        });
        //Button btnExercise = (Button)findViewById(R.id.BtnExercise);
        //Button btnChat = (Button)findViewById(R.id.BtnChat);
        //Button btnVidChat = (Button)findViewById(R.id.BtnVidChat);
        //Button btnSchedule = (Button)findViewById(R.id.BtnSchedule);

    }
    private void notificationStudent(){
        //  ConstraintLayout layoutNotiStu = findViewById(R.id.LayoutNotiStu);
        //  layoutNotiStu.bringToFront();


        ConstraintLayout layoutAskStu = findViewById(R.id.LayoutAskStu);
        layoutAskStu.setVisibility(ConstraintLayout.GONE);
        ConstraintLayout layoutPrivStu = findViewById(R.id.LayoutPrivStu);
        layoutPrivStu.setVisibility(ConstraintLayout.GONE);
        ConstraintLayout layoutNotiStu = findViewById(R.id.LayoutNotiStu);
        layoutNotiStu.setVisibility(ConstraintLayout.VISIBLE);
        ConstraintLayout layoutArtiStu = findViewById(R.id.LayoutArtiStu);
        layoutArtiStu.setVisibility(ConstraintLayout.GONE);

    }
    private void askQuestionStudent(){
        ConstraintLayout layoutAskStu = findViewById(R.id.LayoutAskStu);
        //layoutAskStu.bringToFront();
        layoutAskStu.setVisibility(ConstraintLayout.VISIBLE);
        ConstraintLayout layoutPrivStu = findViewById(R.id.LayoutPrivStu);
        layoutPrivStu.setVisibility(ConstraintLayout.GONE);
        ConstraintLayout layoutNotiStu = findViewById(R.id.LayoutNotiStu);
        layoutNotiStu.setVisibility(ConstraintLayout.GONE);
        ConstraintLayout layoutArtiStu = findViewById(R.id.LayoutArtiStu);
        layoutArtiStu.setVisibility(ConstraintLayout.GONE);
    }

}
