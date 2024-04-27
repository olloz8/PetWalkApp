package com.inhatc.pet_management;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {
    private CardView D1, D2, D3, D4;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        D1 = (CardView) findViewById(R.id.d1);
        D2 = (CardView) findViewById(R.id.d2);
        D3 = (CardView) findViewById(R.id.d3);
        D4 = (CardView) findViewById(R.id.d4);

        D1.setOnClickListener((View.OnClickListener) this);
        D2.setOnClickListener((View.OnClickListener) this);
        D3.setOnClickListener((View.OnClickListener) this);
        D4.setOnClickListener((View.OnClickListener) this);

    }

    @Override
    public void onClick(View v) {
        Intent i;
        int id = v.getId();

        if (id == R.id.d1) {
            i = new Intent(this, PetActivity.class);
            startActivity(i);
        } else if (id == R.id.d2) {
            i = new Intent(this, WalkActivity.class);
            startActivity(i);
        } else if (id == R.id.d3) {
            i = new Intent(this, LogActivity.class);
            startActivity(i);
        } else if (id == R.id.d4) {
            i = new Intent(this, SettingActivity.class);
            startActivity(i);
        }
    }
}