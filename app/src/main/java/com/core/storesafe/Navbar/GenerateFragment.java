package com.core.storesafe.Navbar;

import android.content.ClipData;
import android.content.ClipboardManager;
import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.SeekBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.core.storesafe.R;

import java.util.Random;

public class GenerateFragment extends Fragment {

    // Variable
    RadioGroup radioGroup;
    RadioButton radioButton;
    TextView generatedPassword, text;
    int totalLength;
    String pass;
    CharSequence mode;
    Button buttonGenerate, buttonCopy;



    public static String randomString(int length, String mode) {
        char[] all = "abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ123456789*&^%!$@#.".toCharArray();
        char[] scharacters = "*&^%!$@#.".toCharArray();
        char[] numbers = "123456789".toCharArray();
        char[] letters = "abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ".toCharArray();
        StringBuilder rstring = new StringBuilder(length);
        Random random = new Random();
        for (int i = 0; i < length; i++) {
            if(mode.equals("All")){char c = all[random.nextInt(all.length)]; rstring.append(c);}
            else if(mode.equals("Letters")){char c = letters[random.nextInt(letters.length)]; rstring.append(c);}
            else if(mode.equals("Numbers")){char c = numbers[random.nextInt(numbers.length)]; rstring.append(c);}
            else if(mode.equals("Special Characters")){char c = scharacters[random.nextInt(scharacters.length)]; rstring.append(c);}
        }
        String output = rstring.toString();
        return output;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        final View view = inflater.inflate(R.layout.fragment_generate, container, false);

        // Object Initialisation
        radioGroup = view.findViewById(R.id.radioGroup);
        generatedPassword = view.findViewById(R.id.generatedPassword);
        buttonGenerate = view.findViewById(R.id.generate);
        buttonCopy = view.findViewById(R.id.Copy);

        // Seek Bar Animation
        final SeekBar seekBar = view.findViewById(R.id.seekBar);
        final TextView seekBarValue = view.findViewById(R.id.length);

        seekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener(){

            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                seekBarValue.setText("Password Length: " + String.valueOf(progress));
                totalLength = progress;
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {
            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {
            }
        });

        // Button onClick listeners
        buttonGenerate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int radioId = radioGroup.getCheckedRadioButtonId();
                radioButton = view.findViewById(radioId);
                mode = radioButton.getText();
                pass = randomString(totalLength, mode.toString());
                generatedPassword.setText(pass);
            }
        });

        buttonCopy.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                text = view.findViewById(R.id.generatedPassword);
                ClipboardManager clipboard = (ClipboardManager) getActivity().getSystemService(Context.CLIPBOARD_SERVICE);
                ClipData clip = ClipData.newPlainText("Edit Text", text.getText().toString());
                clipboard.setPrimaryClip(clip);

                Toast.makeText(GenerateFragment.this.getActivity(), "Copied to clipboard", Toast.LENGTH_SHORT).show();
            }
        });

        return view;

    }
}
