package com.core.storesafe;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;

public class ForgottenActivity extends AppCompatActivity {

    // Variable Assignment
    FirebaseAuth mFirebaseAuth;
    EditText emailId;
    Button btnReset, btnBack;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_forgotten);

        // Toolbar
        Toolbar toolbar = (Toolbar) findViewById(R.id.app_bar);
        setSupportActionBar(toolbar);

        // Object Initialisation
        mFirebaseAuth = FirebaseAuth.getInstance();
        emailId = findViewById(R.id.email);
        btnReset = findViewById(R.id.reset);
        btnBack = findViewById(R.id.back);

        // Button onClick Listeners
        btnReset.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                String email = emailId.getText().toString();

                if (email.isEmpty()) {
                    emailId.setError("Please enter an email");
                    emailId.requestFocus();
                }
                else if(!(email.isEmpty())) {
                    mFirebaseAuth.sendPasswordResetEmail(email).addOnCompleteListener(new OnCompleteListener<Void>() {
                        @Override
                        public void onComplete(@NonNull Task<Void> task) {
                            Toast.makeText(ForgottenActivity.this, "Password Reset Email sent", Toast.LENGTH_SHORT).show();
                        }
                    });
                }
                else {
                    Toast.makeText(ForgottenActivity.this, "An Error Occurred", Toast.LENGTH_SHORT).show();
                }
            }
        });

        btnBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(ForgottenActivity.this, LoginActivity.class);
                startActivity(i);
            }
        });
    }
}
