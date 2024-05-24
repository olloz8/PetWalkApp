package com.inhatc.pet_management;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.widget.EditText;
import android.widget.ImageView;


import androidx.appcompat.app.AppCompatActivity;


import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

public class WalkLogAddActivity extends AppCompatActivity {

    String imagePath;
    Date mDate;
    SimpleDateFormat mFormat = new SimpleDateFormat("yyyy-MM-dd", Locale.getDefault());

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

        // 가져온 데이터를 사용하여 UI 업데이트 또는 다른 작업 수행
        EditText tvName = findViewById(R.id.insert_pet_name);
        EditText tvTime = findViewById(R.id.insert_walk_time);
        EditText tvStepNumber = findViewById(R.id.insert_walk_step);
        EditText tvMeter = findViewById(R.id.insert_walk_meter);
        //ImageView mapimg = findViewById(R.id.walklog_photo); // 이미지뷰 추가

        //현재 날짜 가져오기
        EditText tvDate = findViewById(R.id.insert_walk_date);

        mDate = new Date();
        String strDate = mFormat.format(mDate);

        tvName.setText(strName);
        tvTime.setText(strTime);
        tvStepNumber.setText(strStepNumber);
        tvMeter.setText(strMeter);
        tvDate.setText(strDate);

/*        // 이미지 파일 경로로부터 비트맵 로드
        if (imagePath != null) {
            Bitmap bitmap = BitmapFactory.decodeFile(imagePath);
            mapimg.setImageBitmap(bitmap);
        }*/

    }
}