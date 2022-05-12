package com.example.exp2.Activities;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

import com.anychart.AnyChart;
import com.anychart.AnyChartView;
import com.anychart.chart.common.dataentry.DataEntry;
import com.anychart.chart.common.dataentry.ValueDataEntry;
import com.anychart.enums.Align;
import com.anychart.enums.LegendLayout;
import com.example.exp2.R;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import org.joda.time.MutableDateTime;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Map;

public class pieDaily extends AppCompatActivity {

    DatabaseReference database,personal;
    AnyChartView anyChartView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pie_daily);

        FirebaseAuth mAuth = FirebaseAuth.getInstance();
        FirebaseUser mUser = mAuth.getCurrentUser();
        assert mUser != null;
        String uid = mUser.getUid();
        database = FirebaseDatabase.getInstance().getReference("ExpenseData").child(uid);
        personal = FirebaseDatabase.getInstance().getReference("PieData").child(uid);
        anyChartView=findViewById(R.id.pie_daily);

        travelDate();
        foodData();
        entertainmentData();
        otherData();
        graphDay();

    }
    private void otherData() {

        FirebaseAuth mAuth = FirebaseAuth.getInstance();
        FirebaseUser mUser = mAuth.getCurrentUser();

        DateFormat dateFormat = new SimpleDateFormat("dd-MM-yyyy");
        Calendar cal = Calendar.getInstance();
        String date = dateFormat.format(cal.getTime());
        MutableDateTime epoch =new MutableDateTime();
        epoch.setDate(0);

        assert mUser != null;
        String uid = mUser.getUid();

        String typeday="Other"+date;


        DatabaseReference database = FirebaseDatabase.getInstance().getReference("ExpenseData").child(uid);
        Query query = database.orderByChild("typeday").equalTo(typeday);
        query.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {

                if(snapshot.exists()){
                    int totalAmount = 0;
                    for (DataSnapshot ds : snapshot.getChildren()){
                        Map< String, Object> map = (Map<String, Object>) ds.getValue();
                        Object total = map.get("amount");
                        int pTotal = Integer.parseInt(String.valueOf(total));
                        totalAmount+=pTotal;
                    }
                    personal.child("OtherDate").setValue(totalAmount);

                }

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {


            }
        });
    }
    private void entertainmentData() {

        FirebaseAuth mAuth = FirebaseAuth.getInstance();
        FirebaseUser mUser = mAuth.getCurrentUser();

        DateFormat dateFormat = new SimpleDateFormat("dd-MM-yyyy");
        Calendar cal = Calendar.getInstance();
        String date = dateFormat.format(cal.getTime());
        MutableDateTime epoch =new MutableDateTime();
        epoch.setDate(0);

        assert mUser != null;
        String uid = mUser.getUid();

        String typeday="Entertainment"+date;
        DatabaseReference database = FirebaseDatabase.getInstance().getReference("ExpenseData").child(uid);
        Query query = database.orderByChild("typeday").equalTo(typeday);
        query.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {

                if(snapshot.exists()){
                    int totalAmount = 0;
                    for (DataSnapshot ds : snapshot.getChildren()){
                        Map< String, Object> map = (Map<String, Object>) ds.getValue();
                        Object total = map.get("amount");
                        int pTotal = Integer.parseInt(String.valueOf(total));
                        totalAmount+=pTotal;
                    }
                    personal.child("EntertainmentDate").setValue(totalAmount);

                }

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {


            }
        });
    }
    private void foodData() {

        FirebaseAuth mAuth = FirebaseAuth.getInstance();
        FirebaseUser mUser = mAuth.getCurrentUser();

        DateFormat dateFormat = new SimpleDateFormat("dd-MM-yyyy");
        Calendar cal = Calendar.getInstance();
        String date = dateFormat.format(cal.getTime());
        MutableDateTime epoch =new MutableDateTime();
        epoch.setDate(0);

        assert mUser != null;
        String uid = mUser.getUid();

        String typeday="Food"+date;


        DatabaseReference database = FirebaseDatabase.getInstance().getReference("ExpenseData").child(uid);
        Query query = database.orderByChild("typeday").equalTo(typeday);
        query.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {

                if(snapshot.exists()){
                    int totalAmount = 0;
                    for (DataSnapshot ds : snapshot.getChildren()){
                        Map< String, Object> map = (Map<String, Object>) ds.getValue();
                        Object total = map.get("amount");
                        int pTotal = Integer.parseInt(String.valueOf(total));
                        totalAmount+=pTotal;
                    }
                    personal.child("FoodDate").setValue(totalAmount);

                }

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }
    private void travelDate() {


        FirebaseAuth mAuth = FirebaseAuth.getInstance();
        FirebaseUser mUser = mAuth.getCurrentUser();

        DateFormat dateFormat = new SimpleDateFormat("dd-MM-yyyy");
        Calendar cal = Calendar.getInstance();
        String date = dateFormat.format(cal.getTime());
        MutableDateTime epoch =new MutableDateTime();
        epoch.setDate(0);

        assert mUser != null;
        String uid = mUser.getUid();

        String typeday="Travel"+date;


        DatabaseReference database = FirebaseDatabase.getInstance().getReference("ExpenseData").child(uid);
        Query query = database.orderByChild("typeday").equalTo(typeday);
        query.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {

                if(snapshot.exists()){
                    int totalAmount = 0;
                    for (DataSnapshot ds : snapshot.getChildren()){
                        Map< String, Object> map = (Map<String, Object>) ds.getValue();
                        Object total = map.get("amount");
                        int pTotal = Integer.parseInt(String.valueOf(total));
                        totalAmount+=pTotal;
                    }
                    personal.child("TravelDate").setValue(totalAmount);

                }

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });


    }
    private void graphDay(){
        personal.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if (snapshot.exists()) {
                    int Travel;
                    if (snapshot.hasChild("TravelDate")) {
                        Travel = Integer.parseInt(snapshot.child("TravelDate").getValue().toString());
                    } else {
                        Travel = 0;
                    }
                    int Food;
                    if (snapshot.hasChild("FoodDate")) {
                        Food = Integer.parseInt(snapshot.child("FoodDate").getValue().toString());
                    } else {
                        Food = 0;
                    }
                    int Entertainment;
                    if (snapshot.hasChild("EntertainmentDate")) {
                        Entertainment = Integer.parseInt(snapshot.child("EntertainmentDate").getValue().toString());
                    } else {
                        Entertainment = 0;
                    }

                    int Other;
                    if (snapshot.hasChild("OtherDate")) {
                        Other = Integer.parseInt(snapshot.child("OtherDate").getValue().toString());
                    } else {
                        Other = 0;
                    }

                    com.anychart.charts.Pie pie= AnyChart.pie();
                    List<DataEntry> data =new ArrayList<>();
                    data.add(new ValueDataEntry("Travel",Travel));
                    data.add(new ValueDataEntry("Food",Food));
                    data.add(new ValueDataEntry("Entertainment",Entertainment));
                    data.add(new ValueDataEntry("Other",Other));

                    pie.data(data);
                    pie.title("Daily Analysis");
                    pie.labels().position("outside");
                    pie.legend().title().enabled(true);
                    pie.legend().title()
                            .text("Items Spent on")
                            .padding(0d,0d,10d,0d);

                    pie.legend()
                            .position("center-bottom")
                            .itemsLayout(LegendLayout.HORIZONTAL)
                            .align(Align.CENTER);

                    anyChartView.setChart(pie);

                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

    }
}