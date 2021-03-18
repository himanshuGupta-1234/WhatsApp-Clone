package com.example.chat;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.Toast;

import com.example.chat.Adapter.FragmentAdapter;
import com.example.chat.databinding.ActivityHomeBinding;
import com.google.firebase.auth.FirebaseAuth;

public class Home extends AppCompatActivity {

     private  FirebaseAuth mAuth;
    ActivityHomeBinding activityHomeBinding;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        activityHomeBinding=ActivityHomeBinding.inflate(getLayoutInflater());
        setContentView(activityHomeBinding.getRoot());
        getSupportActionBar().hide();

        mAuth=FirebaseAuth.getInstance();






        //Tab layout work
        activityHomeBinding.viewPager.setAdapter(new FragmentAdapter(getSupportFragmentManager()));
        activityHomeBinding.tab. setupWithViewPager(activityHomeBinding.viewPager);


    }

    @Override
    public void onBackPressed() {
        moveTaskToBack(true);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater menuInflater=getMenuInflater();
        menuInflater.inflate(R.menu.menu,menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {


        switch (item.getItemId())
        {
            case R.id.settings:

                Intent intent1=new Intent(Home.this,Settings.class );
                startActivity(intent1);


                break;

            case R.id.group:

                Intent intent=new Intent(Home.this,GroupChat.class );
                startActivity(intent);

                break;

            case R.id.logout:
                mAuth.signOut();

                Intent inten=new Intent(Home.this,sign_in.class );
                startActivity(inten);

                break;
        }
        return super.onOptionsItemSelected(item);
    }
}