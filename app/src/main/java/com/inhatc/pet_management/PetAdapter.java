package com.inhatc.pet_management;

import android.app.AlertDialog;
import android.content.Context;
import android.util.Log;
import android.view.ContextMenu;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;
import java.util.Dictionary;

public class PetAdapter extends RecyclerView.Adapter<PetAdapter.PetViewHolder> {

    private FirebaseDatabase database;
    private DatabaseReference databaseReference;
    private ArrayList<PetAccount> arrayList;
    private Context context;
    //어댑터에서 액티비티 액션을 가져올 때 context가 필요한데 어댑터에는 context가 없다.
    //선택한 액티비티에 대한 context를 가져올 때 필요하다.

    public PetAdapter(ArrayList<PetAccount> arrayList, Context context) {
        this.arrayList = arrayList;
        this.context = context;
        database = FirebaseDatabase.getInstance();
        databaseReference = database.getReference("pet_management").child("PetAccount");
    }

    @NonNull
    @Override
    //실제 리스트뷰가 어댑터에 연결된 다음에 뷰 홀더를 최초로 만들어낸다.
    public PetViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.pet_item, parent, false);
        PetViewHolder holder = new PetViewHolder(view);
        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull PetViewHolder holder, int position) {
        Glide.with(holder.itemView)
                .load(arrayList.get(position).getImgUrl())
                .into(holder.pet_image);
        Log.d("PetAdapter", "Name: " + arrayList.get(position).getName());
        Log.d("PetAdapter", "Birth: " + arrayList.get(position).getBirth());
        Log.d("PetAdapter", "Species: " + arrayList.get(position).getSpecies());
        holder.pet_name.setText(arrayList.get(position).getName());
        holder.pet_birth.setText(arrayList.get(position).getBirth());
        holder.pet_species.setText(arrayList.get(position).getSpecies());
    }

    @Override
    public int getItemCount() {
        // 삼항 연산자
        return (arrayList != null ? arrayList.size() : 0);
    }

    public class PetViewHolder extends RecyclerView.ViewHolder implements View.OnCreateContextMenuListener {
        ImageView pet_image;
        TextView pet_name;
        TextView pet_birth;
        TextView pet_species;

        public PetViewHolder(@NonNull View itemView) {
            super(itemView);
            this.pet_image = itemView.findViewById(R.id.pet_image);
            this.pet_name = itemView.findViewById(R.id.pet_name);
            this.pet_birth = itemView.findViewById(R.id.pet_birth);
            this.pet_species = itemView.findViewById(R.id.pet_species);

            itemView.setOnCreateContextMenuListener(this);
        }


        //리사이클뷰 편집, 삭제 기능

        @Override
        public void onCreateContextMenu(ContextMenu menu, View itemView, ContextMenu.ContextMenuInfo menuInfo) {

            MenuItem Edit = menu.add(Menu.NONE, 1001, 1, "편집");
            MenuItem Delete = menu.add(Menu.NONE, 1002, 2, "삭제");
            Edit.setOnMenuItemClickListener(onEditMenu);
            Delete.setOnMenuItemClickListener(onEditMenu);
        }

        private final MenuItem.OnMenuItemClickListener onEditMenu = new MenuItem.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(@NonNull MenuItem item) {
                switch (item.getItemId()) {
                    case 1001: // 편집
                        // 편집 다이얼로그 표시
                        showEditDialog();
                        return true;

                    case 1002: // 삭제
                        // Firebase에서 해당 아이템 삭제
                        deleteItem(getAdapterPosition());
                        return true;
                }
                return false;
            }
        };

        // 아이템 편집 다이얼로그 표시
        private void showEditDialog() {
            AlertDialog.Builder builder = new AlertDialog.Builder(context);
            View view = LayoutInflater.from(context).inflate(R.layout.activity_pet_edit, null, false);
            builder.setView(view);

            final ImageView pet_image = view.findViewById(R.id.pet_image);
            final Button pet_edit = view.findViewById(R.id.pet_edit);
            final EditText pet_name = view.findViewById(R.id.pet_name);
            final EditText pet_birth = view.findViewById(R.id.pet_birth);
            final EditText pet_species = view.findViewById(R.id.pet_species);

            String imgUrl = arrayList.get(getAdapterPosition()).getImgUrl();
            Glide.with(context).load(imgUrl).into(pet_image);
            pet_name.setText(arrayList.get(getAdapterPosition()).getName());
            pet_birth.setText(arrayList.get(getAdapterPosition()).getBirth());
            pet_species.setText(arrayList.get(getAdapterPosition()).getSpecies());

            final AlertDialog dialog = builder.create();
            pet_edit.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    // 수정된 정보로 PetAccount 객체 생성
                    String newName = pet_name.getText().toString();
                    String newBirth = pet_birth.getText().toString();
                    String newSpecies = pet_species.getText().toString();
                    String imgUrl = arrayList.get(getAdapterPosition()).getImgUrl();
                    PetAccount editedPet = new PetAccount(newName, newBirth, newSpecies, imgUrl);

                    // Firebase에서 해당 아이템 업데이트
                    updateItem(getAdapterPosition(), editedPet);

                    dialog.dismiss();
                }
            });

            dialog.show();
        }

        // Firebase에서 해당 위치의 아이템 삭제
        private void deleteItem(int position) {
            String key = arrayList.get(position).getKey();
            databaseReference.child(key).removeValue().addOnSuccessListener(new OnSuccessListener<Void>() {
                @Override
                public void onSuccess(Void aVoid) {
                    // 아이템 삭제 후 RecyclerView 업데이트
                    arrayList.remove(position);
                    notifyItemRemoved(position);
                    notifyItemRangeChanged(position, arrayList.size());
                }
            }).addOnFailureListener(new OnFailureListener() {
                @Override
                public void onFailure(@NonNull Exception e) {
                    Log.e("PetAdapter", "Failed to delete item: " + e.getMessage());
                }
            });
        }

        // Firebase에서 해당 위치의 아이템 업데이트
        private void updateItem(int position, PetAccount editedPet) {
            String key = arrayList.get(position).getKey();
            editedPet.setEmailId(FirebaseAuth.getInstance().getCurrentUser().getEmail()); // emailId 설정 추가
            databaseReference.child(key).setValue(editedPet).addOnSuccessListener(new OnSuccessListener<Void>() {
                @Override
                public void onSuccess(Void aVoid) {
                    // 아이템 업데이트 후 RecyclerView 업데이트
                    arrayList.set(position, editedPet);
                    notifyItemChanged(position);
                }
            }).addOnFailureListener(new OnFailureListener() {
                @Override
                public void onFailure(@NonNull Exception e) {
                    Log.e("PetAdapter", "Failed to update item: " + e.getMessage());
                }
            });
        }
    }
}