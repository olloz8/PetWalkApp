package com.inhatc.pet_management;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class PetActivity extends AppCompatActivity {

    private RecyclerView recyclerView;
    private RecyclerView.Adapter adapter;
    private RecyclerView.LayoutManager layoutManager;
    private ArrayList<PetAccount> arrayList;
    private FirebaseDatabase database;
    private DatabaseReference databaseReference;
    private TextView txt_name;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pet);

        txt_name = findViewById(R.id.txt_name);

        FirebaseAuth mAuth = FirebaseAuth.getInstance();
        FirebaseUser currentUser = mAuth.getCurrentUser();

        if (currentUser != null) {
            String userId = currentUser.getUid();
            DatabaseReference userRef = FirebaseDatabase.getInstance().getReference("pet_management").child("UserAccount").child(userId);
            userRef.addListenerForSingleValueEvent(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                    if (dataSnapshot.exists()) {
                        String username = dataSnapshot.child("name").getValue(String.class);
                        if (username != null) {
                            txt_name.setText(username + "님의 펫");
                        }
                    }
                }

                @Override
                public void onCancelled(@NonNull DatabaseError databaseError) {
                    // 에러 처리
                    Toast.makeText(PetActivity.this, "데이터베이스 오류: " + databaseError.getMessage(), Toast.LENGTH_SHORT).show();
                }
            });

            recyclerView = findViewById(R.id.recyclerView);
            recyclerView.setHasFixedSize(true);
            layoutManager = new LinearLayoutManager(this);
            recyclerView.setLayoutManager(layoutManager);

            arrayList = new ArrayList<>();

            // Firebase 데이터베이스 연동
            database = FirebaseDatabase.getInstance();
            databaseReference = database.getReference("pet_management").child("PetAccount");
            databaseReference.addListenerForSingleValueEvent(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                    arrayList.clear();
                    for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                        PetAccount petAccount = snapshot.getValue(PetAccount.class);
                        if (petAccount != null && petAccount.getEmailId().equals(currentUser.getEmail())) {
                            petAccount.setKey(snapshot.getKey()); // 키 설정
                            arrayList.add(petAccount);
                        }
                    }
                    adapter.notifyDataSetChanged();
                }

                @Override
                public void onCancelled(@NonNull DatabaseError databaseError) {
                    Log.e("PetActivity", String.valueOf(databaseError.toException()));
                }
            });

            adapter = new PetAdapter(arrayList, this);
            recyclerView.setAdapter(adapter);

            //플로팅 버튼
            FloatingActionButton fab = findViewById(R.id.fab);
            fab.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent intent = new Intent(PetActivity.this, PetInfoActivity.class);
                    startActivity(intent);
                }
            });
        }
    }
}