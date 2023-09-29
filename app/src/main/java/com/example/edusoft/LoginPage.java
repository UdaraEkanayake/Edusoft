package com.example.edusoft;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;
import com.google.firebase.auth.FirebaseAuth;

public class LoginPage extends AppCompatActivity {


    private TextInputEditText usernameEditText;
    private TextInputEditText passwordEditText;
    private Button loginButton;

    //private ProgressBar progressBar;

    //private FirebaseAuth authProfile;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login_page);

        /*if (getSupportActionBar() != null) {
            getSupportActionBar().setTitle("Login"); // Set the title of the ActionBar
        }*/

        // Initialize UI components
        usernameEditText = findViewById(R.id.userName);
        passwordEditText = findViewById(R.id.Password);
        loginButton = findViewById(R.id.btnLogin);
        //progressBar = findViewById(R.id.proBar);

        //authProfile = FirebaseAuth.getInstance();

        loginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

               /* String textEmail = usernameEditText.getText().toString();
                String textPassword = passwordEditText.getText().toString();

                if(TextUtils.isEmpty(textEmail)){
                    Toast.makeText(LoginPage.this,"Please enter your email as username",Toast.LENGTH_SHORT).show();
                    usernameEditText.setError("Username is required");
                    usernameEditText.requestFocus();
                }else if(!Patterns.EMAIL_ADDRESS.matcher(textEmail).matches()){
                    Toast.makeText(LoginPage.this,"Please re-enter your email as username",Toast.LENGTH_SHORT).show();
                    usernameEditText.setError("Valid username is required");
                    usernameEditText.requestFocus();
                }else if (TextUtils.isEmpty(textPassword)){
                    Toast.makeText(LoginPage.this,"Please enter your password",Toast.LENGTH_SHORT).show();
                    usernameEditText.setError("Username is required");
                    usernameEditText.requestFocus();
                }*/
                loginFn();
            }
        });


    }
    private void loginFn(){

        usernameEditText = findViewById(R.id.userName);
        passwordEditText = findViewById(R.id.Password);
        loginButton = findViewById(R.id.btnLogin);
        String username = usernameEditText.getText().toString();
        String pass = passwordEditText.getText().toString();
        if (username.equals("Udara")&& pass.equals("abcd")){
            Intent intent = new Intent(this,Dashboard.class);
            intent.putExtra("LogIn Message" , "Successfully LogIn");
            startActivity(intent);
        }else {
            Toast.makeText(LoginPage.this,"LogIn failed "+username,Toast.LENGTH_SHORT).show();
        }
    }
}