package com.example.edusoft;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
import android.widget.Toolbar;

public class UserProfileActivity extends AppCompatActivity {

    EditText editText_name,editText_email;

    Button button_add,button_view;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_profile);
        editText_email = findViewById(R.id.my_email);
        editText_name = findViewById(R.id.my_Name);

        button_add = findViewById(R.id.btnStudentAdd);
        button_view = findViewById(R.id.btnView);

        button_add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String stringName = editText_name.getText().toString();
                String stringEmail = editText_email.getText().toString();

                if(stringName.length() <=0 || stringEmail.length() <=0){
                    Toast.makeText(UserProfileActivity.this,"Enter All Data",Toast.LENGTH_SHORT).show();
                }else {
                    DatabaseHelperClass databaseHelperClass = new DatabaseHelperClass(UserProfileActivity.this);
                    StudentModelClass studentModelClass = new StudentModelClass(stringName,stringEmail);
                    databaseHelperClass.addStudent(studentModelClass);
                    Toast.makeText(UserProfileActivity.this,"Add student successfully !!!",Toast.LENGTH_SHORT).show();

                    finish();
                    startActivity(getIntent());
                }
            }
        });

        button_view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(UserProfileActivity.this,ViewEmployeeActivity.class);
                startActivity(intent);
            }
        });

    }

    public void backDashboard(View view) {
        Intent intent = new Intent(this,Dashboard.class);
        startActivity(intent);
    }
}