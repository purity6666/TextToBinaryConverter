package com.techta.binaryconverter;

import androidx.appcompat.app.AppCompatActivity;

import android.content.ClipData;
import android.content.ClipboardManager;
import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    private Button convert, copy;
    private TextView resTxt;
    private EditText input;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        final ArrayList<String> wordsBinary = new ArrayList<>();

        convert = findViewById(R.id.convertBtn);
        copy = findViewById(R.id.copyBtn);
        resTxt = findViewById(R.id.binaryTxt);
        input = findViewById(R.id.toBinaryEditText);

        convert.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String[] words = input.getText().toString().split("\\W+");

                input.getText().clear();
                wordsBinary.clear();

                for (int i=0; i<words.length; i++) {

                    String s = words[i];
                    byte[] bytes = s.getBytes();
                    StringBuilder binary = new StringBuilder();

                    for (byte b : bytes) {
                        int val = b;

                        for (int ii = 0; ii < 8; ii++) {
                            binary.append((val & 128) == 0 ? 0 : 1);
                            val <<= 1;
                        }
                    }
                    wordsBinary.add(binary.toString());
                }

                StringBuilder sb2 = new StringBuilder();
                for (String wordBinary : wordsBinary) {
                    sb2.append(wordBinary);
                    sb2.append(" ");
                }

                resTxt.setText(sb2.toString());

                Toast.makeText(MainActivity.this, "Text converted", Toast.LENGTH_SHORT).show();
            }
        });

        copy.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                ClipboardManager clipboard = (ClipboardManager) getSystemService(Context.CLIPBOARD_SERVICE);
                ClipData clip = ClipData.newPlainText("random num", resTxt.getText().toString());
                clipboard.setPrimaryClip(clip);

                Toast.makeText(MainActivity.this, "Binary result copied to clipboard", Toast.LENGTH_SHORT).show();
            }
        });

    }
}