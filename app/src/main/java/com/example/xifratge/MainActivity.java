package com.example.xifratge;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.*;
import android.widget.*;

public class MainActivity extends AppCompatActivity {

    private Button decodeButton;
    private TextView textField;
    private TextView textEncoded;
    private TextView textDecoded;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        this.textField = findViewById(R.id.editText);
        this.textEncoded = findViewById(R.id.editText);
        this.textDecoded = findViewById(R.id.textView);
        this.decodeButton = findViewById(R.id.button);

    }
}
