package com.example.edusoft;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthInvalidCredentialsException;
import com.google.firebase.auth.FirebaseAuthUserCollisionException;
import com.google.firebase.auth.FirebaseAuthWeakPasswordException;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.UserProfileChangeRequest;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.ktx.Firebase;

import java.util.Calendar;

public class RegisterActivity extends AppCompatActivity {

    private EditText editTextRegisterFullName,editTextRegisterEmail,editTextRegisterDoB,editTextRegisterMobile,editTextRegisterPassword,editTextRegisterConfirmPassword;
    private ProgressBar progressBar;
    private RadioGroup radioGroupRegisterGender;
    private RadioButton radioButtonRegisterGenderSelected;

    private DatePickerDialog picker;

    private static final String TAG = "RegisterActivity";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        // Check if the ActionBar exists before trying to modify it
        if (getSupportActionBar() != null) {
            getSupportActionBar().setTitle("Register"); // Set the title of the ActionBar
        }


        Toast.makeText(RegisterActivity.this,"You can register now",Toast.LENGTH_LONG).show();
        editTextRegisterFullName = findViewById(R.id.editText_register_full_name);
        editTextRegisterEmail = findViewById(R.id.editText_register_email);
        editTextRegisterDoB = findViewById(R.id.editText_register_dob);
        editTextRegisterMobile = findViewById(R.id.editText_register_mobile);
        editTextRegisterPassword = findViewById(R.id.editText_register_password);
        editTextRegisterConfirmPassword = findViewById(R.id.editText_register_confirm_password);
        progressBar = findViewById(R.id.progressBar);

        //Radio button for gender
        radioGroupRegisterGender = findViewById(R.id.radio_group_register_gender);
        radioGroupRegisterGender.clearCheck();

        editTextRegisterDoB.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final Calendar calendar = Calendar.getInstance();
                int day = calendar.get(Calendar.DAY_OF_MONTH);
                int month = calendar.get(Calendar.MONTH);
                int year = calendar.get(Calendar.YEAR);

