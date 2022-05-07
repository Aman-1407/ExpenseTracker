package com.example.exp2.Activities;


import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.exp2.R;
import com.example.exp2.model.Data;

import java.util.ArrayList;

public class weekAdapter extends RecyclerView.Adapter<weekAdapter.MyViewHolder> {

    Context context;
    ArrayList<Data> list;

    public weekAdapter (Context context, ArrayList<Data> list) {
        this.context = context;
        this.list = list;
    }

    @NonNull
    @Override
    public weekAdapter.MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(context).inflate(R.layout.expense_recycler_data,parent,false);
        return  new weekAdapter.MyViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull weekAdapter.MyViewHolder holder, int position) {

        Data data = list.get(position);
        holder.setAmount(data.getAmount());
        holder.setType(data.getType());
        holder.setNote(data.getNote());
        holder.setDate(data.getDate());



        holder.itemView.setOnClickListener(view -> {
        });

    }



    @Override
    public int getItemCount() {
        return list.size();
    }

    public static class MyViewHolder extends RecyclerView.ViewHolder {

        View mView;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            mView = itemView;
        }

        void setType(String type) {
            TextView mType = mView.findViewById(R.id.type_txt_expense);
            mType.setText(type);
        }

        void setNote(String note) {

            TextView mNote = mView.findViewById(R.id.note_txt_expense);
            mNote.setText(note);
        }

        void setDate(String date) {
            TextView mDate = mView.findViewById(R.id.date_txt_expense);
            mDate.setText(date);
        }

        void setAmount(int amount) {
            TextView mAmount = mView.findViewById(R.id.amount_txt_expense);
            String st_amount = String.valueOf(amount);
            mAmount.setText(st_amount);
        }
    }
}

