package com.core.storesafe;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.preference.PreferenceFragmentCompat;

public class SettingsActivity extends AppCompatActivity {

    Button btnVault, btnGenerate, btnSettings;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.settings_activity);
        getSupportFragmentManager()
                .beginTransaction()
                .replace(R.id.settings, new SettingsFragment())
                .commit();
        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.setDisplayHomeAsUpEnabled(true);

            btnVault = findViewById(R.id.navVault);
            btnGenerate = findViewById(R.id.navGenerate);
            btnSettings = findViewById(R.id.navSettings);

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

            btnSettings.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent settings = new Intent(SettingsActivity.this, SettingsActivity.class);
                    startActivity(settings);
                }
            });
        }
    }

    public static class SettingsFragment extends PreferenceFragmentCompat {
        @Override
        public void onCreatePreferences(Bundle savedInstanceState, String rootKey) {
            setPreferencesFromResource(R.xml.root_preferences, rootKey);
        }
    }
}