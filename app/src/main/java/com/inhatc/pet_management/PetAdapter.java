package com.inhatc.pet_management;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;

import java.util.ArrayList;

public class PetAdapter extends RecyclerView.Adapter<PetAdapter.PetViewHolder> {

    private ArrayList<PetAccount> arrayList;
    private Context context;
    //어댑터에서 액티비티 액션을 가져올 때 context가 필요한데 어댑터에는 context가 없다.
    //선택한 액티비티에 대한 context를 가져올 때 필요하다.

    public PetAdapter(ArrayList<PetAccount> arrayList, Context context) {
        this.arrayList = arrayList;
        this.context = context;
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

    public class PetViewHolder extends RecyclerView.ViewHolder {
        ImageView pet_image;
        TextView pet_name;
        TextView pet_birth;
        TextView pet_species;

        public PetViewHolder(@NonNull View itemView) {
            super(itemView);
            this.pet_image = itemView.findViewById(R.id.pet_image);
            this.pet_name = itemView.findViewById(R.id.pet_name);
            this.pet_birth = itemView.findViewById(R.id.pet_birth);
            this.pet_species= itemView.findViewById(R.id.pet_species);
        }
    }
}
