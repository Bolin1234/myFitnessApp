package com.example.bolinwang.tudar;

import android.app.LauncherActivity;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

import java.util.ArrayList;
import java.util.List;

public class TrainerQuickAnswerRecycler extends AppCompatActivity {

    FirebaseAuth auth;
    private DatabaseReference mDatabaseUser;
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
        mDatabaseUser = FirebaseDatabase.getInstance().getReference("User");  //initialize database reference

        recyclerView = (RecyclerView) findViewById(R.id.recyclerView);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        listItems = new ArrayList<>();
        //this is only for practice, it will be changed later on by the data from Database
        for(int i = 0; i <10; i++){
            TrainerQuickAnswerListItem listItem = new TrainerQuickAnswerListItem(
                    "heading"+(i+1),
                    "1:10",
                    "Dummmy variable for now"
            );
            listItems.add(listItem);

        }
        adapter = new TrainerQuickAnswerAdapter(listItems, this);

        recyclerView.setAdapter(adapter);
    }
}
