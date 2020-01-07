package com.core.storesafe;

import android.app.Activity;
import android.content.ClipData;
import android.content.ClipboardManager;
import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.SeekBar;
import android.widget.TextView;
import android.widget.Toast;

import org.w3c.dom.Text;

import java.util.Random;

public class GenerateActivity extends Activity {

    RadioGroup radioGroup;
    RadioButton radioButton;
    TextView generatedPassword;
    TextView text;
    int totalLength;
    String pass;
    CharSequence mode;
    private static final String Number = "1234567890";

    public static String randomLetters(int length) {
        char[] all = "abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ".toCharArray();
        StringBuilder rstring = new StringBuilder(length);
        Random random = new Random();
        for (int i = 0; i < length; i++) {
            char c = all[random.nextInt(all.length)];
            rstring.append(c);
        }
        String output = rstring.toString();
        return output;
    }

    public static String randomChars(int length) {
        char[] all = "*&^%!$@#.".toCharArray();
        StringBuilder rstring = new StringBuilder(length);
        Random random = new Random();
        for (int i = 0; i < length; i++) {
            char c = all[random.nextInt(all.length)];
            rstring.append(c);
        }
        String output = rstring.toString();
        return output;
    }

    public static String randomNumbers(int length) {
        char[] all = "123456789".toCharArray();
        StringBuilder rstring = new StringBuilder(length);
        Random random = new Random();
        for (int i = 0; i < length; i++) {
            char c = all[random.nextInt(all.length)];
            rstring.append(c);
        }
        String output = rstring.toString();
        return output;
    }

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


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_generate);
        radioGroup = findViewById(R.id.radioGroup);
        generatedPassword = findViewById(R.id.generatedPassword);


        final SeekBar seekBar = (SeekBar)findViewById(R.id.seekBar);
        final TextView seekBarValue = (TextView)findViewById(R.id.length);

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

        Button buttonGenerate = findViewById(R.id.Generate);
        buttonGenerate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int radioId = radioGroup.getCheckedRadioButtonId();
                radioButton = findViewById(radioId);
                mode = radioButton.getText();
                pass = randomString(totalLength, mode.toString());
                generatedPassword.setText(pass);
            }
        });

        Button buttonCopy = findViewById(R.id.Copy);
        buttonCopy.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                text = findViewById(R.id.generatedPassword);
                ClipboardManager clipboard = (ClipboardManager) getSystemService(Context.CLIPBOARD_SERVICE);
                ClipData clip = ClipData.newPlainText("Edit Text", text.getText().toString());
                clipboard.setPrimaryClip(clip);

                Toast.makeText(GenerateActivity.this, "Copied to clipboard", Toast.LENGTH_SHORT).show();
            }
        });
    }

    public void checkButton(View v) {
        int radioId = radioGroup.getCheckedRadioButtonId();

        radioButton = findViewById(radioId);
    }
}