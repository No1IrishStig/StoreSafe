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
        private TextView mUserdataView;
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

            // Send Data Button Function -----------------------------------------------------------------------------------------------------------------------------------

            mSendData = (Button) findViewById(R.id.save);
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
                    }
                }
            });
            // -------------------------------------------------------------------------------------------------------------------------------------------------------------

            // -------------------------------------------------------------------------------------------------------------------------------------------------------------
            /*btnSendData = findViewById(R.id.fetchData);
            btnSendData.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    mUserdataView = findViewById(R.id.dataView);
                    }
             */
            DatabaseReference rootRef = FirebaseDatabase.getInstance().getReference();
            DatabaseReference userRef = rootRef.child(userid).child("Websites");

            userRef.addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                    Map<String, Object> map = (Map<String, Object>) dataSnapshot.getValue();

                    //mUserdataView.setText(map.toString());

                    System.out.println(dataSnapshot.getChildren());
                }

                @Override
                public void onCancelled(@NonNull DatabaseError databaseError) {

                }
            });

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
                case R.id.add_account:
                    Intent vault = new Intent(this, NewCredentialsHolder.class);
                    startActivity(vault);
                    break;
                case R.id.find_credentials:
                    Intent generate = new Intent(this, GenerateActivity.class);
                    startActivity(generate);
                    break;
            }
            return super.onOptionsItemSelected(item);
        }


    }
