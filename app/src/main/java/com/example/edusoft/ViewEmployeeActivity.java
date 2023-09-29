package com.example.edusoft;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import java.util.List;

public class ViewEmployeeActivity extends AppCompatActivity {

    RecyclerView recyclerView;
    StudentAdapterClass studentAdapterClass;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_employee);

        recyclerView = findViewById(R.id.myeView);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setHasFixedSize(true);

        DatabaseHelperClass databaseHelperClass = new DatabaseHelperClass(this);
        List<StudentModelClass> studentModelClasses = databaseHelperClass.getStudentList();
        Log.d("DataSize", "Data size: " + studentModelClasses.size());

        if(studentModelClasses.size()>0){

                StudentAdapterClass studentAdapterClass = new StudentAdapterClass(studentModelClasses, ViewEmployeeActivity.this);
                recyclerView.setAdapter(studentAdapterClass);

        }else{
            Toast.makeText(this,"There is no student registered yet !!!",Toast.LENGTH_SHORT).show();
        }

    }

    public void updateStudentRecord(StudentModelClass updatedStudent) {
        DatabaseHelperClass databaseHelperClass = new DatabaseHelperClass(this);

        // Update the student's record in the database
        databaseHelperClass.updateStudent(updatedStudent);
    }

    public void deleteStudentRecord(int studentId) {
        // Call the delete method in your database helper
        DatabaseHelperClass databaseHelperClass = new DatabaseHelperClass(this);
        databaseHelperClass.deleteStudent(studentId);

        // Update the UI by removing the deleted student from the adapter
        studentAdapterClass.removeStudent(studentId);
        studentAdapterClass.notifyDataSetChanged();
    }
}