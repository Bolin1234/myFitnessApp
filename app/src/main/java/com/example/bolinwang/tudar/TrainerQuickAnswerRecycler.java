package com.example.bolinwang.tudar;

import android.app.ProgressDialog;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

import java.util.ArrayList;
import java.util.List;

public class TrainerQuickAnswerRecycler extends AppCompatActivity {

    FirebaseAuth auth;
    private DatabaseReference mDatabaseQuestion;
    FirebaseStorage storage;
    StorageReference storageReference;

    private RecyclerView recyclerView;
    private RecyclerView.Adapter adapter;
    private List<TrainerQuickAnswerListItem> listItems;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_trainer_quick_answer_recycler);

        storage = FirebaseStorage.getInstance();
        storageReference = storage.getReference();
        auth = FirebaseAuth.getInstance();
        mDatabaseQuestion = FirebaseDatabase.getInstance().getReference("Question");  //initialize database reference

        recyclerView = (RecyclerView) findViewById(R.id.recyclerView);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        listItems = new ArrayList<>();
        adapter = new TrainerQuickAnswerAdapter(listItems,getApplicationContext());
        loadRecyclerViewData();
    }
    private void loadRecyclerViewData(){
        final ProgressDialog progressDialog = new ProgressDialog(this);
        progressDialog.setMessage("Loading Data...");
        progressDialog.show();

        mDatabaseQuestion.child("QuickQuestion").addChildEventListener(new ChildEventListener() {

            @Override
            public void onChildAdded(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {
                TrainerQuickAnswerListItem trainerQuickAnswerListItem = new TrainerQuickAnswerListItem();
                trainerQuickAnswerListItem = dataSnapshot.getValue(TrainerQuickAnswerListItem.class);
                if(!trainerQuickAnswerListItem.getIsAnswered()){
                    listItems.add(trainerQuickAnswerListItem);
                    recyclerView.setAdapter(adapter);
                }

                progressDialog.dismiss();
            }

            @Override
            public void onChildChanged(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {

            }

            @Override
            public void onChildRemoved(@NonNull DataSnapshot dataSnapshot) {
                //still have problem here

                TrainerQuickAnswerListItem trainerQuickAnswerListItem = new TrainerQuickAnswerListItem();
                trainerQuickAnswerListItem = dataSnapshot.getValue(TrainerQuickAnswerListItem.class);
                //delete the question in real-time
                LOOP:
                    for(int i = 0; i<listItems.size(); i++){
                        if ((trainerQuickAnswerListItem.getTimeStamp() == listItems.get(i).getTimeStamp()) &&
                                (trainerQuickAnswerListItem.getQuestionContent() == listItems.get(i).getQuestionContent())) {
                            listItems.remove(i);
                            break LOOP;
                        }
                    }
                recyclerView.setAdapter(adapter);
            }

            @Override
            public void onChildMoved(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });


    }
}
