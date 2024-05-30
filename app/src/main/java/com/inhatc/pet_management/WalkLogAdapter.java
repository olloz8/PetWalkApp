package com.inhatc.pet_management;

import android.content.Context;
import android.util.Log;
import android.view.ContextMenu;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;

public class WalkLogAdapter extends RecyclerView.Adapter<WalkLogAdapter.WalkLogViewHolder> {

    private ArrayList<WalkAccount> arrayList;
    private Context context;

    public WalkLogAdapter(ArrayList<WalkAccount> arrayList, Context context) {
        this.arrayList = arrayList;
        this.context = context;
    }

    @NonNull
    @Override
    public WalkLogViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.log_item, parent, false);
        return new WalkLogViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull WalkLogViewHolder holder, int position) {
        WalkAccount walkAccount = arrayList.get(position);

        Glide.with(holder.itemView)
                .load(walkAccount.getImgUrl())
                .into(holder.image_walk_diary);

        holder.text_walk_date.setText(walkAccount.getDate());
        holder.text_pet_name.setText(walkAccount.getName());
        holder.text_walk_time.setText(walkAccount.getTime());
        holder.text_walk_step.setText(walkAccount.getStepNumber());
        holder.text_walk_meter.setText(walkAccount.getMeter());
        holder.text_walk_memo.setText(walkAccount.getMemo());
    }

    @Override
    public int getItemCount() {
        return (arrayList != null ? arrayList.size() : 0);
    }

    public class WalkLogViewHolder extends RecyclerView.ViewHolder implements View.OnCreateContextMenuListener {
        ImageView image_walk_diary;
        TextView text_walk_date;
        TextView text_pet_name;
        TextView text_walk_time;
        TextView text_walk_meter;
        TextView text_walk_step;
        TextView text_walk_memo;

        public WalkLogViewHolder(@NonNull View itemView) {
            super(itemView);
            this.image_walk_diary = itemView.findViewById(R.id.image_walk_diary);
            this.text_walk_date = itemView.findViewById(R.id.text_walk_date);
            this.text_pet_name = itemView.findViewById(R.id.text_pet_name);
            this.text_walk_time = itemView.findViewById(R.id.text_walk_time);
            this.text_walk_meter = itemView.findViewById(R.id.text_walk_meter);
            this.text_walk_step = itemView.findViewById(R.id.text_walk_step);
            this.text_walk_memo = itemView.findViewById(R.id.text_walk_memo);

            itemView.setOnCreateContextMenuListener(this);
        }

        @Override
        public void onCreateContextMenu(ContextMenu menu, View v, ContextMenu.ContextMenuInfo menuInfo) {
            MenuItem Edit = menu.add(Menu.NONE, 1001, 1, "편집");
            MenuItem Delete = menu.add(Menu.NONE, 1002, 2, "삭제");
            Edit.setOnMenuItemClickListener(onEditMenu);
            Delete.setOnMenuItemClickListener(onEditMenu);
        }

        private final MenuItem.OnMenuItemClickListener onEditMenu = new MenuItem.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem item) {
                switch (item.getItemId()) {
                    case 1001:
                        showEditDialog();
                        return true;

                    case 1002:
                        deleteItem(getAdapterPosition());
                        return true;
                }
                return false;
            }
        };

        private void showEditDialog() {
            AlertDialog.Builder builder = new AlertDialog.Builder(context);
            View view = LayoutInflater.from(context).inflate(R.layout.activity_log_edit, null, false);
            builder.setView(view);

            final ImageView walk_image = view.findViewById(R.id.walklog_photo);
            final TextView walk_edit = view.findViewById(R.id.btn_walk_edit);
            final TextView walk_date = view.findViewById(R.id.insert_walk_date);
            final TextView pet_name = view.findViewById(R.id.insert_pet_name);
            final TextView walk_time = view.findViewById(R.id.insert_walk_time);
            final TextView walk_step = view.findViewById(R.id.insert_walk_step);
            final TextView walk_meter = view.findViewById(R.id.insert_walk_meter);
            final TextView walk_memo = view.findViewById(R.id.insert_walk_memo);

            WalkAccount walkAccount = arrayList.get(getAdapterPosition());
            Glide.with(context).load(walkAccount.getImgUrl()).into(walk_image);
            pet_name.setText(walkAccount.getName());
            walk_date.setText(walkAccount.getDate());
            walk_time.setText(walkAccount.getTime());
            walk_step.setText(walkAccount.getStepNumber());
            walk_meter.setText(walkAccount.getMeter());
            walk_memo.setText(walkAccount.getMemo());

            final AlertDialog dialog = builder.create();
            walk_edit.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    String newWalkDate = walk_date.getText().toString();
                    String newWalkTime = walk_time.getText().toString();
                    String newWalkStep = walk_step.getText().toString();
                    String newWalkMeter = walk_meter.getText().toString();
                    String newWalkMemo = walk_memo.getText().toString();
                    String newName = pet_name.getText().toString();
                    String imgUrl = walkAccount.getImgUrl();
                    WalkAccount editedWalkLog = new WalkAccount(newWalkDate, newWalkTime, newWalkStep, newWalkMeter, newWalkMemo, newName, imgUrl);

                    updateItem(getAdapterPosition(), editedWalkLog);

                    dialog.dismiss();
                }
            });

            dialog.show();
        }

        private void deleteItem(int position) {
            String key = arrayList.get(position).getKey();
            DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference("pet_management").child("WalkAccount");
            databaseReference.child(key).removeValue().addOnSuccessListener(aVoid -> {
                arrayList.remove(position);
                notifyItemRemoved(position);
                notifyItemRangeChanged(position, arrayList.size());
            }).addOnFailureListener(e -> Log.e("WalkLogAdapter", "Failed to delete item: " + e.getMessage()));
        }

        private void updateItem(int position, WalkAccount editedWalk) {
            String key = arrayList.get(position).getKey();
            editedWalk.setEmailId(FirebaseAuth.getInstance().getCurrentUser().getEmail());
            DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference("pet_management").child("WalkAccount");
            databaseReference.child(key).setValue(editedWalk).addOnSuccessListener(aVoid -> {
                arrayList.set(position, editedWalk);
                notifyItemChanged(position);
            }).addOnFailureListener(e -> Log.e("WalkLogAdapter", "Failed to update item: " + e.getMessage()));
        }
    }
}
