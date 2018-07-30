package com.example.bolinwang.tudar;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.UUID;

public class QuickAsk extends AppCompatActivity {
    Button btnBack;
    Button btnSend;
    EditText editTextMsg;
    FirebaseAuth auth;
    private DatabaseReference mDatabaseUser, mDatabaseQuestion;

    String uid;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_quick_ask);

        btnBack = findViewById(R.id.BtnBack);
        btnSend = findViewById(R.id.BtnSend);
        editTextMsg = findViewById(R.id.editTextMsg);

        auth = FirebaseAuth.getInstance();
        mDatabaseUser = FirebaseDatabase.getInstance().getReference("User");  //initialize database reference
        mDatabaseQuestion = FirebaseDatabase.getInstance().getReference("Question");  //initialize database reference
        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();

        if(user != null)
            uid = user.getUid();
        // throw exception for null user

        btnBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(QuickAsk.this, StudentMainActivity.class);
                startActivity(intent);
                finish();
            }
        });

        btnSend.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String plaintextMessage = editTextMsg.getText().toString().trim();
                int length = plaintextMessage.length();
                if(length== 0){
                    Toast.makeText(getApplicationContext(), "Please Enter Your Question", Toast.LENGTH_SHORT).show();
                    return;
                }
                else if (length > 200){
                    Toast.makeText(getApplicationContext(), "Your Question Exceeds 200 Words", Toast.LENGTH_SHORT).show();
                    return;
                }
                else{
                    String questionID = UUID.randomUUID().toString();
                    DatabaseReference mDatabaseTemp = mDatabaseQuestion.child("QuickQuestion").child(questionID);
                    mDatabaseTemp.child("AnswerID").setValue("");
                    mDatabaseTemp.child("IsAnswered").setValue(false);
                    mDatabaseTemp.child("IsPaid").setValue(false);
                    mDatabaseTemp.child("QuestionContent").setValue(plaintextMessage);
                    mDatabaseTemp.child("ReplyContent").setValue("");
                    mDatabaseTemp.child("ReplyTime").setValue("");
                    mDatabaseTemp.child("TimeStamp").setValue(System.currentTimeMillis()/1000);
                    mDatabaseTemp.child("WithID").setValue(uid);
                    mDatabaseUser.child("Student").child(uid).child("Question").child(UUID.randomUUID().toString()).child("QuickQuestionLocation").setValue(questionID);
                }

                Intent intent = new Intent(QuickAsk.this, StudentMainActivity.class);
                startActivity(intent);
                finish();
            }
        });
    }
}
