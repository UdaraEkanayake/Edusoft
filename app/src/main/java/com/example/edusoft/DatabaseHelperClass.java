package com.example.edusoft;


import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import java.util.ArrayList;
import java.util.List;

public class DatabaseHelperClass extends SQLiteOpenHelper {

    //Database version
    private static final int DATABASE_VERSION = 1;

    private static final String  DATABASE_NAME = "student_database";

    private static final String TABLE_NAME = "STUDENT";

    public static final String ID ="id";
    public static final String NAME ="name";
    public static final String EMAIL ="email";
    private SQLiteDatabase sqLiteDatabase;


    //create table
   // private static final String CREATE_TABLE = "create table " + TABLE_NAME + "("+ID+"INTEGER PRIMARY KEY AUTOINCREMENT,"+ NAME + "TEXT NOT NULL,"+EMAIL+"TEXT NOT NULL)";

    private static final String TABLE_CREATE =
            "CREATE TABLE " + TABLE_NAME + "(" +
                    ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                    NAME + " TEXT, " +
                    EMAIL + " TEXT)";

    public DatabaseHelperClass (Context context){
        super (context,DATABASE_NAME,null,DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(TABLE_CREATE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL(" DROP TABLE IF EXISTS " + TABLE_NAME);
        onCreate(db);
    }
    public void addStudent(StudentModelClass studentModelClass){
        ContentValues contentValues = new ContentValues();
        contentValues.put(DatabaseHelperClass.NAME,studentModelClass.getName());
        contentValues.put(DatabaseHelperClass.EMAIL,studentModelClass.getEmail());
        sqLiteDatabase = this.getWritableDatabase();
        sqLiteDatabase.insert(DatabaseHelperClass.TABLE_NAME, null , contentValues);
    }

    public List<StudentModelClass> getStudentList(){
        String sql = "select * from "+ TABLE_NAME;
        sqLiteDatabase = this.getReadableDatabase();
        List<StudentModelClass> storeStudent = new ArrayList<>();
        Cursor cursor = sqLiteDatabase.rawQuery(sql,null);
        if(cursor.moveToFirst()){
            do{
                int id = Integer.parseInt(cursor.getString(0));
                String name = cursor.getString(1);
                String email = cursor.getString(2);

                Log.d("StudentData", "ID: " + id + ", Name: " + name + ", Email: " + email);


                storeStudent.add(new StudentModelClass(id,name,email));
            }while(cursor.moveToNext());
        }
        cursor.close();
        return storeStudent;
    }

    public void updateStudent(StudentModelClass updatedStudent) {
        ContentValues values = new ContentValues();
        values.put(NAME, updatedStudent.getName());
        values.put(EMAIL, updatedStudent.getEmail());

        SQLiteDatabase db = this.getWritableDatabase();

        // Update the record where ID matches
        int rowsUpdated = db.update(TABLE_NAME, values, ID + " = ?", new String[] { String.valueOf(updatedStudent.getId()) });

        Log.d("UpdatedStudent", "Updated student: " + updatedStudent.getName() + ", " + updatedStudent.getEmail());

        Log.d("UpdateResult", "Rows updated: " + rowsUpdated);
        // Close the database connection
        db.close();
    }
    public void deleteStudent(int studentId) {
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete(TABLE_NAME, ID + " = ?", new String[]{String.valueOf(studentId)});
        db.close();
    }


}