                picker = new DatePickerDialog(RegisterActivity.this, new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker datePicker, int year, int monthOfYear, int dayOfMonth) {
                        editTextRegisterDoB.setText(dayOfMonth+"/"+ (monthOfYear +1 ) +"/"+ year);
                    }
                },year,month,day);
                picker.show();
            }
        });

        Button buttonRegister = findViewById(R.id.regButtonNew);
        buttonRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                int selectedGenderId = radioGroupRegisterGender.getCheckedRadioButtonId();
                radioButtonRegisterGenderSelected = findViewById(selectedGenderId);

                //Obtain the entered data
                String textFullName = editTextRegisterFullName.getText().toString();
                String textEmail = editTextRegisterEmail.getText().toString();
                String textDoB = editTextRegisterDoB.getText().toString();
                String textMobile = editTextRegisterMobile.getText().toString();
                String textPassword = editTextRegisterPassword.getText().toString();
                String textConfirmedPassword = editTextRegisterConfirmPassword.getText().toString();
                String textGender;

                if(TextUtils.isEmpty(textFullName)){
                    Toast.makeText(RegisterActivity.this,"Please enter your full name",Toast.LENGTH_LONG).show();
                    editTextRegisterFullName.setError("Full name is required");
                    editTextRegisterFullName.requestFocus();
                }else if(TextUtils.isEmpty(textEmail)){
                    Toast.makeText(RegisterActivity.this,"Please enter your email",Toast.LENGTH_LONG).show();
                    editTextRegisterEmail.setError("Email is required");
                    editTextRegisterEmail.requestFocus();
                }else if(!Patterns.EMAIL_ADDRESS.matcher(textEmail).matches()){
                    Toast.makeText(RegisterActivity.this,"Please check your email address again",Toast.LENGTH_LONG).show();
                    editTextRegisterEmail.setError("Valid email is required");
                    editTextRegisterEmail.requestFocus();
                }else if(TextUtils.isEmpty(textDoB)){
                    Toast.makeText(RegisterActivity.this,"Please enter the DoB",Toast.LENGTH_LONG).show();
                    editTextRegisterDoB.setError("DoB is required");
                    editTextRegisterDoB.requestFocus();
                }else if(radioGroupRegisterGender.getCheckedRadioButtonId() == -1){
                    Toast.makeText(RegisterActivity.this,"Please select your gender",Toast.LENGTH_LONG).show();
                    radioButtonRegisterGenderSelected.setError("DoB is required");
                    radioButtonRegisterGenderSelected.requestFocus();
                }else if(TextUtils.isEmpty(textMobile)){
                    Toast.makeText(RegisterActivity.this,"Please enter your mobile number",Toast.LENGTH_LONG).show();
                    editTextRegisterMobile.setError("Mobile number is required");
                    editTextRegisterMobile.requestFocus();
                }else if(textMobile.length() != 10){
                    Toast.makeText(RegisterActivity.this,"Please enter valid mobile number",Toast.LENGTH_LONG).show();
                    editTextRegisterMobile.setError("Mobile number must contain 10 digits");
                    editTextRegisterMobile.requestFocus();
                }else if (TextUtils.isEmpty(textPassword)) {
                    Toast.makeText(RegisterActivity.this,"Please enter the password",Toast.LENGTH_LONG).show();
                    editTextRegisterPassword.setError("Password is required");
                    editTextRegisterPassword.requestFocus();
                }else if(textPassword.length()<6){
                    Toast.makeText(RegisterActivity.this,"Please should have at least 6 digits",Toast.LENGTH_LONG).show();
                    editTextRegisterPassword.setError("Password is too weak");
                    editTextRegisterPassword.requestFocus();
                }else if (TextUtils.isEmpty(textConfirmedPassword)) {
                    Toast.makeText(RegisterActivity.this,"Please confirm the password",Toast.LENGTH_LONG).show();
                    editTextRegisterConfirmPassword.setError("Confirm password is required");
                    editTextRegisterConfirmPassword.requestFocus();
                } else if (!textPassword.equals(textConfirmedPassword)) {
                    Toast.makeText(RegisterActivity.this,"Please check the password and confirm password",Toast.LENGTH_LONG).show();
                    editTextRegisterConfirmPassword.setError("Password does not match");
                    editTextRegisterConfirmPassword.requestFocus();

                    //clear the entered password
                    editTextRegisterPassword.clearComposingText();
                    editTextRegisterConfirmPassword.clearComposingText();
                }else{
                    textGender = radioButtonRegisterGenderSelected.getText().toString();
                    progressBar.setVisibility(View.VISIBLE);
                    registerUser(textFullName,textEmail,textDoB,textGender,textMobile,textPassword);
                }
            }
        });

    }


    private void registerUser(String textFullName, String textEmail, String textDoB, String textGender, String textMobile, String textPassword) {
        FirebaseAuth auth = FirebaseAuth.getInstance();
        auth.createUserWithEmailAndPassword(textEmail,textPassword).addOnCompleteListener(RegisterActivity.this, new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if(task.isSuccessful()){

                    FirebaseUser firebaseUser = auth.getCurrentUser();

                    //User profile change request
                    UserProfileChangeRequest profileChangeRequest = new UserProfileChangeRequest.Builder().setDisplayName(textFullName).build();
                    firebaseUser.updateProfile(profileChangeRequest);

                    //Enter User Data Into FireBase

                    ReadWriteUserDetails writeUserDetails = new ReadWriteUserDetails(textFullName,textDoB,textGender,textMobile);


                    //Extracting user references from db for registered users
                    DatabaseReference referenceProfile = FirebaseDatabase.getInstance().getReference("Registered user");

                    referenceProfile.child(firebaseUser.getUid()).setValue(writeUserDetails).addOnCompleteListener(new OnCompleteListener<Void>() {
                        @Override
                        public void onComplete(@NonNull Task<Void> task) {

                            if(task.isSuccessful()){
                                //send verification email
                                firebaseUser.sendEmailVerification();

                                Toast.makeText(RegisterActivity.this,"User registered successfully!!!, Please verify your email",Toast.LENGTH_LONG).show();

                                //open user profile successful registration
                                Intent intent = new Intent(RegisterActivity.this, UserProfileActivity.class);

                                //to prevent to returning user to the registration form again
                                intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
                                startActivity(intent);

                                //finish the activity
                                finish();
                            }
                            else{
                                Toast.makeText(RegisterActivity.this,"User registered unsuccessfully!!!, Please try again later",Toast.LENGTH_LONG).show();
                            }
                            progressBar.setVisibility(View.GONE);

                        }
                    });
                } else {
                    try {
                        throw task.getException();
                    } catch(FirebaseAuthWeakPasswordException e){
                        editTextRegisterPassword.setError("Your password is too weak.Kindly use appropriate password");
                        editTextRegisterPassword.requestFocus();
                    } catch(FirebaseAuthInvalidCredentialsException e){
                        editTextRegisterPassword.setError("Email is already in use.Please try another email address");
                        editTextRegisterPassword.requestFocus();}
                    /*}catch(FirebaseAuthUserCollisionException e) {
                        editTextRegisterPassword.setError("Your password is too weak.Kindly use appropriate password");
                        editTextRegisterPassword.requestFocus();
                    }*/catch (Exception e){
                        Log.e(TAG, e.getMessage());
                        Toast.makeText(RegisterActivity.this, e.getMessage(), Toast.LENGTH_LONG).show();
                    }
                    progressBar.setVisibility(View.GONE);
                }
            }
        });
    }
}