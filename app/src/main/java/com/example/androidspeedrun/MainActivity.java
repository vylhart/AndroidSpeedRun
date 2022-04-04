package com.example.androidspeedrun;

import androidx.appcompat.app.AppCompatActivity;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.widget.EditText;
import android.widget.TextView;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;

public class MainActivity extends AppCompatActivity {
    TextView mTextView;
    EditText mEditText;
    private final String PATH = "textfile.txt";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mTextView = findViewById(R.id.textview);
        mEditText = findViewById(R.id.edittext);
        String text = readFile();
        if(text!=null)  mTextView.setText(text);
        findViewById(R.id.button).setOnClickListener(view -> {
            String text2 = mEditText.getText().toString();
            if(!text2.isEmpty()){
                mTextView.setText(text2);
                writeFile(text2);
            }
        });
    }

    private void writeFile(String text){
        try {
            OutputStream out = openFileOutput(PATH, MODE_PRIVATE);
            out.write(text.getBytes());
            out.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private String readFile(){
        StringBuilder str = new StringBuilder();
        try {

            InputStream in = openFileInput(PATH);
            BufferedReader br = new BufferedReader(new InputStreamReader(in));
            String text  = null;
            while((text = br.readLine()) != null){
                str.append(text).append("\n");
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return String.valueOf(str);

    }
}