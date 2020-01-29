package com.core.storesafe.Navbar;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.core.storesafe.LoginActivity;
import com.core.storesafe.R;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class SettingsFragment extends Fragment {

    private FirebaseUser currentFirebaseUser = FirebaseAuth.getInstance().getCurrentUser();
    private String Uid = currentFirebaseUser.getUid();
    Button btnClose, btnSignout;
    TextView UID;

    @Nullable
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_settings, container, false);

        // Object Initialisation
        btnClose = view.findViewById(R.id.closeapp);
        btnSignout = view.findViewById(R.id.signout);
        UID = view.findViewById(R.id.userid);

        UID.setText("User ID: " + Uid);

        // Button onClick listeners
        btnClose.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                System.exit(0);
            }
        });

        btnSignout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FirebaseAuth.getInstance().signOut();
                Intent intent = new Intent(SettingsFragment.this.getActivity(), LoginActivity.class);
                startActivity(intent);
            }
        });
        return view;
    }
}
