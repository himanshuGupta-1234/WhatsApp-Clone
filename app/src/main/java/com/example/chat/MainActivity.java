package com.example.chat;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.example.chat.Models.Users;
import com.example.chat.databinding.ActivityMainBinding;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.FirebaseDatabase;

public class MainActivity extends AppCompatActivity {

    private FirebaseAuth mAuth;
    FirebaseDatabase database;
    ActivityMainBinding binding;
    ProgressDialog progressDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding=ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        getSupportActionBar().hide();

        progressDialog=new ProgressDialog(this);
        progressDialog.setTitle("Creating  Account");
        progressDialog.setMessage("We are Creating your Account");


        mAuth = FirebaseAuth.getInstance();
        database = FirebaseDatabase.getInstance();

        binding.alrdy.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent(MainActivity.this,sign_in.class);
                startActivity(intent);
            }
        });
        binding.signUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if (binding.name.getText().length() == 0 || binding.email.getText().length() == 0 || binding.password.getText().length() == 0) {
                    Toast.makeText(MainActivity.this, "Enter the data", Toast.LENGTH_LONG).show();
                } else {
                    progressDialog.show();
                    mAuth.createUserWithEmailAndPassword(binding.email.getText().toString(), binding.password.getText().toString()).
                            addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                                @Override
                                public void onComplete(@NonNull Task<AuthResult> task) {
                                    if (task.isSuccessful()) {
                                        progressDialog.dismiss();
                                        Users user = new Users(binding.name.getText().toString(), binding.email.getText().toString(), binding.password.getText().toString());
                                        String id = task.getResult().getUser().getUid();
                                        database.getReference().child("Users").child(id).setValue(user);
                                        Toast.makeText(MainActivity.this, "User created Successfully", Toast.LENGTH_LONG).show();

                                        Intent intent=new Intent(MainActivity.this,Home.class);
                                        startActivity(intent);
                                    } else {
                                        Toast.makeText(MainActivity.this, task.getException().getMessage(), Toast.LENGTH_LONG).show();
                                    }
                                }
                            });

                }
            }
        });




    }

    @Override
    public void onBackPressed() {
        moveTaskToBack(true);
    }
}