package com.inhatc.pet_management;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.Button;
import android.widget.EditText;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class WalkLogAddActivity extends AppCompatActivity {

    private int mStepDetector;
    private String meterResult = null;
    private String record = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_log_add);

        // 인텐트에서 데이터 가져오기
        Intent intent = getIntent();
        String strTime = intent.getStringExtra("time"); // 산책 시간
        String strStepNumber = intent.getStringExtra("stepNumber"); // 걸음 수 또는 칼로리
        String strMeter = intent.getStringExtra("meter"); // 이동 거리

        // 가져온 데이터를 사용하여 UI 업데이트 또는 다른 작업 수행
        EditText tvTime = findViewById(R.id.insert_walk_time);
        EditText tvStepNumber = findViewById(R.id.insert_walk_step);
        EditText tvMeter = findViewById(R.id.insert_walk_meter);

        tvTime.setText(strTime);
        tvStepNumber.setText(strStepNumber);
        tvMeter.setText(strMeter);


    }
}