package com.inhatc.pet_management;

import android.os.Bundle;
import android.view.MenuItem;

import androidx.activity.EdgeToEdge;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import com.google.android.material.bottomnavigation.BottomNavigationView;

public class HomeActivity extends AppCompatActivity {

    private BottomNavigationView bottomNavigationView; //바텀 네비게이션 뷰
    private FragmentManager fm;
    private FragmentTransaction ft;
    private Mypage mypage;
    private Record record;
    private Settings settings;
    private Walk walk;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);


        bottomNavigationView = findViewById(R.id.bottomNavi);
        bottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
                int itemId = menuItem.getItemId();
                if (itemId == R.id.action_home) {
                    setFrag(0);
                } else if (itemId == R.id.action_walk) {
                    setFrag(1);
                } else if (itemId == R.id.action_note) {
                    setFrag(2);
                } else if (itemId == R.id.action_settings) {
                    setFrag(3);
                }
                return true;
            }
        });
        mypage = new Mypage();
        record = new Record();
        settings = new Settings();
        walk = new Walk();
        setFrag(0); // 첫 프래그먼트 화면을 무엇으로 지정해줄 것인지 선택.

    }


    //프래그먼트 교체가 일어나는 실행문
    private void setFrag(int n) {
        fm = getSupportFragmentManager();
        ft = fm.beginTransaction();
        switch (n) {
            case 0:
                ft.replace(R.id.main_frame, mypage);
                ft.commit();
                break;
            case 1:
                ft.replace(R.id.main_frame, walk);
                ft.commit();
                break;
            case 2:
                ft.replace(R.id.main_frame, record);
                ft.commit();
                break;
            case 3:
                ft.replace(R.id.main_frame, settings);
                ft.commit();
                break;
        }


    }
}