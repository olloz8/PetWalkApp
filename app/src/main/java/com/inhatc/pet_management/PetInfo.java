package com.inhatc.pet_management;


import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.io.File;


public class PetInfo extends AppCompatActivity {

    private FirebaseAuth mFirebaseAuth; // 파이어베이스 인증 처리
    private DatabaseReference mDatabaseRef; //실시간 데이터 베이스
    private FirebaseStorage mFirebaseStorage; //파이어베이스 이미지 저장
    private EditText mEtName, mEtBirth, mEtSpecies ; //펫 정보 입력필드
    private Button mBtnPetAdd; //반려동물 추가 버튼


    private ImageView pet_image; //이미지 등록
    private Uri mImageUri;
    private static final int REQUEST_IMAGE_GET = 1;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pet_info);

        mFirebaseAuth = FirebaseAuth.getInstance();
        mDatabaseRef = FirebaseDatabase.getInstance().getReference("pet_management");
        mFirebaseStorage = FirebaseStorage.getInstance();

        mEtName = findViewById(R.id.pet_name);
        mEtBirth = findViewById(R.id.pet_birth);
        mEtSpecies = findViewById(R.id.pet_species);
        mBtnPetAdd = findViewById(R.id.pet_add);
        pet_image = findViewById(R.id.pet_image);

        mBtnPetAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // 반려동물 추가 처리 시작
                String strPetName = mEtName.getText().toString().trim();
                String strPetBirth = mEtBirth.getText().toString().trim();
                String strPetSpecies = mEtSpecies.getText().toString().trim();

                // Firebase Realtime Database에 반려동물 정보 추가
                DatabaseReference petRef = mDatabaseRef.child("PetAccount").push(); // 새로운 키 생성
                petRef.child("name").setValue(strPetName);
                petRef.child("birth").setValue(strPetBirth);
                petRef.child("species").setValue(strPetSpecies);
                petRef.child("uid").setValue(mFirebaseAuth.getCurrentUser().getUid()); // 현재 사용자의 UID 저장

                Toast.makeText(PetInfo.this, "반려동물 정보가 추가되었습니다.", Toast.LENGTH_SHORT).show();

                // 이미지가 선택되었는지 확인 후 Firebase Storage에 업로드
                if (mImageUri != null) {
                    uploadImageToFirebaseStorage(petRef.getKey());
                }
            }

            // Firebase Storage에 이미지 업로드
            private void uploadImageToFirebaseStorage(String petId) {
                StorageReference imageRef = mFirebaseStorage.getReference().child("PetAccount/" + petId + "/image.jpg");
                imageRef.putFile(mImageUri)
                        .addOnSuccessListener(taskSnapshot -> {
                            // 이미지 업로드 성공 시
                            imageRef.getDownloadUrl().addOnSuccessListener(uri -> {
                                // 다운로드 URL을 받아옴
                                mDatabaseRef.child("PetAccount").child(petId).child("imgUrl").setValue(uri.toString());
                                Toast.makeText(PetInfo.this, "이미지가 업로드되었습니다.", Toast.LENGTH_SHORT).show();
                            });
                        })
                        .addOnFailureListener(e -> {
                            // 이미지 업로드 실패 시
                            Toast.makeText(PetInfo.this, "이미지 업로드에 실패했습니다.", Toast.LENGTH_SHORT).show();
                        });
            }
        });

        // ImageView 초기화
        pet_image = findViewById(R.id.pet_image);

        // 갤러리 접근 권한 확인 및 요청
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.READ_EXTERNAL_STORAGE)
                != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this,
                    new String[]{Manifest.permission.READ_EXTERNAL_STORAGE},
                    REQUEST_IMAGE_GET);
        }

        // ImageView 클릭 시 갤러리 열기
        pet_image.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openGallery();
            }
        });
    }

    // 갤러리 열기
    private void openGallery() {
        Intent intent = new Intent(Intent.ACTION_GET_CONTENT);
        intent.setType("image/*");
        if (intent.resolveActivity(getPackageManager()) != null) {
            startActivityForResult(intent, REQUEST_IMAGE_GET);
        }
    }

    // 갤러리 접근 권한 요청 결과 처리
    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == REQUEST_IMAGE_GET) {
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                // 권한이 허용된 경우 갤러리 열기
                openGallery();
            } else {
                Toast.makeText(this, "", Toast.LENGTH_SHORT).show();
            }
        }
    }

    // 갤러리에서 선택한 이미지 처리
    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == REQUEST_IMAGE_GET && resultCode == RESULT_OK) {
            if (data != null) {
                mImageUri = data.getData();
                // 이미지를 ImageView에 설정
                pet_image.setImageURI(mImageUri);
            }
        }

        //뒤로가기
        ImageView btn_back = (ImageView) findViewById(R.id.btn_back);
        btn_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(PetInfo.this, Mypage.class);
                startActivity(intent);
            }
        });
    }
}