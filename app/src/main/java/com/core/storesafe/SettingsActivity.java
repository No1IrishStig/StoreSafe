package com.core.storesafe;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.google.firebase.auth.FirebaseAuth;

public class SettingsActivity extends AppCompatActivity {
    Button btnVault, btnGenerate, btnSettings, btnClose, btnSignout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);

        btnVault = findViewById(R.id.navVault);
        btnGenerate = findViewById(R.id.navGenerate);
        btnSettings = findViewById(R.id.navSettings);

        btnClose = findViewById(R.id.closeapp);
        btnSignout = findViewById(R.id.signout);

        btnVault.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent vault = new Intent(SettingsActivity.this, HomeActivity.class);
                startActivity(vault);
            }
        });

        btnGenerate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent generate = new Intent(SettingsActivity.this, GenerateActivity.class);
                startActivity(generate);
            }
        });

        btnClose.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
                System.exit(0);
            }
        });

        btnSignout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FirebaseAuth.getInstance().signOut();
                Intent intent = new Intent(SettingsActivity.this, LoginActivity.class);
                startActivity(intent);
            }
        });


    }
}
