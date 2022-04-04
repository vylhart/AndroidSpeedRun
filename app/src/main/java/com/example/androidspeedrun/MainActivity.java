package com.example.androidspeedrun;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import android.Manifest;
import android.app.Activity;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.os.Environment;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;

public class MainActivity extends AppCompatActivity {
    TextView mTextView;
    EditText mEditText;
    private final String PATH = "textfile.txt";
    private final String PERMISSION_STORAGE[] = {
            Manifest.permission.READ_EXTERNAL_STORAGE,
            Manifest.permission.WRITE_EXTERNAL_STORAGE
    };


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mTextView = findViewById(R.id.textview);
        mEditText = findViewById(R.id.edittext);
        String text = readFile();
        mTextView.setText(text);
        findViewById(R.id.button).setOnClickListener(view -> {
            String text2 = mEditText.getText().toString();
            if(!text2.isEmpty()){
                mTextView.setText(text2);
                writeFile(text2);
            }
        });
    }

    private boolean isExternalStorageWriteable(){
        return Environment.MEDIA_MOUNTED.equals(Environment.getExternalStorageState());
    }

    private boolean isExternalStorageReadable(){
        return Environment.getExternalStorageState().equals(Environment.MEDIA_MOUNTED) ||
                Environment.getExternalStorageState().equals(Environment.MEDIA_MOUNTED_READ_ONLY);
    }

    private void checkStoragePermission(){
        int permission = ActivityCompat.checkSelfPermission(this, PERMISSION_STORAGE[1]);
        if(permission != PackageManager.PERMISSION_GRANTED){
            ActivityCompat.requestPermissions(this, PERMISSION_STORAGE, 101);
        }
    }



    private void writeFile(String text){
        if(isExternalStorageWriteable()){
            checkStoragePermission();
            File file = new File(Environment.getExternalStorageDirectory(), PATH);
            try {
                OutputStream out = new FileOutputStream(file);
                out.write(text.getBytes());
                out.close();
            } catch (IOException e) {
                e.printStackTrace();
                Toast.makeText(this, "Error writing", Toast.LENGTH_SHORT).show();
            }

        }
        else
            Toast.makeText(this, "No writing permission", Toast.LENGTH_SHORT).show();

    }

    private String readFile(){
        if(isExternalStorageReadable()){
            checkStoragePermission();
            StringBuilder str = new StringBuilder();
            try {
                File file = new File(Environment.getExternalStorageDirectory(), PATH);
                InputStream in = new FileInputStream(file);
                BufferedReader br = new BufferedReader(new InputStreamReader(in));
                String text;
                while((text = br.readLine()) != null){
                    str.append(text).append("\n");
                }
            } catch (IOException e) {
                e.printStackTrace();
                return "Error";
            }
            return String.valueOf(str);
        }
        else{
            return "No permission";
        }

    }
}