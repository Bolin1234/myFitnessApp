package com.example.bolinwang.tudar;

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
    int imageIds[];
    private static final String TAG = "TrainerAnswer";
    TrainerViewQuestionImageAdapter trainerViewQuestionImageAdapter;

    List<String> urlList = new ArrayList<>();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_trainer_answer_question);

        urlList = getIncomingIntent();

        viewPager = (ViewPager) findViewById(R.id.viewPager);
        trainerViewQuestionImageAdapter = new TrainerViewQuestionImageAdapter(TrainerAnswerQuestionActivity.this, urlList);
        viewPager.setAdapter(trainerViewQuestionImageAdapter);
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
            List<String> urlList = new ArrayList<>();
            urlList.add(photo1Location);
            urlList.add(photo2Location);
            urlList.add(photo3Location);
            return urlList;
        }
        return null;
    }
}
