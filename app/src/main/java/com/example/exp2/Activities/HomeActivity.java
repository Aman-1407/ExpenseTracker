package com.example.exp2.Activities;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import com.example.exp2.Activities.Expense.ExpenseActivity;
import com.example.exp2.Activities.Expense.MyAdapter2;
import com.example.exp2.Activities.Income.IncomeActivity;
import com.example.exp2.Activities.Income.MyAdapter;
import com.example.exp2.R;
import com.example.exp2.databinding.ActivityHomeBinding;
import com.example.exp2.model.Data;
import com.example.exp2.model.User;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
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

public class HomeActivity extends AppCompatActivity {
    ActivityHomeBinding binding;
    DatabaseReference database,database2,personal;
    MyAdapter myAdapter;
    MyAdapter2 myAdapter2;
    weekAdapter weekAdapter;
    ArrayList<Data> list;
    TextView amountTextview1,amountTextview2,txt_today,txt_week,txt_month,balance;
    ImageView profile,inc,exe,today,week,month;

    FloatingActionButton mAddFab, mAddIncomeFab, mAddExpenseFab;
    TextView addIncomeText, addExpenseText;
    Boolean isAllFabVisible;

    @SuppressLint("NonConstantResourceId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        binding = ActivityHomeBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
//        replaceFragment(new HomeFragment());


        mAddFab = findViewById(R.id.add_fab);
        mAddIncomeFab = findViewById(R.id.add_income_fab);
        mAddExpenseFab = findViewById(R.id.add_expense_fab);

        addIncomeText = findViewById(R.id.add_income_text);
        addExpenseText = findViewById(R.id.add_expense_text);

        mAddIncomeFab.setVisibility(View.GONE);
        mAddExpenseFab.setVisibility(View.GONE);
        addIncomeText.setVisibility(View.GONE);
        addExpenseText.setVisibility(View.GONE);

        isAllFabVisible = false;
        FirebaseAuth mAuth = FirebaseAuth.getInstance();
        FirebaseUser mUser = mAuth.getCurrentUser();
        assert mUser != null;
        String uid = mUser.getUid();
        database = FirebaseDatabase.getInstance().getReference("IncomeData").child(uid);
        database2 = FirebaseDatabase.getInstance().getReference("ExpenseData").child(uid);
        personal = FirebaseDatabase.getInstance().getReference("PieData").child(uid);
        DatabaseReference reference = FirebaseDatabase.getInstance().getReference("Users");

        DateFormat dateFormat = new SimpleDateFormat("dd-MM-yyyy");
        Calendar cal = Calendar.getInstance();
        String date = dateFormat.format(cal.getTime());
        MutableDateTime epoch = new MutableDateTime();
        epoch.setDate(0);
        DateTime now = new DateTime();
        Weeks weeks=Weeks.weeksBetween(epoch,now);
        Months months= Months.monthsBetween(epoch,now);

        final TextView greetingTextView = (TextView) findViewById(R.id.welcome);
        amountTextview1 = findViewById(R.id.income_home);
        amountTextview2 = findViewById(R.id.expense_home);
        inc = findViewById(R.id.img_inc);
        exe = findViewById(R.id.img_exe);
        today = findViewById(R.id.img_today);
        week = findViewById(R.id.img_week);
        month = findViewById(R.id.img_month);
        balance = findViewById(R.id.balance);
        txt_today=findViewById(R.id.txt_today);
        txt_week=findViewById(R.id.txt_week);
        txt_month=findViewById(R.id.txt_month);
        list = new ArrayList<>();
        myAdapter = new MyAdapter(this, list);
        myAdapter2 = new MyAdapter2(this, list);
        weekAdapter=new weekAdapter(this,list);
        profile=findViewById(R.id.p_img);

        Query query = database2.orderByChild("date").equalTo(date);
        Query query2 = database2.orderByChild("week").equalTo(weeks.getWeeks());
        Query query3 = database2.orderByChild("month").equalTo(months.getMonths());

        reference.child(uid).addListenerForSingleValueEvent(new ValueEventListener() {
            @SuppressLint("SetTextI18n")
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                User UserProfile = snapshot.getValue(User.class);
                if (UserProfile != null) {
                    String fullname = UserProfile.fullname;
                    greetingTextView.setText(""+ fullname);
                }

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {


            }
        });

        mAddFab.setOnClickListener(view1 -> {
            if (!isAllFabVisible) {
                mAddIncomeFab.show();
                mAddExpenseFab.show();
                addExpenseText.setVisibility(View.VISIBLE);
                addIncomeText.setVisibility(View.VISIBLE);

                isAllFabVisible = true;
            } else {

                mAddIncomeFab.hide();
                mAddExpenseFab.hide();
                addExpenseText.setVisibility(View.GONE);
                addIncomeText.setVisibility(View.GONE);

                isAllFabVisible = false;
            }
        });

        mAddIncomeFab.setOnClickListener(view13 -> showIncomeDialog());

        mAddExpenseFab.setOnClickListener(view12 -> showExpenseDialog());


        database.addValueEventListener(new ValueEventListener() {
            @SuppressLint({"NotifyDataSetChanged", "SetTextI18n"})
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                list.clear();
                for (DataSnapshot dataSnapshot : snapshot.getChildren()) {

                    Data data = dataSnapshot.getValue(Data.class);
                    list.add(data);
                }
                myAdapter.notifyDataSetChanged();

                int totalAmount = 0;
                for (DataSnapshot ds : snapshot.getChildren()) {
                    Map<String, Object> map = (Map<String, Object>) ds.getValue();
                    assert map != null;
                    Object total = map.get("amount");
                    int pTotal = Integer.parseInt(String.valueOf(total));
                    totalAmount += pTotal;
                    amountTextview1.setText("\u20B9" + totalAmount);
                }
                personal.child("Income").setValue(totalAmount);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }

        });

