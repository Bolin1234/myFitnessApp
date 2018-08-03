package com.example.bolinwang.tudar;

import android.app.ProgressDialog;
import android.content.ClipData;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.net.Uri;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.OnProgressListener;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.io.IOException;
import java.util.ArrayList;
import java.util.UUID;
import java.util.List;

public class QuickAsk extends AppCompatActivity {
    Button btnBack;
    Button btnSend;
    Button btnSelectPic;
    EditText editTextMsg;
    FirebaseAuth auth;
    private DatabaseReference mDatabaseUser, mDatabaseQuestion;
    int PICK_IMAGE_MULTIPLE = 1;
    String imageEncoded;
    List<String> imagesEncodedList;
    String uid;
    ImageView imgAsk0;
    ImageView imgAsk1;
    ImageView imgAsk2;
    FirebaseStorage storage;
    StorageReference storageReference;
    boolean onePicFlag = true;
    private Uri mImageUri;
    private ArrayList<Uri> mArrayUri;
    String questionID;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_quick_ask);
        btnSelectPic = findViewById(R.id.BtnSelectPic);
        btnBack = findViewById(R.id.BtnBack);
        btnSend = findViewById(R.id.BtnSend);
        editTextMsg = findViewById(R.id.editTextMsg);
        imgAsk0 = (ImageView) findViewById(R.id.ImgAsk0);
        imgAsk1 = (ImageView) findViewById(R.id.ImgAsk1);
        imgAsk2 = (ImageView) findViewById(R.id.ImgAsk2);

        storage = FirebaseStorage.getInstance();
        storageReference = storage.getReference();
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
                    questionID = UUID.randomUUID().toString();
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
                uploadImage();

                Intent intent = new Intent(QuickAsk.this, StudentMainActivity.class);
                startActivity(intent);
                finish();
            }
        });
        btnSelectPic.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                chooseImage();
            }
        });
        imgAsk0.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                imgAsk0.setImageDrawable(null);
            }
        });
        imgAsk1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                imgAsk1.setImageDrawable(null);
            }
        });
        imgAsk2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                imgAsk2.setImageDrawable(null);
            }
        });
    }


    private void chooseImage() {
        Intent intent = new Intent();
        intent.setType("image/*");
        intent.putExtra(Intent.EXTRA_ALLOW_MULTIPLE, true);
        intent.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(Intent.createChooser(intent, "Select Picture"), PICK_IMAGE_MULTIPLE);
    }
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        try {
            if(requestCode == PICK_IMAGE_MULTIPLE && resultCode == RESULT_OK
                    && data != null ){

                String[] filePathColumn ={MediaStore.Images.Media.DATA};
                imagesEncodedList = new ArrayList<String>();
                if(data.getData() != null){
                    onePicFlag = true;
                    mImageUri=data.getData();
                    Bitmap bitmap = MediaStore.Images.Media.getBitmap(getContentResolver(),mImageUri);
                    imgAsk0.setImageBitmap(bitmap);
                    // Get the cursor
                    Cursor cursor = getContentResolver().query(mImageUri,
                            filePathColumn, null, null, null);
                    // Move to first row
                    cursor.moveToFirst();

                    int columnIndex = cursor.getColumnIndex(filePathColumn[0]);
                    imageEncoded  = cursor.getString(columnIndex);
                    cursor.close();
                } else {
                    if (data.getClipData() != null) {
                        onePicFlag = false;
                        ClipData mClipData = data.getClipData();
                        mArrayUri = new ArrayList<Uri>();
                        for (int i = 0; i < mClipData.getItemCount(); i++) {

                            ClipData.Item item = mClipData.getItemAt(i);
                            Uri uri = item.getUri();
                            Bitmap bitmap = MediaStore.Images.Media.getBitmap(getContentResolver(),uri);
                            if(i == 0){
                                imgAsk0.setImageBitmap(bitmap);
                            } else if(i == 1){
                                imgAsk1.setImageBitmap(bitmap);
                            } else
                                imgAsk2.setImageBitmap(bitmap);

                            mArrayUri.add(uri);
                            // Get the cursor
                            Cursor cursor = getContentResolver().query(uri, filePathColumn, null, null, null);
                            // Move to first row
                            cursor.moveToFirst();

                            int columnIndex = cursor.getColumnIndex(filePathColumn[0]);
                            imageEncoded = cursor.getString(columnIndex);
                            imagesEncodedList.add(imageEncoded);
                            cursor.close();

                        }


                        Log.v("LOG_TAG", "Selected Images" + mArrayUri.size());
                        Log.v("LIST1", "address1" + imagesEncodedList.get(1));
                        Log.v("LIST2", "address2" + imagesEncodedList.get(2));
                        Log.v("LIST3", "address3" + imagesEncodedList.get(0));
                    }
                }
            } else{
                Toast.makeText(this, "You haven't picked Image",
                        Toast.LENGTH_LONG).show();
            }
        } catch (Exception e) {
            Toast.makeText(this, "Something went wrong", Toast.LENGTH_LONG)
                    .show();
}

        super.onActivityResult(requestCode, resultCode, data);
                }

    private void uploadImage() {
        if(onePicFlag){
            if(mImageUri != null)
            {
                final ProgressDialog progressDialog = new ProgressDialog(this);
                progressDialog.setTitle("Uploading...");
                progressDialog.show();
                final StorageReference ref = storageReference.child("QuestionImages/"+ UUID.randomUUID().toString());
                ref.putFile(mImageUri)
                        .addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                            @Override
                            public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                                progressDialog.dismiss();
                                Toast.makeText(QuickAsk.this, "Uploaded", Toast.LENGTH_SHORT).show();

                                ref.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                                    @Override
                                    public void onSuccess(Uri uri) {
                                        DatabaseReference mDatabaseTemp = mDatabaseQuestion.child("QuickQuestion").child(questionID);
                                        mDatabaseTemp.child("Photos").child("Photo"+"1"+"Location").setValue(uri.toString());
                                    }
                                });
                            }
                        })
                        .addOnFailureListener(new OnFailureListener() {
                            @Override
                            public void onFailure(@NonNull Exception e) {
                                progressDialog.dismiss();
                                Toast.makeText(QuickAsk.this, "Failed "+e.getMessage(), Toast.LENGTH_SHORT).show();
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
        } else{
            for(int j = 0; j < mArrayUri.size(); j++){
                final ProgressDialog progressDialog = new ProgressDialog(this);
                progressDialog.setTitle("Uploading...");
                progressDialog.show();

                final StorageReference ref = storageReference.child("QuestionImages/"+ UUID.randomUUID().toString());
                ref.putFile(mArrayUri.get(j))
                        .addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                            @Override
                            public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                                progressDialog.dismiss();
                                Toast.makeText(QuickAsk.this, "Uploaded", Toast.LENGTH_SHORT).show();
                                ref.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                                    @Override
                                    public void onSuccess(Uri uri) {
                                        DatabaseReference mDatabaseTemp = mDatabaseQuestion.child("QuickQuestion").child(questionID);
                                        mDatabaseTemp.child("Photos").child("Photo"+j+"Location").setValue(uri.toString());
                                    }
                                });
                            }
                        })
                        .addOnFailureListener(new OnFailureListener() {
                            @Override
                            public void onFailure(@NonNull Exception e) {
                                progressDialog.dismiss();
                                Toast.makeText(QuickAsk.this, "Failed "+e.getMessage(), Toast.LENGTH_SHORT).show();
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


    }
}
