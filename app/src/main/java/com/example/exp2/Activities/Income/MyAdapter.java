package com.example.exp2.Activities.Income;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.recyclerview.widget.RecyclerView;

import com.example.exp2.R;
import com.example.exp2.model.Data;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import org.joda.time.DateTime;
import org.joda.time.Months;
import org.joda.time.MutableDateTime;
import org.joda.time.Weeks;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.time.Month;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

public class MyAdapter extends RecyclerView.Adapter<MyAdapter.MyViewHolder> {

    Context context;
    ArrayList<Data> list;
    private String id;
    private int amount;
    private String type;
    private String note;

    public MyAdapter (Context context, ArrayList<Data> list) {
        this.context = context;
        this.list = list;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(context).inflate(R.layout.income_recycler_data,parent,false);
        return  new MyViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {

        Data data = list.get(position);
        holder.setAmount(data.getAmount());
        holder.setType(data.getType());
        holder.setNote(data.getNote());
        holder.setDate(data.getDate());

        switch (data.getType()){
            case "Travel":
                holder.imageView.setImageResource(R.drawable.amazon);
                break;
            case "Food":
                holder.imageView.setImageResource(R.drawable.spotify);
                break;
            case "Entertainment":
                holder.imageView.setImageResource(R.drawable.netflix);
                break;
            case "Other":
                holder.imageView.setImageResource(R.drawable.dribble);
                break;
        }



        holder.itemView.setOnClickListener(view -> {
            id=data.getId();
            amount=data.getAmount();
            type=data.getType();
            note=data.getNote();
            updateData();
        });

    }

    private void updateData() {

        AlertDialog.Builder myDialog = new AlertDialog.Builder(context);
        LayoutInflater inflater=LayoutInflater.from(context);
        View myView = inflater.inflate(R.layout.activity_update_income,null);

        myDialog.setView(myView);

        final AlertDialog dialog = myDialog.create();

        final TextView mType = myView.findViewById(R.id.type_update);
        final EditText mAmount = myView.findViewById(R.id.income_update);
        final EditText mNote = myView.findViewById(R.id.note_update);

        mType.setText(type);

        mAmount.setText(String.valueOf(amount));
        mAmount.setSelection(String.valueOf(amount).length());

        mNote.setText(note);
        mNote.setSelection(note.length());

        Button updateBtn  = myView.findViewById(R.id.btn_update);
        Button deleteBtn = myView.findViewById(R.id. btn_cancel_update);

        updateBtn.setOnClickListener(view -> {
            amount = Integer.parseInt(mAmount.getText().toString());
            note = mNote.getText().toString();
            type =mType.getText().toString();
            String mDate= DateFormat.getDateInstance().format(new Date());


            DateFormat dateFormat = new SimpleDateFormat("dd-MM-yyyy");
            Calendar cal = Calendar.getInstance();
            String date = dateFormat.format(cal.getTime());
            MutableDateTime epoch =new MutableDateTime();
            epoch.setDate(0);
            DateTime now =new DateTime();
            Months months= Months.monthsBetween(epoch,now);
            Weeks weeks=Weeks.weeksBetween(epoch,now);

            String typeday=type+date;
            String typeweek=type+weeks.getWeeks();
            String typemonth=type+months.getMonths();

            Data data=new Data(amount,type,typeday,typemonth,typeweek,note,id,date,months.getMonths(),weeks.getWeeks());
            DatabaseReference reference = FirebaseDatabase.getInstance().getReference().child("IncomeData").child(FirebaseAuth.getInstance().getCurrentUser().getUid()
            );
            reference.child(id).setValue(data).addOnCompleteListener(task -> {
                if (task.isSuccessful()){
                    Toast.makeText(context, "Updated successfully", Toast.LENGTH_SHORT).show();
                }else {
                    Toast.makeText(context, "failed " +task.getException(), Toast.LENGTH_SHORT).show();
                }
            });
            dialog.dismiss();
        });

        deleteBtn.setOnClickListener(view -> {

            DatabaseReference reference = FirebaseDatabase.getInstance().getReference().child("IncomeData").child(FirebaseAuth.getInstance().getCurrentUser().getUid()
            );
            reference.child(id).removeValue().addOnCompleteListener(task -> {
                if (task.isSuccessful()){
                    Toast.makeText(context, "Deleted successfully", Toast.LENGTH_SHORT).show();
                }else {
                    Toast.makeText(context, "failed to delete " +task.getException(), Toast.LENGTH_SHORT).show();
                }
            });
            dialog.dismiss();

        });
        dialog.show();

    }


    @Override
    public int getItemCount() {
        return list.size();
    }

    public static class MyViewHolder extends RecyclerView.ViewHolder {

        View mView;
        public ImageView imageView;


        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            mView = itemView;
            imageView=itemView.findViewById(R.id.imageview2);
        }

        void setType(String type) {
            TextView mType = mView.findViewById(R.id.type_txt_income);
            mType.setText(type);
        }

        void setNote(String note) {

            TextView mNote = mView.findViewById(R.id.note_txt_income);
            mNote.setText(note);
        }

        void setDate(String date) {
            TextView mDate = mView.findViewById(R.id.date_txt_income);
            mDate.setText(date);
        }

        void setAmount(int amount) {
            TextView mAmount = mView.findViewById(R.id.amount_txt_income);
            String st_amount = String.valueOf(amount);
            mAmount.setText(st_amount);
        }
    }
}
