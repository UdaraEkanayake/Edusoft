package com.example.edusoft;

import androidx.appcompat.app.AppCompatActivity;

import android.app.ActivityOptions;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.util.Pair;
import android.view.View;
import android.view.WindowManager;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {

    //move to login page in 2 second
    private static int SPLASH_SCREEN = 2000;

    //variables for animation
    Animation topAnim,bottomAnim;

    //variables for get image and text id;

    ImageView image;
    TextView logo;
    TextView logo2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_main);

        //call the animations to the variables
        topAnim = AnimationUtils.loadAnimation(this,R.anim.top_animation);
        bottomAnim = AnimationUtils.loadAnimation(this,R.anim.bottom_animation);

        //Hooks
        image = findViewById(R.id.imageView);
        logo = findViewById(R.id.textView);
        logo2 = findViewById(R.id.textView2);

        //set animations to the components
        image.setAnimation((topAnim));
        logo.setAnimation(bottomAnim);
        logo2.setAnimation(bottomAnim);

        //navigate to the Dashboard Activity with withing 2 second (using SPLASH_SCREEN)

        new Handler().postDelayed(() -> {
            Intent intent = new Intent(MainActivity.this,LoginPage.class);
            Pair[] pairs = new Pair[3];
            pairs[0] = new Pair<View,String>(image, "logo_image");
            pairs[1] = new Pair<View,String>(logo, "logo_text");
            pairs[2] = new Pair<View,String>(logo2,"logo_text2");
            if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.LOLLIPOP) {
                ActivityOptions options = ActivityOptions.makeSceneTransitionAnimation(MainActivity.this,pairs);
                startActivity(intent,options.toBundle());
            }
        },SPLASH_SCREEN);
    }
}