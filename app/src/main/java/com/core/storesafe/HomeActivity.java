package com.core.storesafe;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.content.ClipData;
import android.content.ClipboardManager;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
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

public class HomeActivity extends AppCompatActivity {

    Button btnVault, btnGenerate, btnSettings, btnSearch;
    FirebaseUser currentFirebaseUser = FirebaseAuth.getInstance().getCurrentUser();
    String userid = currentFirebaseUser.getUid();
    TextView dataView;
    TextView url_container, name_container, username_container, password_container, notes_container;
    EditText siteToSearch;
    Button CopyUrl, CopyName, CopyUsername, CopyPassword, CopyNotes;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        Toolbar toolbar = (Toolbar) findViewById(R.id.app_bar);
        setSupportActionBar(toolbar);

        btnVault = findViewById(R.id.navVault);
        btnGenerate = findViewById(R.id.navGenerate);
        btnSettings = findViewById(R.id.navSettings);
        dataView = findViewById(R.id.dataView);
        siteToSearch = findViewById(R.id.searchedSitename);

        CopyUrl = findViewById(R.id.copy_url);
        CopyName = findViewById(R.id.copy_name);
        CopyUsername = findViewById(R.id.copy_username);
        CopyPassword = findViewById(R.id.copy_password);
        CopyNotes = findViewById(R.id.copy_notes);

        btnVault.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent vault = new Intent(HomeActivity.this, HomeActivity.class);
                startActivity(vault);
            }
        });

        btnGenerate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent generate = new Intent(HomeActivity.this, GenerateActivity.class);
                startActivity(generate);
            }
        });

        btnSettings.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent settings = new Intent(HomeActivity.this, SettingsActivity.class);
                startActivity(settings);
            }
        });

        CopyUrl.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                TextView CopiedURL = findViewById(R.id.url_Container);
                ClipboardManager clipboard = (ClipboardManager) getSystemService(Context.CLIPBOARD_SERVICE);
                ClipData clip = ClipData.newPlainText("Edit Text", CopiedURL.getText().toString());
                clipboard.setPrimaryClip(clip);

                Toast.makeText(HomeActivity.this, "Copied to clipboard", Toast.LENGTH_SHORT).show();
            }
        });

        CopyName.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                TextView CopiedName = findViewById(R.id.name_Container);
                ClipboardManager clipboard = (ClipboardManager) getSystemService(Context.CLIPBOARD_SERVICE);
                ClipData clip = ClipData.newPlainText("Edit Text", CopiedName.getText().toString());
                clipboard.setPrimaryClip(clip);

                Toast.makeText(HomeActivity.this, "Copied to clipboard", Toast.LENGTH_SHORT).show();
            }
        });

        CopyUsername.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                TextView CopiedUsername = findViewById(R.id.username_Container);
                ClipboardManager clipboard = (ClipboardManager) getSystemService(Context.CLIPBOARD_SERVICE);
                ClipData clip = ClipData.newPlainText("Edit Text", CopiedUsername.getText().toString());
                clipboard.setPrimaryClip(clip);

                Toast.makeText(HomeActivity.this, "Copied to clipboard", Toast.LENGTH_SHORT).show();
            }
        });

        CopyPassword.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                TextView CopiedPassword = findViewById(R.id.password_Container);
                ClipboardManager clipboard = (ClipboardManager) getSystemService(Context.CLIPBOARD_SERVICE);
                ClipData clip = ClipData.newPlainText("Edit Text", CopiedPassword.getText().toString());
                clipboard.setPrimaryClip(clip);

                Toast.makeText(HomeActivity.this, "Copied to clipboard", Toast.LENGTH_SHORT).show();
            }
        });

        CopyNotes.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                TextView CopiedNotes = findViewById(R.id.notes_Container);
                ClipboardManager clipboard = (ClipboardManager) getSystemService(Context.CLIPBOARD_SERVICE);
                ClipData clip = ClipData.newPlainText("Edit Text", CopiedNotes.getText().toString());
                clipboard.setPrimaryClip(clip);

                Toast.makeText(HomeActivity.this, "Copied to clipboard", Toast.LENGTH_SHORT).show();
            }
        });

        btnSearch = findViewById(R.id.search);
        btnSearch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                url_container = findViewById(R.id.url_Container);
                name_container = findViewById(R.id.name_Container);
                username_container = findViewById(R.id.username_Container);
                password_container = findViewById(R.id.password_Container);
                notes_container = findViewById(R.id.notes_Container);
                String searchedSite = siteToSearch.getText().toString();

                DatabaseReference rootRef = FirebaseDatabase.getInstance().getReference();
                final DatabaseReference userRef = rootRef.child(userid).child("Websites");
                final DatabaseReference siteRef = rootRef.child(userid).child("Websites").child(searchedSite);


                siteRef.addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                        Map<String, Object> map = (Map<String, Object>) dataSnapshot.getValue();
                        String URL = dataSnapshot.child("URL").getValue(String.class);
                        String Sitename = dataSnapshot.child("Site Name").getValue(String.class);
                        String Username = dataSnapshot.child("Username").getValue(String.class);
                        String Password = dataSnapshot.child("Password").getValue(String.class);
                        String Notes = dataSnapshot.child("Notes").getValue(String.class);

                        url_container.setText(URL);
                        name_container.setText(Sitename);
                        username_container.setText(Username);
                        password_container.setText(Password);
                        notes_container.setText(Notes);

                        //System.out.println(dataSnapshot.child("Username").getValue(String.class));

                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError databaseError) {

                    }
                });
            }
        });

        DatabaseReference rootRef = FirebaseDatabase.getInstance().getReference();
        final DatabaseReference userRef = rootRef.child(userid).child("Websites");
        final DatabaseReference siteRef = rootRef.child(userid).child("Websites").child("My Site");


        siteRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                Map<String, Object> map = (Map<String, Object>) dataSnapshot.getValue();
                dataSnapshot.child("Username").getValue(String.class);
                // System.out.println(dataSnapshot.child("Username").getValue(String.class));

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.home_menu, menu);
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
            case R.id.viewSites:
                Toast.makeText(HomeActivity.this, "Work in progress!", Toast.LENGTH_SHORT).show();
                break;
            case R.id.signout:
                FirebaseAuth.getInstance().signOut();
                Intent intToMain = new Intent(HomeActivity.this, MainActivity.class);
                startActivity(intToMain);
                break;
        }
        return super.onOptionsItemSelected(item);
    }
}
