package com.core.storesafe;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import android.content.Intent;
import android.media.Image;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;

import com.core.storesafe.Navbar.GenerateFragment;
import com.core.storesafe.Navbar.HomeFragment;
import com.core.storesafe.Navbar.SettingsFragment;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

public class MainActivity extends AppCompatActivity {

    // Variable Assignment
    private Button getStarted;
    Animation frombottom;
    ImageView logo;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        // Toolbar
        Toolbar toolbar = (Toolbar) findViewById(R.id.app_bar);
        setSupportActionBar(toolbar);

        // Object Initialisation
        getStarted = findViewById(R.id.Go);
        frombottom = AnimationUtils.loadAnimation(this, R.anim.from_bottom);
        getStarted.setAnimation(frombottom);
        logo = findViewById(R.id.logoview);


        // Splash Screen
        Animation animSlide = AnimationUtils.loadAnimation(getApplicationContext(),
                R.anim.floating);

        logo.startAnimation(animSlide);
    }


    // On Get Started Run this Animation
    public void openLogin(View view) {
        Intent intent = new Intent(this, LoginActivity.class);
        startActivity(intent);
        overridePendingTransition(R.anim.slide_up, R.anim.slide_down);
    }
}
