package com.example.exp2.Activities;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.TextView;

import com.example.exp2.R;

public class Feature extends AppCompatActivity {





    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_feature);

        TextView week=findViewById(R.id.week);
        TextView month=findViewById(R.id.month);
        TextView pie=findViewById(R.id.pie_feature);

        week.setOnClickListener(view -> startActivity(new Intent(Feature.this, com.example.exp2.Activities.week.class)));
        month.setOnClickListener(view -> startActivity(new Intent(Feature.this, com.example.exp2.Activities.month.class)));
        pie.setOnClickListener(view -> startActivity(new Intent(Feature.this, Pie.class)));

    }
}