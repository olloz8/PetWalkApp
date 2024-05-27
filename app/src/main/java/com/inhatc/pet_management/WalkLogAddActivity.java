package com.inhatc.pet_management;

import static android.content.ContentValues.TAG;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

public class WalkLogAddActivity extends AppCompatActivity {

    Date mDate;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_log_add);




        // 인텐트에서 데이터 가져오기
        Intent intent = getIntent();
        String strName = intent.getStringExtra("name");
        String strTime = intent.getStringExtra("time");
        String strStepNumber = intent.getStringExtra("stepNumber");
        String strMeter = intent.getStringExtra("meter");

        String imagePath = intent.getStringExtra("imagePath");

        if (imagePath != null) {
            ImageView imageView = findViewById(R.id.walklog_photo);
            Bitmap bitmap = BitmapFactory.decodeFile(imagePath);
            if (bitmap != null) {
                imageView.setImageBitmap(bitmap);
            } else {
                Toast.makeText(this, "Failed to load map image", Toast.LENGTH_SHORT).show();
            }
        }


        // 가져온 데이터를 사용하여 UI 업데이트 또는 다른 작업 수행
        EditText tvName = findViewById(R.id.insert_pet_name);
        EditText tvTime = findViewById(R.id.insert_walk_time);
        EditText tvStepNumber = findViewById(R.id.insert_walk_step);
        EditText tvMeter = findViewById(R.id.insert_walk_meter);
        EditText tvDate = findViewById(R.id.insert_walk_date);
        //ImageView mapImg = findViewById(R.id.walklog_photo); // 이미지뷰 추가

        //현재 날짜 가져오기
        SimpleDateFormat mFormat = new SimpleDateFormat("yyyy-MM-dd", Locale.getDefault());
        mDate = new Date();
        String strDate = mFormat.format(mDate);

        tvName.setText(strName);
        tvTime.setText(strTime);
        tvStepNumber.setText(strStepNumber);
        tvMeter.setText(strMeter);
        tvDate.setText(strDate);


    }
}