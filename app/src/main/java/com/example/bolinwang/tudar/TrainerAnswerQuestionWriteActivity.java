package com.example.bolinwang.tudar;

import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;
import java.util.List;

public class TrainerAnswerQuestionWriteActivity extends AppCompatActivity {
    EditText editText;
    Button btnPublish;

    String uid;
    DatabaseReference mDatabaseUser;
    DatabaseReference mDatabaseQuickQuestion;
    FirebaseAuth auth;
    String timeStamp;
    String questionContent;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_trainer_answer_question_write);

        auth = FirebaseAuth.getInstance();
        mDatabaseUser = FirebaseDatabase.getInstance().getReference("User");  //initialize database reference
        mDatabaseQuickQuestion = FirebaseDatabase.getInstance().getReference("Question").child("QuickQuestion");  //initialize database reference
        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();

        if(user != null)
            uid = user.getUid();

        editText = (EditText)findViewById(R.id.editText);
        btnPublish = (Button)findViewById(R.id.btnPublish);
        getIncomingIntent();
        btnPublish.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //check if textbox is empty
                if(!TextUtils.isEmpty(editText.getText())){
                    //upload the time, id, content to database.
                    mDatabaseQuickQuestion.addChildEventListener(new ChildEventListener() {
                        @Override
                        public void onChildAdded(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {
                            TrainerQuickAnswerListItem trainerQuickAnswerListItem = new TrainerQuickAnswerListItem();
                            trainerQuickAnswerListItem = dataSnapshot.getValue(TrainerQuickAnswerListItem.class);
                            if(trainerQuickAnswerListItem.getQuestionContent().equals(questionContent)
                                    && trainerQuickAnswerListItem.getTimeStamp().toString().equals(timeStamp)){
                               String questionId = dataSnapshot.getRef().getKey();
                               mDatabaseQuickQuestion.child(questionId).child("ReplyContent").setValue(editText.getText().toString());
                               mDatabaseQuickQuestion.child(questionId).child("ReplyTime").setValue(System.currentTimeMillis()/1000);
                               mDatabaseQuickQuestion.child(questionId).child("AnswerID").setValue(uid);
                               mDatabaseQuickQuestion.child(questionId).child("IsAnswered").setValue(true);

                               Intent intent = new Intent(TrainerAnswerQuestionWriteActivity.this, TrainerQuickAnswerRecycler.class);
                               startActivity(intent);
                               finish();
                            }
                        }

                        @Override
                        public void onChildChanged(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {

                        }

                        @Override
                        public void onChildRemoved(@NonNull DataSnapshot dataSnapshot) {

                        }

                        @Override
                        public void onChildMoved(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {

                        }

                        @Override
                        public void onCancelled(@NonNull DatabaseError databaseError) {

                        }
                    });

                }else{
                    Toast.makeText(getApplicationContext(), "请输入内容!", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }
    private void getIncomingIntent(){
        if(getIntent().hasExtra("TimeStamp") &&
                getIntent().hasExtra("QuestionContent")){

            questionContent = getIntent().getStringExtra("QuestionContent");
            timeStamp = getIntent().getStringExtra("TimeStamp");
        }
    }
}
