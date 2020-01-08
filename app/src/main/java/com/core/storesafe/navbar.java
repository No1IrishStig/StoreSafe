package com.core.storesafe;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class navbar extends AppCompatActivity {

    Button btnVault, btnGenerate, btnSettings;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_navbar);

        btnVault = findViewById(R.id.navVault);
        btnGenerate = findViewById(R.id.navGenerate);
        btnSettings = findViewById(R.id.navSettings);

        btnVault.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent vault = new Intent(navbar.this, HomeActivity.class);
                startActivity(vault);
            }
        });

        btnGenerate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent generate = new Intent(navbar.this, GenerateActivity.class);
                startActivity(generate);
            }
        });

        btnSettings.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent settings = new Intent(navbar.this, SettingsActivity.class);
                startActivity(settings);
            }
        });
    }
}
