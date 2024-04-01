package com.inhatc.pet_management;

import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Paint;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Button btn_login = (Button) findViewById(R.id.btn_login);
        btn_login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                EditText et_id = (EditText) findViewById(R.id.et_id);
                EditText et_pass = (EditText) findViewById(R.id.et_pass);

                String ID = et_id.getText().toString();
                String PW = et_pass.getText().toString();

                // 아이디와 비밀번호를 입력하지 않은 경우
                if (ID.isEmpty() || PW.isEmpty()) {
                    Toast.makeText(getApplicationContext(), "아이디와 비밀번호를 입력하세요.", Toast.LENGTH_SHORT).show();
                    return;
                }

                SharedPreferences outId = getSharedPreferences("UserInfo", MODE_PRIVATE);
                String inputId = outId.getString("id", "");

                SharedPreferences outPw = getSharedPreferences("UserInfo", MODE_PRIVATE);
                String inputPw = outPw.getString("pw", "");

                if (ID.equals(inputId) == true && PW.equals(inputPw) == true) {
                    Intent intent = new Intent(MainActivity.this, HomeActivity.class);
                    startActivity(intent);
                } else {
                    Toast.makeText(getApplicationContext(), "아이디 또는 비밀번호 오류입니다.", Toast.LENGTH_SHORT).show();
                }
            }
        });

        Button btn_register = (Button) findViewById(R.id.btn_register);
        btn_register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent2 = new Intent(MainActivity.this, JoinActivity.class);
                startActivity(intent2);
            }
        });
    }
}
