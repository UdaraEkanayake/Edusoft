package com.example.edusoft;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.ClipData;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import com.google.android.material.bottomnavigation.BottomNavigationView;

public class Dashboard extends AppCompatActivity {

   Toolbar toolbar;
   BottomNavigationView bottomNavigationView;
   Home home;
   Assignment assignment;
   Fees fees;
   Library library;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dashboard);
        home = new Home();
        assignment = new Assignment();
        fees = new Fees();
        library = new Library();

        // Find the Toolbar by its ID
        toolbar = findViewById(R.id.tool);
        bottomNavigationView = findViewById(R.id.bottom);

        //work with the Toolbar
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("Edusoft");

        bottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                return false;
            }
        });
    }


    public void regClick(View view) {
        //        Intent intent = new Intent(this,Dashboard.class);
//            intent.putExtra("LogIn Message" , "Successfully LogIn");
//            startActivity(intent);

        Intent intent = new Intent(this,RegisterActivity.class);
        startActivity(intent);
    }

    public void logClick(View view) {
        Intent intent2 = new Intent(this,LoginActivity.class);
        startActivity(intent2);
    }
}