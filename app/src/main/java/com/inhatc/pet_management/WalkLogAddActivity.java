package com.inhatc.pet_management;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

public class WalkLogAddActivity extends AppCompatActivity {

    private FirebaseAuth mFirebaseAuth;
    private DatabaseReference mDatabaseRef;
    private FirebaseStorage mFirebaseStorage;
    private EditText mEtDate, mEtName, mEtTime, mEtStepNumber, mEtMeter, mEtMemo;
    private ImageView walk_image;
    private Uri mImageUri;
    private Button mBtnWalkLogAdd;
    private String strMemo;
    Date mDate;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_log_add);

        initializeUI();
        initializeFirebase();
        loadIntentData();
        setDefaultDate();

        mBtnWalkLogAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                addWalkLog();
            }
        });
    }

    private void initializeUI() {
        mEtName = findViewById(R.id.insert_pet_name);
        mEtTime = findViewById(R.id.insert_walk_time);
        mEtStepNumber = findViewById(R.id.insert_walk_step);
        mEtMeter = findViewById(R.id.insert_walk_meter);
        mEtDate = findViewById(R.id.insert_walk_date);
        mEtMemo = findViewById(R.id.insert_walk_memo);
        walk_image = findViewById(R.id.walklog_photo);
        mBtnWalkLogAdd = findViewById(R.id.btn_walk_add);
    }

    private void initializeFirebase() {
        mFirebaseAuth = FirebaseAuth.getInstance();
        mDatabaseRef = FirebaseDatabase.getInstance().getReference("pet_management");
        mFirebaseStorage = FirebaseStorage.getInstance();
    }

    private void loadIntentData() {
        Intent intent = getIntent();
        String strName = intent.getStringExtra("name");
        String strTime = intent.getStringExtra("time");
        String strStepNumber = intent.getStringExtra("stepNumber");
        String strMeter = intent.getStringExtra("meter");
        String imagePath = intent.getStringExtra("imagePath");

        if (imagePath != null) {
            mImageUri = Uri.fromFile(new File(imagePath));
            Bitmap bitmap = BitmapFactory.decodeFile(imagePath);
            if (bitmap != null) {
                walk_image.setImageBitmap(bitmap);
            } else {
                Toast.makeText(this, "지도 이미지를 불러오는 데 실패했습니다.", Toast.LENGTH_SHORT).show();
            }
        }

        mEtName.setText(strName);
        mEtTime.setText(strTime);
        mEtStepNumber.setText(strStepNumber);
        mEtMeter.setText(strMeter);
        mEtMemo.setText(strMemo);
    }

    private void setDefaultDate() {
        SimpleDateFormat mFormat = new SimpleDateFormat("yyyy-MM-dd", Locale.getDefault());
        mDate = new Date();
        String strDate = mFormat.format(mDate);
        mEtDate.setText(strDate);
    }

    private void addWalkLog() {
        String strDate = mEtDate.getText().toString();
        String strName = mEtName.getText().toString();
        String strTime = mEtTime.getText().toString();
        String strStepNumber = mEtStepNumber.getText().toString();
        String strMeter = mEtMeter.getText().toString();
        String strMemo = mEtMemo.getText().toString();
        String currentUserEmail = mFirebaseAuth.getCurrentUser().getEmail();

        String mapId = mDatabaseRef.child("WalkAccount").push().getKey();
        DatabaseReference walkLogRef = mDatabaseRef.child("WalkAccount").child(mapId);

        walkLogRef.child("date").setValue(strDate);
        walkLogRef.child("name").setValue(strName);
        walkLogRef.child("time").setValue(strTime);
        walkLogRef.child("stepNumber").setValue(strStepNumber);
        walkLogRef.child("meter").setValue(strMeter);
        walkLogRef.child("memo").setValue(strMemo);
        walkLogRef.child("emailId").setValue(currentUserEmail);

        uploadImageToFirebaseStorage(mapId);
    }

    private void uploadImageToFirebaseStorage(String mapId) {
        if (mImageUri != null) {
            StorageReference imageRef = mFirebaseStorage.getReference().child("WalkAccount/" + mapId + "/image.jpg");
            imageRef.putFile(mImageUri)
                    .addOnSuccessListener(taskSnapshot -> imageRef.getDownloadUrl().addOnSuccessListener(uri -> {
                        mDatabaseRef.child("WalkAccount").child(mapId).child("imgUrl").setValue(uri.toString());
                        Toast.makeText(WalkLogAddActivity.this, "산책 일지가 추가되었습니다.", Toast.LENGTH_SHORT).show();
                        startActivity(new Intent(WalkLogAddActivity.this, MainActivity.class));
                    }))
                    .addOnFailureListener(e -> Toast.makeText(WalkLogAddActivity.this, "이미지 업로드 실패", Toast.LENGTH_SHORT).show());
        } else {
            Toast.makeText(this, "이미지가 선택되지 않았습니다.", Toast.LENGTH_SHORT).show();
        }
    }
}
