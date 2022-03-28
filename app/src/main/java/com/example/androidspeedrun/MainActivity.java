package com.example.androidspeedrun;

import androidx.appcompat.app.AppCompatActivity;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.widget.EditText;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {
    TextView mTextView;
    EditText mEditText;
    private final String KEY = "key";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mTextView = findViewById(R.id.textview);
        mEditText = findViewById(R.id.edittext);
        String text = getPreferences(MODE_PRIVATE).getString(KEY, null);
        if(text!=null)  mTextView.setText(text);
        findViewById(R.id.button).setOnClickListener(view -> {
            String text2 = mEditText.getText().toString();
            SharedPreferences.Editor editor = getPreferences(MODE_PRIVATE).edit();
            editor.putString(KEY, text2);
            editor.apply();
        });
    }

}