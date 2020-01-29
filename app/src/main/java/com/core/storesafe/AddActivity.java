package com.core.storesafe;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.content.Intent;
import android.os.Bundle;
import android.util.Base64;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.security.MessageDigest;

import javax.crypto.Cipher;
import javax.crypto.spec.SecretKeySpec;

public class AddActivity extends AppCompatActivity {

    // Variable Assignment
    private Button mSendData, btnCancel, btnExit;
    EditText URL, Sitename, Username, Password, Notes;
    FirebaseUser currentFirebaseUser = FirebaseAuth.getInstance().getCurrentUser();
    String userid = currentFirebaseUser.getUid();
    String AES = "AES";
    String OutputText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add);

        // Toolbar
        Toolbar toolbar = (Toolbar) findViewById(R.id.app_bar);
        setSupportActionBar(toolbar);

        // Object Initialisation
        URL = findViewById(R.id.url);
        Sitename = findViewById(R.id.sitename);
        Username = findViewById(R.id.username);
        Password = findViewById(R.id.password);
        Notes = findViewById(R.id.notes);

        // Save Button Function
        mSendData = findViewById(R.id.save);
        mSendData.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if (Sitename.getText().toString().isEmpty() || Username.getText().toString().isEmpty() || Password.getText().toString().isEmpty()) {
                    Toast.makeText(AddActivity.this, "Please fill in all fields", Toast.LENGTH_SHORT).show();
                } else {
                    FirebaseDatabase database = FirebaseDatabase.getInstance();
                    DatabaseReference databaseReference = database.getReference();

                    // Encryption
                    try {
                        OutputText = encrypt(Password.getText().toString(), userid);
                        System.out.println(OutputText);
                    } catch (Exception e) {
                        e.printStackTrace();
                    }

                    databaseReference.child(userid).child("Websites").child(Sitename.getText().toString()).child("URL").setValue(URL.getText().toString());
                    databaseReference.child(userid).child("Websites").child(Sitename.getText().toString()).child("Site Name").setValue(Sitename.getText().toString());
                    databaseReference.child(userid).child("Websites").child(Sitename.getText().toString()).child("Username").setValue(Username.getText().toString());
                    databaseReference.child(userid).child("Websites").child(Sitename.getText().toString()).child("Password").setValue(OutputText);
                    databaseReference.child(userid).child("Websites").child(Sitename.getText().toString()).child("Notes").setValue(Notes.getText().toString());

                    Toast.makeText(AddActivity.this, "Storing Successful", Toast.LENGTH_SHORT).show();
                    Intent back = new Intent(AddActivity.this, HomeActivity.class);
                    startActivity(back);
                }
            }
        });

        // Button Listeners
        btnCancel = findViewById(R.id.cancel);
        btnCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(v.getContext(), HomeActivity.class));

            }
        });


        btnExit = findViewById(R.id.exit);
        btnExit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(v.getContext(), HomeActivity.class));
            }
        });

    }

    private String encrypt(String Data, String Userid) throws Exception {
        SecretKeySpec key = generateKey(Userid);
        Cipher c = Cipher.getInstance(AES);
        c.init(Cipher.ENCRYPT_MODE, key);
        byte[] encVal = c.doFinal(Data.getBytes());
        String encryptedValue = Base64.encodeToString(encVal, Base64.DEFAULT);
        return encryptedValue;
    }

    private SecretKeySpec generateKey(String password) throws Exception {
        final MessageDigest digest = MessageDigest.getInstance("SHA-256");
        byte[] bytes = password.getBytes("UTF-8");
        digest.update(bytes, 0, bytes.length);
        byte[] key = digest.digest();
        SecretKeySpec secretkeySpec = new SecretKeySpec(key, "AES");
        return secretkeySpec;
    }
}
