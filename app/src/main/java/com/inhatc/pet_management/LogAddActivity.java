package com.inhatc.pet_management;

import android.content.DialogInterface;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.util.Log;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class LogAddActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_log_add);

  /*      quitCheckBuilder.setPositiveButton("기록", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                AlertDialog.Builder builder = new AlertDialog.Builder(WalkActivity.this);
                View view = LayoutInflater.from(WalkActivity.this).inflate(R.layout.dailog_walk_diary, null, false);
                builder.setView(view);

                final Button BtnSubmit = (Button) view.findViewById(R.id.btn_walk_dialog);
                final EditText editDate = (EditText) view.findViewById(R.id.insert_walk_date);
                final EditText editTime = (EditText) view.findViewById(R.id.insert_walk_time);
                final EditText editStepNumber = (EditText)view.findViewById(R.id.insert_walk_step);
                final EditText editMeter = (EditText)view.findViewById(R.id.insert_walk_meter);
                final EditText editMemo = (EditText)view.findViewById(R.id.insert_walk_memo);
                ivPhoto = (ImageView)view.findViewById(R.id.walklog_photo);

                // SD카드, 앱 내부에 저장된 이미지 불러오기
                if (uploadImage != null) {
                    dialogImagePath = uploadImage.getAbsolutePath();
                }
                Environment.getExternalStorageDirectory().getAbsolutePath()는 SD카드의 절대경로를 구하는 매소드
                //path에 불러올 비트맵파일의 주소명을 초기화 시켜준다.
                Log.d("TAG", dialogImagePath);
                BitmapFactory.Options bo = new BitmapFactory.Options();
                bo.inSampleSize = 2;
                Bitmap bmp = BitmapFactory.decodeFile(dialogImagePath, bo);
                //저장되있던 비트맵 불러온다.
                ImageView imageView = (ImageView)view.findViewById(R.id.walklog_photo);
                imageView.setImageBitmap(bmp);
                // 최종 시간기록을 불러온다.
                editTime.setText(record);
                // 최종 걸음수를 불러온다.
                editStepNumber.setText(String.valueOf(mStepDetector));
                // 최종 meter 이동 거리를 불러온다.
                editMeter.setText(meterResult);
                // 기된 정보를 산책 일지로 넘긴다.
                final AlertDialog addWalkItemDialog = builder.create();


                final AlertDialog dialog = builder.create();*/
    }
}