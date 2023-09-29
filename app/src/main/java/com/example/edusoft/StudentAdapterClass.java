package com.example.edusoft;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class StudentAdapterClass extends RecyclerView.Adapter<StudentAdapterClass.ViewHolder>{

    List<StudentModelClass> student;
    Context context;
    DatabaseHelperClass databaseHelperClass;

    public StudentAdapterClass(List<StudentModelClass> student, Context context) {
        this.student = student;
        this.context = context;
        databaseHelperClass = new DatabaseHelperClass(context);
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater  =  LayoutInflater.from(parent.getContext());
        View view  = layoutInflater.inflate(R.layout.student_item_list,parent,false);
        ViewHolder viewHolder = new ViewHolder(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        final StudentModelClass studentModelClass = student.get(position);
        holder.textViewID.setText(Integer.toString(studentModelClass.getId()));
        holder.editText_Name.setText(studentModelClass.getName());
        holder.editText_Email.setText(studentModelClass.getEmail());

        // Handle the edit button click
        holder.button_Edit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Update the student's record directly in the adapter
                ((ViewEmployeeActivity) context).updateStudentRecord(studentModelClass);

                // Notify the adapter that the data has changed
                notifyDataSetChanged();

                // Show a toast message indicating the update
                Toast.makeText(context, "Student record updated", Toast.LENGTH_SHORT).show();
            }
        });

        // Handle the delete button click
        holder.button_delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Call the deleteStudentRecord method in your activity
                ((ViewEmployeeActivity) context).deleteStudentRecord(studentModelClass.getId());
            }
        });
    }

    @Override
    public int getItemCount() {
        return student.size();
    }

    // Helper method to remove a student from the list
    public void removeStudent(int studentId) {
        for (int i = 0; i < student.size(); i++) {
            if (student.get(i).getId() == studentId) {
                student.remove(i);
                break;
            }
        }
    }

    public class ViewHolder extends RecyclerView.ViewHolder{

        TextView textViewID ;
        EditText editText_Name ;
        EditText editText_Email;
        Button button_Edit;
        Button button_delete;


        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            textViewID = itemView.findViewById(R.id.text_id);
            editText_Name = itemView.findViewById(R.id.editTextName);
            editText_Email = itemView.findViewById(R.id.editTextEmail);
            button_delete = itemView.findViewById(R.id.btndeleteDetails);
            button_Edit = itemView.findViewById(R.id.btneditDetails);
        }
    }
}
