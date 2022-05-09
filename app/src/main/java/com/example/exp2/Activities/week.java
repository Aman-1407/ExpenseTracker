package com.example.exp2.Activities;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.anychart.AnyChartView;
import com.example.exp2.R;
import com.example.exp2.model.Data;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import org.joda.time.DateTime;
import org.joda.time.MutableDateTime;
import org.joda.time.Weeks;

import java.util.ArrayList;
import java.util.Map;

public class week extends AppCompatActivity {


    RecyclerView recyclerView;
    AnyChartView anyChartView;
    DatabaseReference database,personal;
    com.example.exp2.Activities.weekAdapter weekAdapter;
    ArrayList<Data> list;
    private TextView amountTextview1;
    ImageView p_week;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_week);
        FirebaseAuth mAuth = FirebaseAuth.getInstance();
        FirebaseUser mUser = mAuth.getCurrentUser();
        assert mUser != null;
        String uid = mUser.getUid();
        recyclerView = findViewById(R.id.recycler_week);
        database = FirebaseDatabase.getInstance().getReference("ExpenseData").child(uid);
        personal = FirebaseDatabase.getInstance().getReference("PieData").child(uid);
        anyChartView=findViewById(R.id.pie_daily);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        linearLayoutManager.setReverseLayout(true);
        linearLayoutManager.setStackFromEnd(true);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(linearLayoutManager);

        amountTextview1 = findViewById(R.id.expense_week);
        p_week=findViewById(R.id.p_week);
        anyChartView=findViewById(R.id.pie_daily);

        list = new ArrayList<>();
        weekAdapter = new weekAdapter(this,list);
        recyclerView.setAdapter(weekAdapter);
        readWeek();

        p_week.setOnClickListener(view -> startActivity(new Intent(week.this, pieWeek.class)));
    }

    private void readWeek() {

        MutableDateTime epoch =new MutableDateTime();
        epoch.setDate(0);
        DateTime now =new DateTime();
        Weeks weeks=Weeks.weeksBetween(epoch,now);
        FirebaseAuth mAuth = FirebaseAuth.getInstance();
        FirebaseUser mUser = mAuth.getCurrentUser();
        assert mUser != null;
        String uid = mUser.getUid();
        database = FirebaseDatabase.getInstance().getReference("ExpenseData").child(uid);
        Query query = database.orderByChild("week").equalTo(weeks.getWeeks());
        query.addValueEventListener(new ValueEventListener() {
            @SuppressLint({"NotifyDataSetChanged", "SetTextI18n"})
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                list.clear();
                for (DataSnapshot dataSnapshot: snapshot.getChildren()){
                    Data data = dataSnapshot.getValue(Data.class);
                    list.add(data);
                }

                weekAdapter.notifyDataSetChanged();

                int totalAmount = 0;
                for (DataSnapshot ds : snapshot.getChildren()){
                    Map< String, Object> map = (Map<String, Object>) ds.getValue();
                    assert map != null;
                    Object total = map.get("amount");
                    int pTotal = Integer.parseInt(String.valueOf(total));
                    totalAmount+=pTotal;

                    amountTextview1.setText(""+totalAmount);

                }

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });


    }


}