package com.example.bolinwang.tudar;

import android.content.Intent;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.nfc.Tag;
import android.os.Environment;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

public class TrainerAnswerQuestionActivity extends AppCompatActivity {
    FirebaseAuth auth;
    private DatabaseReference mDatabaseQuestion;
    FirebaseStorage storage;
    StorageReference storageReference;

    ViewPager viewPager;
    TextView textView;
    Button btnAnswer;
    int imageIds[];
    private static final String TAG = "TrainerAnswer";
    TrainerViewQuestionImageAdapter trainerViewQuestionImageAdapter;
    String questionContent;
    String timeStamp;

    List<String> urlList = new ArrayList<>();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_trainer_view_question_item);

        viewPager = (ViewPager) findViewById(R.id.viewPager);
        textView = (TextView) findViewById(R.id.textView);
        btnAnswer = (Button) findViewById(R.id.btnAnswer);
        urlList = getIncomingIntent();

        trainerViewQuestionImageAdapter = new TrainerViewQuestionImageAdapter(TrainerAnswerQuestionActivity.this, urlList);
        viewPager.setAdapter(trainerViewQuestionImageAdapter);

        btnAnswer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(TrainerAnswerQuestionActivity.this, TrainerAnswerQuestionWriteActivity.class);
                intent.putExtra("TimeStamp", timeStamp);
                intent.putExtra("QuestionContent", questionContent);
                startActivity(intent);
                finish();
            }
        });
    }
    private List<String> getIncomingIntent(){
        Log.d(TAG, "getIncomingIntent: checking for incoming intents");
        if(getIntent().hasExtra("Photo1Location") &&
                getIntent().hasExtra("Photo2Location") &&
                getIntent().hasExtra("Photo3Location")){
            Log.d(TAG, "found extras");

            String photo1Location = getIntent().getStringExtra("Photo1Location");
            String photo2Location = getIntent().getStringExtra("Photo2Location");
            String photo3Location = getIntent().getStringExtra("Photo3Location");
            questionContent = getIntent().getStringExtra("QuestionContent");
            timeStamp = getIntent().getStringExtra("TimeStamp");
            List<String> urlList = new ArrayList<>();
            if(photo1Location != null)
                urlList.add(photo1Location);
            if(photo2Location != null)
                urlList.add(photo2Location);
            if(photo3Location != null)
                urlList.add(photo3Location);

            textView.setText(questionContent);

            return urlList;
        }
        return null;
    }
    private void getQuestionContent(){

    }
}
