package com.core.storesafe;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.content.Intent;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.Map;

public class NewCredentialsHolder extends AppCompatActivity {
        private Button btnLogout, btnSendData, mSendData, btnCancel, btnExit;
        FirebaseAuth mFirebaseAuth;
        private FirebaseAuth.AuthStateListener mAuthStateListener;
        EditText URL, Sitename, Username, Password, Notes;
        FirebaseUser currentFirebaseUser = FirebaseAuth.getInstance().getCurrentUser();
        String userid = currentFirebaseUser.getUid();


        @Override
        protected void onCreate(Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);
            setContentView(R.layout.activity_new_credentials_holder);
            Toolbar toolbar = (Toolbar) findViewById(R.id.app_bar);
            setSupportActionBar(toolbar);

            URL = findViewById(R.id.url);
            Sitename = findViewById(R.id.sitename);
            Username = findViewById(R.id.username);
            Password = findViewById(R.id.password);
            Notes = findViewById(R.id.notes);

            // Save Button Function -----------------------------------------------------------------------------------------------------------------------------------

            mSendData = findViewById(R.id.save);
            mSendData.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {

                    if (Sitename.getText().toString().isEmpty() || Username.getText().toString().isEmpty() || Password.getText().toString().isEmpty()) {
                        Toast.makeText(NewCredentialsHolder.this, "Please fill in all fields", Toast.LENGTH_SHORT).show();
                    } else {
                        FirebaseDatabase database = FirebaseDatabase.getInstance();
                        DatabaseReference databaseReference = database.getReference();
                        databaseReference.child(userid).child("Websites").child(Sitename.getText().toString()).child("URL").setValue(URL.getText().toString());
                        databaseReference.child(userid).child("Websites").child(Sitename.getText().toString()).child("Site Name").setValue(Sitename.getText().toString());
                        databaseReference.child(userid).child("Websites").child(Sitename.getText().toString()).child("Username").setValue(Username.getText().toString());
                        databaseReference.child(userid).child("Websites").child(Sitename.getText().toString()).child("Password").setValue(Password.getText().toString());
                        databaseReference.child(userid).child("Websites").child(Sitename.getText().toString()).child("Notes").setValue(Notes.getText().toString());

                        Toast.makeText(NewCredentialsHolder.this, "Your information has been stored successfully.", Toast.LENGTH_SHORT).show();
                        Intent back = new Intent(NewCredentialsHolder.this, HomeActivity.class);
                        startActivity(back);
                    }
                }
            });
            // -------------------------------------------------------------------------------------------------------------------------------------------------------------


            btnCancel = findViewById(R.id.cancel);
            btnCancel.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent cancel = new Intent(NewCredentialsHolder.this, HomeActivity.class);
                    startActivity(cancel);
                }
            });

            btnExit = findViewById(R.id.exit);
            btnExit.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent exit = new Intent(NewCredentialsHolder.this, HomeActivity.class);
                    startActivity(exit);
                }
            });
        }

        @Override
        public boolean onCreateOptionsMenu(Menu menu) {
            MenuInflater inflater = getMenuInflater();
            inflater.inflate(R.menu.menu, menu);
            return super.onCreateOptionsMenu(menu);
        }

        @Override
        public boolean onOptionsItemSelected(@NonNull MenuItem item) {
            switch(item.getItemId())
            {
                case R.id.action_vault:
                    Intent vault = new Intent(this, HomeActivity.class);
                    startActivity(vault);
                    break;
                case R.id.action_generate:
                    Intent generate = new Intent(this, GenerateActivity.class);
                    startActivity(generate);
                    break;
            }
            return super.onOptionsItemSelected(item);
        }
    }