        database2.addValueEventListener(new ValueEventListener() {
            @SuppressLint({"NotifyDataSetChanged", "SetTextI18n"})
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                list.clear();
                for (DataSnapshot dataSnapshot : snapshot.getChildren()) {

                    Data data = dataSnapshot.getValue(Data.class);
                    list.add(data);
                }
                myAdapter2.notifyDataSetChanged();

                int totalAmount = 0;
                for (DataSnapshot ds : snapshot.getChildren()) {
                    Map<String, Object> map = (Map<String, Object>) ds.getValue();
                    assert map != null;
                    Object total = map.get("amount");
                    int pTotal = Integer.parseInt(String.valueOf(total));
                    totalAmount += pTotal;
                    amountTextview2.setText("\u20B9" + totalAmount);
                }
                personal.child("Expense").setValue(totalAmount);
            }


            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });


        personal.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                int income;
                if(snapshot.hasChild("Income")) {
                    income = Integer.parseInt(snapshot.child("Income").getValue().toString());
                }else{
                    income=0;
                }
                int expense;
                if(snapshot.hasChild("Expense")) {
                    expense = Integer.parseInt(snapshot.child("Expense").getValue().toString());
                }else{
                    expense=0;
                }
                int saving = income - expense;
                balance.setText("\u20B9" + saving);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

        query.addValueEventListener(new ValueEventListener() {
            @SuppressLint({"NotifyDataSetChanged", "SetTextI18n"})
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                list.clear();
                for (DataSnapshot dataSnapshot : snapshot.getChildren()) {
                    Data data = dataSnapshot.getValue(Data.class);
                    list.add(data);
                }

                weekAdapter.notifyDataSetChanged();

                int totalAmount = 0;
                for (DataSnapshot ds : snapshot.getChildren()) {
                    Map<String, Object> map = (Map<String, Object>) ds.getValue();
                    Object total = map.get("amount");
                    int pTotal = Integer.parseInt(String.valueOf(total));
                    totalAmount += pTotal;

                    txt_today.setText("" + totalAmount);

                }

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
        query2.addValueEventListener(new ValueEventListener() {
            @SuppressLint({"NotifyDataSetChanged", "SetTextI18n"})
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                list.clear();
                for (DataSnapshot dataSnapshot : snapshot.getChildren()) {
                    Data data = dataSnapshot.getValue(Data.class);
                    list.add(data);
                }

                weekAdapter.notifyDataSetChanged();

                int totalAmount = 0;
                for (DataSnapshot ds : snapshot.getChildren()) {
                    Map<String, Object> map = (Map<String, Object>) ds.getValue();
                    Object total = map.get("amount");
                    int pTotal = Integer.parseInt(String.valueOf(total));
                    totalAmount += pTotal;

                    txt_week.setText("" + totalAmount);

                }

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
        query3.addValueEventListener(new ValueEventListener() {
            @SuppressLint({"NotifyDataSetChanged", "SetTextI18n"})
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                list.clear();
                for (DataSnapshot dataSnapshot : snapshot.getChildren()) {
                    Data data = dataSnapshot.getValue(Data.class);
                    list.add(data);
                }

                weekAdapter.notifyDataSetChanged();

                int totalAmount = 0;
                for (DataSnapshot ds : snapshot.getChildren()) {
                    Map<String, Object> map = (Map<String, Object>) ds.getValue();
                    Object total = map.get("amount");
                    int pTotal = Integer.parseInt(String.valueOf(total));
                    totalAmount += pTotal;

                    txt_month.setText("" + totalAmount);

                }

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });


        inc.setOnClickListener(view -> startActivity(new Intent(HomeActivity.this, IncomeActivity.class)));
        exe.setOnClickListener(view -> startActivity(new Intent(HomeActivity.this, ExpenseActivity.class)));
        today.setOnClickListener(view -> startActivity(new Intent(HomeActivity.this, Daily.class)));
        week.setOnClickListener(view -> startActivity(new Intent(HomeActivity.this, week.class)));
        month.setOnClickListener(view -> startActivity(new Intent(HomeActivity.this, month.class)));
        profile.setOnClickListener(view -> startActivity(new Intent(HomeActivity.this, ProfileActivity.class)));

    }

    private void showIncomeDialog() {
    AlertDialog.Builder builder = new AlertDialog.Builder(this);

    final View customLayout = getLayoutInflater().inflate(R.layout.activity_add_income, null);
    EditText et_income = customLayout.findViewById(R.id.etIncomeAmount);

    Button btn_save = customLayout.findViewById(R.id.btnSaveIncome);
    Button btn_cancel = customLayout.findViewById(R.id.btn_cancel_income);

    final Spinner itemSpinner = customLayout.findViewById(R.id.spinner_income);
    ArrayAdapter<String> itemsAdapter = new ArrayAdapter<>(this,
            android.R.layout.simple_list_item_1, getResources().getStringArray(R.array.items));
    itemsAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
    itemSpinner.setAdapter(itemsAdapter);

    builder.setView(customLayout);
    AlertDialog alertDialog = builder.create();

    alertDialog.show();

    btn_cancel.setOnClickListener(v -> alertDialog.dismiss());

    btn_save.setOnClickListener(v -> {
        String amount = et_income.getText().toString();
        String type = itemSpinner.getSelectedItem().toString();
        if (amount.isEmpty()) {
            et_income.setError("Empty amount");
        }else if (type.equalsIgnoreCase("select item")){
            Toast.makeText(HomeActivity.this, "Please select a valid item", Toast.LENGTH_SHORT).show();
        } else {
            int in_amount=Integer.parseInt(amount);
            String id=database.push().getKey();
            String note="Budget";
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
            String mDate= DateFormat.getDateInstance().format(new Date());
            Data data=new Data(in_amount,type,typeday,typemonth,typeweek,note,id,date,weeks.getWeeks(),months.getMonths());
            assert id != null;
            database.child(id).setValue(data);
            Toast.makeText(HomeActivity.this,"Data Added!",Toast.LENGTH_SHORT).show();
            mAddIncomeFab.hide();
            mAddExpenseFab.hide();
            addExpenseText.setVisibility(View.GONE);
            addIncomeText.setVisibility(View.GONE);

            isAllFabVisible = false;
            alertDialog.dismiss();

        }

    });

}
    private void showExpenseDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);

        final View customLayout = getLayoutInflater().inflate(R.layout.activity_add_expense, null);
        EditText et_income = customLayout.findViewById(R.id.et_expenseAmount);
        EditText et_note = customLayout.findViewById(R.id.et_expenseNote);

        Button btn_save = customLayout.findViewById(R.id.btn_save_expense);
        Button btn_cancel = customLayout.findViewById(R.id.btn_cancel_expense);

        final Spinner itemSpinner = customLayout.findViewById(R.id.spinner_expense);
        ArrayAdapter<String> itemsAdapter = new ArrayAdapter<>(this,
                android.R.layout.simple_list_item_1, getResources().getStringArray(R.array.items));
        itemsAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        itemSpinner.setAdapter(itemsAdapter);

        builder.setView(customLayout);
        AlertDialog alertDialog = builder.create();

        alertDialog.show();

        btn_cancel.setOnClickListener(v -> alertDialog.dismiss());

        btn_save.setOnClickListener(v -> {

            String amount = et_income.getText().toString();
            String note = et_note.getText().toString();
            String type = itemSpinner.getSelectedItem().toString();


            if (amount.isEmpty()) {
                et_income.setError("Empty amount");
            } else if (type.equalsIgnoreCase("select item")){
            Toast.makeText(HomeActivity.this, "Please select a valid item", Toast.LENGTH_SHORT).show();
            } else if (note.isEmpty()) {
                et_note.setError("Empty note");
            } else {
                int out_amount=Integer.parseInt(amount);
                String id=database2.push().getKey();


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

                String mDate= DateFormat.getDateInstance().format(new Date());
                Data data=new Data(out_amount,type,typeday,typemonth,typeweek,note,id,date,weeks.getWeeks(),months.getMonths());
                assert id != null;
                database2.child(id).setValue(data);
                Toast.makeText(HomeActivity.this,"Data Added!",Toast.LENGTH_SHORT).show();
                mAddIncomeFab.hide();
                mAddExpenseFab.hide();
                addExpenseText.setVisibility(View.GONE);
                addIncomeText.setVisibility(View.GONE);

                isAllFabVisible = false;
                alertDialog.dismiss();

            }

        });
//        binding.bottomNavigationView.setOnItemSelectedListener(item -> {
//            switch(item.getItemId()){
//                case R.id.miHome:
//                    replaceFragment(new HomeFragment());
//                    break;
//                case R.id.miIncome:
////                    replaceFragment(new IncomeFragment());
//                    startActivity(new Intent(HomeActivity.this, IncomeActivity.class));
//
//                    break;
//                case R.id.miExpense:
//                    startActivity(new Intent(HomeActivity.this, ExpenseActivity.class));
////                    replaceFragment(new ExpenseFragment());
//                    break;
//                case R.id.miProfile:
//                    startActivity(new Intent(HomeActivity.this, ProfileActivity.class));
//                    break;
//
//            }
//            return true;
//        });
    }

    private void replaceFragment(Fragment fragment){
            FragmentManager fragmentManager = getSupportFragmentManager();
            FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
//            fragmentTransaction.replace(R.id.frame_layout, fragment);
            fragmentTransaction.commit();
        }

}
