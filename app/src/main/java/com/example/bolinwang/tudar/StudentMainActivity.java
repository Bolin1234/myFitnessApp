package com.example.bolinwang.tudar;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.constraint.ConstraintLayout;
import android.support.design.widget.BottomNavigationView;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;
import android.widget.Button;
import android.widget.TextView;

public class StudentMainActivity extends AppCompatActivity {

    private TextView mTextMessage;

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
                    return true;
            }
            return false;
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_student_main);

        mTextMessage = (TextView) findViewById(R.id.message);
        BottomNavigationView navigation = (BottomNavigationView) findViewById(R.id.NavigationStudent);
        navigation.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener);
    }
    private void articleStudent(){
        ConstraintLayout layoutArtiStu = (ConstraintLayout) findViewById(R.id.LayoutArtiStu);
        //layoutArtiStu.bringToFront();
    }
    private void privateTrainingStudent(){
        //ConstraintLayout layoutPrivStu = (ConstraintLayout) findViewById(R.id.LayoutPrivStu);
        //layoutPrivStu.bringToFront();
        //Button btnDiet = (Button)findViewById(R.id.BtnDiet);
        //Button btnExercise = (Button)findViewById(R.id.BtnExercise);
        //Button btnChat = (Button)findViewById(R.id.BtnChat);
        //Button btnVidChat = (Button)findViewById(R.id.BtnVidChat);
        //Button btnSchedule = (Button)findViewById(R.id.BtnSchedule);

    }
    private void notificationStudent(){
        ConstraintLayout layoutNotiStu = (ConstraintLayout) findViewById(R.id.LayoutNotiStu);
       // layoutNotiStu.bringToFront();

    }
    private void askQuestionStudent(){
        ConstraintLayout layoutAskStu = (ConstraintLayout) findViewById(R.id.LayoutAskStu);
       // layoutAskStu.bringToFront();
    }

}
