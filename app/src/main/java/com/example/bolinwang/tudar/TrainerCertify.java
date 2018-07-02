package com.example.bolinwang.tudar;

import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.view.View;
//import android.widget.AdapterView;
//import android.widget.ArrayAdapter;
import android.widget.Button;
///import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageView;
//import android.widget.Spinner;
import android.widget.Toast;


import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.UserInfo;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.OnProgressListener;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.io.*;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;
public class TrainerCertify extends AppCompatActivity {
    EditText editTextID;
    EditText editTextName;
    Button btnUpload;
    Button btnSelect;
    Button btnDone;
    private ImageView imgCertify;

    private Uri filePath;
    private final int PICK_IMAGE_REQUEST = 71;

    FirebaseAuth auth;
    private DatabaseReference mDatabase;
    int freq;
    String uid;
    FirebaseStorage storage;
    StorageReference storageReference;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_trainer_certify);

        editTextID = (EditText) findViewById(R.id.EditTextID);
        editTextName = (EditText) findViewById(R.id.EditTextName);
        btnSelect = (Button) findViewById(R.id.BtnImgSelect);
        btnUpload = (Button) findViewById(R.id.BtnImgUpload);
        btnDone = (Button) findViewById(R.id.BtnDone);
        imgCertify = (ImageView) findViewById(R.id.ImgCertify);
        storage = FirebaseStorage.getInstance();
        storageReference = storage.getReference();

        auth = FirebaseAuth.getInstance();
        mDatabase = FirebaseDatabase.getInstance().getReference("User");  //initialize database reference
        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();

        if(user != null)
            uid = user.getUid();

        btnSelect.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                chooseImage();
            }
        });

        btnUpload.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                uploadImage();
            }
        });

        btnDone.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                infoUpload();
            }
        });
    }
    private void chooseImage() {
        Intent intent = new Intent();
        intent.setType("image/*");
        intent.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(Intent.createChooser(intent, "Select Picture"), PICK_IMAGE_REQUEST);
    }
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode == PICK_IMAGE_REQUEST && resultCode == RESULT_OK
                && data != null && data.getData() != null )
        {
            filePath = data.getData();
            try {
                Bitmap bitmap = MediaStore.Images.Media.getBitmap(getContentResolver(), filePath);
                imgCertify.setImageBitmap(bitmap);
            }
            catch (IOException e)
            {
                e.printStackTrace();
            }
        }
    }
    private void uploadImage() {

        if(filePath != null)
        {
            final ProgressDialog progressDialog = new ProgressDialog(this);
            progressDialog.setTitle("Uploading...");
            progressDialog.show();

            StorageReference ref = storageReference.child("images/"+ UUID.randomUUID().toString());
            ref.putFile(filePath)
                    .addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                        @Override
                        public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                            progressDialog.dismiss();
                            Toast.makeText(TrainerCertify.this, "Uploaded", Toast.LENGTH_SHORT).show();
                        }
                    })
                    .addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e) {
                            progressDialog.dismiss();
                            Toast.makeText(TrainerCertify.this, "Failed "+e.getMessage(), Toast.LENGTH_SHORT).show();
                        }
                    })
                    .addOnProgressListener(new OnProgressListener<UploadTask.TaskSnapshot>() {
                        @Override
                        public void onProgress(UploadTask.TaskSnapshot taskSnapshot) {
                            double progress = (100.0*taskSnapshot.getBytesTransferred()/taskSnapshot
                                    .getTotalByteCount());
                            progressDialog.setMessage("Uploaded "+(int)progress+"%");
                        }
                    });
        }
    }
    private void infoUpload(){
        String editTextIDString = editTextID.getText().toString().trim();
        String editTextNameString = editTextName.getText().toString().trim();
        if (TextUtils.isEmpty(editTextIDString)) {
            Toast.makeText(getApplicationContext(), "Enter ID!", Toast.LENGTH_SHORT).show();
            return;
        } else {
            int editTextIDUpload = Integer.parseInt(editTextIDString);
            mDatabase.child("Trainer").child(uid).child("UserData").child("ID").setValue(editTextIDUpload);
        }
        mDatabase.child("Trainer").child(uid).child("UserData").child("Frequency").setValue(freq);
        if (TextUtils.isEmpty(editTextNameString)) {
            Toast.makeText(getApplicationContext(), "Enter Name!", Toast.LENGTH_SHORT).show();
            return;
        } else{
            mDatabase.child("Trainer").child(uid).child("UserData").child("Height").setValue(editTextNameString);
        }
    }
}
