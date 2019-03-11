package com.noname.backgroundthreadexample;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;

public class MainActivity extends AppCompatActivity {

    // Declare global variables
    EditText input;
    ProgressBar loading;
    Button start;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Initialize global variables
        input = findViewById(R.id.et_input);
        loading = findViewById(R.id.pb_loading);
        start = findViewById(R.id.btn_start);
    }
}
