package com.core.storesafe.Navbar;

import android.content.ClipData;
import android.content.ClipboardManager;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Base64;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;

import com.core.storesafe.HomeActivity;
import com.core.storesafe.LoginActivity;
import com.core.storesafe.R;
import com.core.storesafe.AddActivity;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.security.MessageDigest;
import java.util.Map;

import javax.crypto.Cipher;
import javax.crypto.spec.SecretKeySpec;

public class HomeFragment extends Fragment {

    private FirebaseUser currentFirebaseUser = FirebaseAuth.getInstance().getCurrentUser();
    private String userid = currentFirebaseUser.getUid();
    private TextView dataView, url_container, name_container, username_container, password_container, notes_container;
    private EditText siteToSearch;
    private Button btnSearch, CopyUrl, CopyName, CopyUsername, CopyPassword, CopyNotes;
    private String encPass, decPass;
    String AES = "AES";

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        final View view = inflater.inflate(R.layout.fragment_vault, container, false);

        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);

        // Top Toolbar Initialization
        Toolbar toolbar = (Toolbar) view.findViewById(R.id.app_bar);
        ((HomeActivity)getActivity()).setSupportActionBar(toolbar);

        // Object Assignment
        dataView = view.findViewById(R.id.url);
        siteToSearch = view.findViewById(R.id.searchedSitename);
        btnSearch = view.findViewById(R.id.search);
        CopyUrl = view.findViewById(R.id.copy_url);
        CopyName = view.findViewById(R.id.copy_name);
        CopyUsername = view.findViewById(R.id.copy_username);
        CopyPassword = view.findViewById(R.id.copy_password);
        CopyNotes = view.findViewById(R.id.copy_notes);

        // Search onClick Listener
        btnSearch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                url_container = view.findViewById(R.id.url_Container);
                name_container = view.findViewById(R.id.name_Container);
                username_container = view.findViewById(R.id.username_Container);
                password_container = view.findViewById(R.id.password_Container);
                notes_container = view.findViewById(R.id.notes_Container);

                String searchedSite = siteToSearch.getText().toString();
                DatabaseReference rootRef = FirebaseDatabase.getInstance().getReference();
                final DatabaseReference siteRef = rootRef.child(userid).child("Websites").child(searchedSite);


                // Search For Site Value Listener
                siteRef.addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                        // Creates a map of elements stored in the database
                        try{
                            Map<String, Object> map = (Map<String, Object>) dataSnapshot.getValue();
                        } catch (Exception E){
                            return;
                        }


                        encPass = dataSnapshot.child("Password").getValue(String.class);


                        try {
                            decPass = decrypt(encPass, userid);
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                        url_container.setText(dataSnapshot.child("URL").getValue(String.class));
                        name_container.setText(dataSnapshot.child("Site Name").getValue(String.class));
                        username_container.setText(dataSnapshot.child("Username").getValue(String.class));
                        password_container.setText(decPass);
                        notes_container.setText(dataSnapshot.child("Notes").getValue(String.class));
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError databaseError) {
                        // Listener Requirement
                    }
                });
            }
        });

        CopyUrl.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                TextView CopiedURL = view.findViewById(R.id.url_Container);
                ClipboardManager clipboard = (ClipboardManager) getActivity().getSystemService(Context.CLIPBOARD_SERVICE);
                ClipData clip = ClipData.newPlainText("Edit Text", CopiedURL.getText().toString());
                clipboard.setPrimaryClip(clip);

                Toast.makeText(HomeFragment.this.getActivity(), "Copied to clipboard", Toast.LENGTH_SHORT).show();
            }
        });

        CopyName.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                TextView CopiedName = view.findViewById(R.id.name_Container);
                ClipboardManager clipboard = (ClipboardManager) getActivity().getSystemService(Context.CLIPBOARD_SERVICE);
                ClipData clip = ClipData.newPlainText("Edit Text", CopiedName.getText().toString());
                clipboard.setPrimaryClip(clip);

                Toast.makeText(HomeFragment.this.getActivity(), "Copied to clipboard", Toast.LENGTH_SHORT).show();
            }
        });

        CopyUsername.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                TextView CopiedUsername = view.findViewById(R.id.username_Container);
                ClipboardManager clipboard = (ClipboardManager) getActivity().getSystemService(Context.CLIPBOARD_SERVICE);
                ClipData clip = ClipData.newPlainText("Edit Text", CopiedUsername.getText().toString());
                clipboard.setPrimaryClip(clip);

                Toast.makeText(HomeFragment.this.getActivity(), "Copied to clipboard", Toast.LENGTH_SHORT).show();
            }
        });

        CopyPassword.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                TextView CopiedPassword = view.findViewById(R.id.password_Container);
                ClipboardManager clipboard = (ClipboardManager) getActivity().getSystemService(Context.CLIPBOARD_SERVICE);
                ClipData clip = ClipData.newPlainText("Edit Text", CopiedPassword.getText().toString());
                clipboard.setPrimaryClip(clip);

                Toast.makeText(HomeFragment.this.getActivity(), "Copied to clipboard", Toast.LENGTH_SHORT).show();
            }
        });

        CopyNotes.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                TextView CopiedNotes = view.findViewById(R.id.notes_Container);
                ClipboardManager clipboard = (ClipboardManager) getActivity().getSystemService(Context.CLIPBOARD_SERVICE);
                ClipData clip = ClipData.newPlainText("Edit Text", CopiedNotes.getText().toString());
                clipboard.setPrimaryClip(clip);

                Toast.makeText(HomeFragment.this.getActivity(), "Copied to clipboard", Toast.LENGTH_SHORT).show();
            }
        });

        String searchedSite = siteToSearch.getText().toString();
        DatabaseReference rootRef = FirebaseDatabase.getInstance().getReference();
        final DatabaseReference siteRef = rootRef.child(userid).child("Websites").child(searchedSite);

        siteRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                try {
                    Map<String, Object> map = (Map<String, Object>) dataSnapshot.getValue();
                    dataSnapshot.child("Username").getValue(String.class);
                } catch (Exception e) {
                    return;
                }

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                // Listener Requirement
            }
        });
        return view;
    }

    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        super.onCreateOptionsMenu(menu, inflater);
        inflater.inflate(R.menu.home_menu, menu);
    }

    // Dots Options Menu
    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch(item.getItemId())
        {
            case R.id.add_account:
                Intent vault = new Intent(HomeFragment.this.getActivity(), AddActivity.class);
                startActivity(vault);
                break;
            case R.id.viewSites:
                Toast.makeText(HomeFragment.this.getActivity(), "Work in progress!", Toast.LENGTH_SHORT).show();
                break;
            case R.id.signout:
                FirebaseAuth.getInstance().signOut();
                Intent intToMain = new Intent(HomeFragment.this.getActivity(), LoginActivity.class);
                startActivity(intToMain);
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    private String decrypt(String EncData, String Userid) throws Exception {
        SecretKeySpec key = generateKey(Userid);
        Cipher c = Cipher.getInstance(AES);
        c.init(Cipher.DECRYPT_MODE, key);
        byte[] decodedData = Base64.decode(EncData, Base64.DEFAULT);
        byte[] decValue = c.doFinal(decodedData);
        String decryptedValue = new String(decValue);
        return decryptedValue;
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

