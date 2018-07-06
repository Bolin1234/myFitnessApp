package com.example.bolinwang.tudar;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;
import android.widget.TextView;

public class StudentMainActivity extends AppCompatActivity {

    private TextView mTextMessage;

    private BottomNavigationView.OnNavigationItemSelectedListener mOnNavigationItemSelectedListener
            = new BottomNavigationView.OnNavigationItemSelectedListener() {

        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem item) {
            switch (item.getItemId()) {
                case R.id.navigation_article_student:
                    mTextMessage.setText(R.string.title_article_student);
                    return true;
                case R.id.navigation_private_training_student:
                    mTextMessage.setText(R.string.title_private_training_student);
                    return true;
                case R.id.navigation_notifications_student:
                    mTextMessage.setText(R.string.title_notifications_student);
                    return true;
                case R.id.navigation_ask_question_student:
                    mTextMessage.setText(R.string.title_ask_question_student);
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

}
