package com.inhatc.pet_management;

import android.content.Intent;
import android.content.SharedPreferences;
import android.media.Image;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class JoinActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_join);

        EditText et_id = (EditText) findViewById(R.id.et_id);
        EditText et_pass = (EditText) findViewById(R.id.et_pass);
        EditText et_name = (EditText) findViewById(R.id.et_name);
        EditText et_birth = (EditText) findViewById(R.id.et_birth);
        EditText et_email = (EditText) findViewById(R.id.et_email);
        Button btn_register = (Button) findViewById(R.id.btn_register);
        Button btn_idcheck = (Button) findViewById(R.id.btn_idcheck);

        btn_register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                SharedPreferences inputId = getSharedPreferences("UserInfo", MODE_PRIVATE);
                SharedPreferences.Editor editor = inputId.edit();
                editor.putString("id", et_id.getText().toString());
                editor.putString("pw", et_pass.getText().toString());
                editor.putString("name", et_name.getText().toString());
                editor.putString("birth", et_birth.getText().toString());
                editor.putString("email", et_email.getText().toString());
                editor.apply();

                Intent intent = new Intent(JoinActivity.this, MainActivity.class);
                startActivity(intent);
            }
        });

        //아이디 중복 체크
        btn_idcheck.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                SharedPreferences inputId = getSharedPreferences("UserInfo", MODE_PRIVATE);
                String saveId = inputId.getString("id", "");
                String enterId = et_id.getText().toString().trim();

                if (saveId.equals(enterId)) {
                    Toast.makeText(JoinActivity.this, "이미 사용중인 아이디입니다.", Toast.LENGTH_SHORT).show();

                } else {
                    Toast.makeText(JoinActivity.this, "사용 가능한 아이디입니다.", Toast.LENGTH_SHORT).show();


                }
            }
        });

        //뒤로가기
        ImageView btn_back = (ImageView) findViewById(R.id.btn_back);
        btn_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent2 = new Intent(JoinActivity.this, MainActivity.class);
                startActivity(intent2);
            }
        });
    }
}