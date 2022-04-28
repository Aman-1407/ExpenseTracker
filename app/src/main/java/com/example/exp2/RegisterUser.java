package com.example.exp2;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.FirebaseDatabase;

import java.util.regex.Pattern;

public class RegisterUser extends AppCompatActivity implements View.OnClickListener{

    private TextView banner,register_user;
    private EditText fullname;
    private EditText age;
    private EditText email_reg;
    private EditText pass_reg;
    private ProgressBar ProgressBar;

    private FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register_user);

        mAuth = FirebaseAuth.getInstance();
        banner = (TextView) findViewById(R.id.banner);
        banner.setOnClickListener(this);
        register_user=(Button) findViewById(R.id.register_user);
        register_user.setOnClickListener(this);
        fullname=(EditText) findViewById(R.id.fullname);
        age=(EditText) findViewById(R.id.age);
        email_reg=(EditText) findViewById(R.id.email_reg);
        pass_reg=(EditText) findViewById(R.id.pass_reg);
        ProgressBar=(ProgressBar) findViewById(R.id.ProgressBar);

    }

    @SuppressLint("NonConstantResourceId")
    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.banner:
                startActivity(new Intent(this,MainActivity.class));
                break;
            case R.id.register_user:
                register_user();
                break;
    }
}

    private void register_user() {
        String email= email_reg.getText().toString().trim();
        String password= pass_reg.getText().toString().trim();
        String full= fullname.getText().toString().trim();
        String ag= age.getText().toString().trim();

        if(full.isEmpty()){
            fullname.setError("Full name is Required!");
            fullname.requestFocus();
            return;
        }
        if(ag.isEmpty()){
            age.setError("Age is Required!");
            age.requestFocus();
            return;
        }
        if(email.isEmpty()){
            email_reg.setError("Email is Required!");
            email_reg.requestFocus();
            return;
        }
        if(!Patterns.EMAIL_ADDRESS.matcher(email).matches()){
            email_reg.setError("Enter valid Email Address!");
            email_reg.requestFocus();
            return;
        }
        if(password.isEmpty()){
            pass_reg.setError("Password is Required!");
            pass_reg.requestFocus();
            return;
        }
        if (password.length()<6){
            pass_reg.setError("Min password length should be 6 characters!");
            pass_reg.requestFocus();
            return;
        }

        ProgressBar.setVisibility(View.VISIBLE);
        mAuth.createUserWithEmailAndPassword(email,password)
                .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if(task.isSuccessful()){
                            User user= new User(full,ag,email);
                            FirebaseDatabase.getInstance().getReference("Users")
                                    .child(FirebaseAuth.getInstance().getCurrentUser().getUid())
                                    .setValue(user).addOnCompleteListener(new OnCompleteListener<Void>() {
                                @Override
                                public void onComplete(@NonNull Task<Void> task) {
                                    if(task.isSuccessful()){
                                        Toast.makeText(RegisterUser.this, "User has been registered successfully", Toast.LENGTH_SHORT).show();
                                        ProgressBar.setVisibility(View.GONE);
                                    }else{
                                        Toast.makeText(RegisterUser.this, "Failed to register! Try again!", Toast.LENGTH_SHORT).show();
                                        ProgressBar.setVisibility(View.GONE);
                                    }
                                }
                            });
                        }else{
                            Toast.makeText(RegisterUser.this, "Failed to register! Try again!", Toast.LENGTH_SHORT).show();
                            ProgressBar.setVisibility(View.GONE);

                        }
                    }
                });

}}