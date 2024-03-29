package com.inhatc.pet_management;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

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
        btn_register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                SharedPreferences inputId = getSharedPreferences("Login", MODE_PRIVATE);
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

    Button btn_back = (Button)findViewById(R.id.btn_back);
        btn_back.setOnClickListener(new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            Intent intent2 = new Intent(JoinActivity.this,MainActivity.class);
            startActivity(intent2);
            }
        });
    }
}