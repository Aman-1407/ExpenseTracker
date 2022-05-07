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
    DatabaseReference database,database2,balanceRef;
    MyAdapter myAdapter;
    MyAdapter2 myAdapter2;
    ArrayList<Data> list;
    TextView amountTextview1,amountTextview2,inc_dash,exe_dash,balance,record;
    ImageView profile;

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
        DatabaseReference reference = FirebaseDatabase.getInstance().getReference("Users");

        final TextView greetingTextView = (TextView) findViewById(R.id.welcome);
        amountTextview1 = findViewById(R.id.income_home);
        amountTextview2 = findViewById(R.id.expense_home);
        inc_dash = findViewById(R.id.inc_dash);
        exe_dash = findViewById(R.id.exe_dash);
        balance = findViewById(R.id.balance);
        record = findViewById(R.id.record_dash);
        list = new ArrayList<>();
        myAdapter = new MyAdapter(this, list);
        myAdapter2 = new MyAdapter2(this, list);

        reference.child(uid).addListenerForSingleValueEvent(new ValueEventListener() {
            @SuppressLint("SetTextI18n")
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                User UserProfile = snapshot.getValue(User.class);
                if (UserProfile != null) {
                    String fullname = UserProfile.fullname;
                    greetingTextView.setText("" + fullname);
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
            }


            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

        inc_dash.setOnClickListener(view -> startActivity(new Intent(HomeActivity.this, IncomeActivity.class)));

        exe_dash.setOnClickListener(view -> startActivity(new Intent(HomeActivity.this, ExpenseActivity.class)));

        record.setOnClickListener(view -> startActivity(new Intent(HomeActivity.this, Feature.class)));

//
//        balanceRef.addValueEventListener(new ValueEventListener() {
//            @Override
//            public void onDataChange(@NonNull DataSnapshot snapshot) {
//                int income = Integer.parseInt(snapshot.child("IncomeData").getValue().toString());
//                int expense = Integer.parseInt(snapshot.child("ExpenseData").getValue().toString());
//                int saving = income - expense;
//                balance.setText(" " + saving);
//            }
//
//            @Override
//            public void onCancelled(@NonNull DatabaseError error) {
//
//            }
//        });


    }



private void showIncomeDialog() {
    AlertDialog.Builder builder = new AlertDialog.Builder(this);

    final View customLayout = getLayoutInflater().inflate(R.layout.activity_add_income, null);
    EditText et_income = customLayout.findViewById(R.id.etIncomeAmount);
    EditText et_note = customLayout.findViewById(R.id.etIncomeNote);

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
        String note = et_note.getText().toString();
        String type = itemSpinner.getSelectedItem().toString();
        if (amount.isEmpty()) {
            et_income.setError("Empty amount");
        } else if (note.isEmpty()) {
            et_note.setError("Empty note");
        }else if (type.equalsIgnoreCase("select item")){
            Toast.makeText(HomeActivity.this, "Please select a valid item", Toast.LENGTH_SHORT).show();
        } else {
            int in_amount=Integer.parseInt(amount);
            String id=database.push().getKey();


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