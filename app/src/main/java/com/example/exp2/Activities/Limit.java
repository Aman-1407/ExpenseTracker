package com.example.exp2.Activities;

import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import com.example.exp2.Activities.Expense.MyAdapter2;
import com.example.exp2.Activities.Income.MyAdapter;
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
import org.joda.time.Months;
import org.joda.time.MutableDateTime;
import org.joda.time.Weeks;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.Map;

public class Limit extends AppCompatActivity {

    DatabaseReference database,database2,personal,limit;
    MyAdapter myAdapter;
    MyAdapter2 myAdapter2;
    ArrayList<Data> list;

    private LinearLayout l1,l2,l3,l4;
    TextView t1,t2,t3,t4;
    ImageView i1,i2,i3,i4;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_limit);

        FirebaseAuth mAuth = FirebaseAuth.getInstance();
        FirebaseUser mUser = mAuth.getCurrentUser();
        assert mUser != null;
        String uid = mUser.getUid();
        database = FirebaseDatabase.getInstance().getReference("ExpenseData").child(uid);
        database2 = FirebaseDatabase.getInstance().getReference("IncomeData").child(uid);
        personal = FirebaseDatabase.getInstance().getReference("PieData").child(uid);
        limit = FirebaseDatabase.getInstance().getReference("LimitData").child(uid);
        l1=findViewById(R.id.l1);
        l2=findViewById(R.id.l2);
        l3=findViewById(R.id.l3);
        l4=findViewById(R.id.l4);


        t1=findViewById(R.id.t1);
        t2=findViewById(R.id.t2);
        t3=findViewById(R.id.t3);
        t4=findViewById(R.id.t4);

        i1=findViewById(R.id.i1);


        travelMonth();
        foodMonth();
        entertainmentMonth();
        otherMonth();


        travelLimit();
        foodLimit();
        entertainmentLimit();
        otherLimit();
        limit();


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


    private void otherLimit() {

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


        DatabaseReference database = FirebaseDatabase.getInstance().getReference("LimitData").child(uid);
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
                        t4.setText("\u20B9" + totalAmount);
                    }
                    personal.child("OtherLimit").setValue(totalAmount);

                }

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }
    private void entertainmentLimit() {

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


        DatabaseReference database = FirebaseDatabase.getInstance().getReference("LimitData").child(uid);
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
                        t3.setText("\u20B9" + totalAmount);
                    }
                    personal.child("EntertainmentLimit").setValue(totalAmount);

                }

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {


            }
        });
    }
    private void foodLimit() {

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


        DatabaseReference database = FirebaseDatabase.getInstance().getReference("LimitData").child(uid);
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
                        t2.setText("\u20B9" + totalAmount);
                    }
                    personal.child("FoodLimit").setValue(totalAmount);

                }

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }
    private void travelLimit() {
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


        DatabaseReference database = FirebaseDatabase.getInstance().getReference("LimitData").child(uid);
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
                        t1.setText("\u20B9" + totalAmount);
                    }
                    personal.child("TravelLimit").setValue(totalAmount);

                }

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }


    private void limit(){
        personal.addValueEventListener(new ValueEventListener() {

            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {

                TextView limitview1=findViewById(R.id.travel_limit);
                TextView limitview2=findViewById(R.id.food_limit);
                TextView limitview3=findViewById(R.id.ent_limit);
                TextView limitview4=findViewById(R.id.other_limit);

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
                    int TravelLimit;
                    if (snapshot.hasChild("TravelLimit")) {
                        TravelLimit = Integer.parseInt(snapshot.child("TravelLimit").getValue().toString());
                    } else {
                        TravelLimit = 0;
                    }
                    int FoodLimit;
                    if (snapshot.hasChild("FoodLimit")) {
                        FoodLimit = Integer.parseInt(snapshot.child("FoodLimit").getValue().toString());
                    } else {
                        FoodLimit = 0;
                    }
                    int EntertainmentLimit;
                    if (snapshot.hasChild("EntertainmentLimit")) {
                        EntertainmentLimit = Integer.parseInt(snapshot.child("EntertainmentLimit").getValue().toString());
                    } else {
                        EntertainmentLimit = 0;
                    }

                    int OtherLimit;
                    if (snapshot.hasChild("OtherLimit")) {
                        OtherLimit = Integer.parseInt(snapshot.child("OtherLimit").getValue().toString());
                    } else {
                        OtherLimit = 0;
                    }


                    if(snapshot.hasChild("TravelLimit") && TravelLimit<Travel) {
                        int t1 = Math.abs(TravelLimit - Travel);
                        limitview1.setText("\u20B9" + t1);
                    }else{
                        l1.setVisibility(View.GONE);
                    }
                    if(snapshot.hasChild("FoodLimit") && FoodLimit<Food) {
                        int t2 = Math.abs(FoodLimit - Food);
                        limitview2.setText("\u20B9" + t2);
                    }else{
                        l2.setVisibility(View.GONE);
                    }
                    if(snapshot.hasChild("EntertainmentLimit") && EntertainmentLimit<Entertainment) {
                        int t3 = Math.abs(EntertainmentLimit - Entertainment);
                        limitview3.setText("\u20B9" + t3);
                    }else{
                        l3.setVisibility(View.GONE);
                    }
                    if(snapshot.hasChild("OtherLimit") && OtherLimit<Other) {
                        int t4 = Math.abs(OtherLimit - Other);
                        limitview4.setText("\u20B9" + t4);
                    }else{
                        l4.setVisibility(View.GONE);
                    }
                }
            }
            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

    }
}