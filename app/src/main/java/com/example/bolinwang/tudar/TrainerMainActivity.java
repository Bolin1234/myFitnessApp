package com.example.bolinwang.tudar;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;
import android.widget.TextView;

public class TrainerMainActivity extends AppCompatActivity {

    private TextView mTextMessage;

    private BottomNavigationView.OnNavigationItemSelectedListener mOnNavigationItemSelectedListener
            = new BottomNavigationView.OnNavigationItemSelectedListener() {

        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem item) {
            switch (item.getItemId()) {
                case R.id.navigation_article_trainer:
                    mTextMessage.setText(R.string.title_article_trainer);
                    return true;
                case R.id.navigation_answer_question_trainer:
                    mTextMessage.setText(R.string.title_answer_question_trainer);
                    return true;
                case R.id.navigation_private_training_trainer:
                    mTextMessage.setText(R.string.title_private_training_trainer);
                    return true;
                case R.id.navigation_notifications_trainer:
                    mTextMessage.setText(R.string.title_notifications_trainer);
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

}
