package com.example.exp2.Activities;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.Toast;

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

import org.joda.time.DateTime;
import org.joda.time.Months;
import org.joda.time.MutableDateTime;
import org.joda.time.Weeks;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Map;

public class pieMonth extends AppCompatActivity {

    DatabaseReference database,personal;
    AnyChartView anyChartView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pie_month);
        FirebaseAuth mAuth = FirebaseAuth.getInstance();
        FirebaseUser mUser = mAuth.getCurrentUser();
        assert mUser != null;
        String uid = mUser.getUid();
        database = FirebaseDatabase.getInstance().getReference("ExpenseData").child(uid);
        personal = FirebaseDatabase.getInstance().getReference("PieData").child(uid);
        anyChartView=findViewById(R.id.pie_daily);
        travelMonth();
        foodMonth();
        entertainmentMonth();
        otherMonth();
        graphMonth();


    }

    private void otherMonth() {

        FirebaseAuth mAuth = FirebaseAuth.getInstance();
        FirebaseUser mUser = mAuth.getCurrentUser();

        DateFormat dateFormat = new SimpleDateFormat("dd-MM-yyyy");
        Calendar cal = Calendar.getInstance();
        String date = dateFormat.format(cal.getTime());
        MutableDateTime epoch =new MutableDateTime();
        epoch.setDate(0);
        DateTime now =new DateTime();
        Weeks weeks=Weeks.weeksBetween(epoch,now);
        Months months= Months.monthsBetween(epoch,now);

        assert mUser != null;
        String uid = mUser.getUid();

        String typemonth="Other"+months.getMonths();


        DatabaseReference database = FirebaseDatabase.getInstance().getReference("ExpenseData").child(uid);
        Query query = database.orderByChild("typemonth").equalTo(typemonth);
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
                    personal.child("OtherMonth").setValue(totalAmount);

                }

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }
    private void entertainmentMonth() {

        FirebaseAuth mAuth = FirebaseAuth.getInstance();
        FirebaseUser mUser = mAuth.getCurrentUser();

        DateFormat dateFormat = new SimpleDateFormat("dd-MM-yyyy");
        Calendar cal = Calendar.getInstance();
        String date = dateFormat.format(cal.getTime());
        MutableDateTime epoch =new MutableDateTime();
        epoch.setDate(0);
        DateTime now =new DateTime();
        Weeks weeks=Weeks.weeksBetween(epoch,now);
        Months months= Months.monthsBetween(epoch,now);

        assert mUser != null;
        String uid = mUser.getUid();

        String typemonth="Entertainment"+months.getMonths();


        DatabaseReference database = FirebaseDatabase.getInstance().getReference("ExpenseData").child(uid);
        Query query = database.orderByChild("typemonth").equalTo(typemonth);
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
                    personal.child("EntertainmentMonth").setValue(totalAmount);

                }

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {


            }
        });
    }
    private void foodMonth() {

        FirebaseAuth mAuth = FirebaseAuth.getInstance();
        FirebaseUser mUser = mAuth.getCurrentUser();

        DateFormat dateFormat = new SimpleDateFormat("dd-MM-yyyy");
        Calendar cal = Calendar.getInstance();
        String date = dateFormat.format(cal.getTime());
        MutableDateTime epoch =new MutableDateTime();
        epoch.setDate(0);
        DateTime now =new DateTime();
        Weeks weeks=Weeks.weeksBetween(epoch,now);
        Months months= Months.monthsBetween(epoch,now);

        assert mUser != null;
        String uid = mUser.getUid();

        String typemonth="Food"+months.getMonths();


        DatabaseReference database = FirebaseDatabase.getInstance().getReference("ExpenseData").child(uid);
        Query query = database.orderByChild("typemonth").equalTo(typemonth);
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
                    personal.child("FoodMonth").setValue(totalAmount);

                }

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }
    private void travelMonth() {
        FirebaseAuth mAuth = FirebaseAuth.getInstance();
        FirebaseUser mUser = mAuth.getCurrentUser();

        DateFormat dateFormat = new SimpleDateFormat("dd-MM-yyyy");
        Calendar cal = Calendar.getInstance();
        String date = dateFormat.format(cal.getTime());
        MutableDateTime epoch =new MutableDateTime();
        epoch.setDate(0);
        DateTime now =new DateTime();
        Weeks weeks=Weeks.weeksBetween(epoch,now);
        Months months= Months.monthsBetween(epoch,now);

        assert mUser != null;
        String uid = mUser.getUid();

        String typemonth="Travel"+months.getMonths();


        DatabaseReference database = FirebaseDatabase.getInstance().getReference("ExpenseData").child(uid);
        Query query = database.orderByChild("typemonth").equalTo(typemonth);
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
                    personal.child("TravelMonth").setValue(totalAmount);

                }

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }

    private void graphMonth(){
        personal.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if (snapshot.exists()) {
                    int Travel;
                    if (snapshot.hasChild("TravelMonth")) {
                        Travel = Integer.parseInt(snapshot.child("TravelMonth").getValue().toString());
                    } else {
                        Travel = 0;
                    }
                    int Food;
                    if (snapshot.hasChild("FoodMonth")) {
                        Food = Integer.parseInt(snapshot.child("FoodMonth").getValue().toString());
                    } else {
                        Food = 0;
                    }
                    int Entertainment;
                    if (snapshot.hasChild("EntertainmentMonth")) {
                        Entertainment = Integer.parseInt(snapshot.child("EntertainmentMonth").getValue().toString());
                    } else {
                        Entertainment = 0;
                    }

                    int Other;
                    if (snapshot.hasChild("OtherMonth")) {
                        Other = Integer.parseInt(snapshot.child("OtherMonth").getValue().toString());
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
                    pie.title("Monthly Analysis");
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