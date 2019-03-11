package com.noname.backgroundthreadexample;

import android.content.Context;
import android.os.AsyncTask;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    // Declare global variables
    EditText input;
    ProgressBar loading;
    Button start;
    Context context;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Initialize global variables
        init();
        context = MainActivity.this;
    }

    private void init() {
        input = findViewById(R.id.et_input);
        loading = findViewById(R.id.pb_loading);
        start = findViewById(R.id.btn_start);
        // Set the OnClickListener to this Activity(this) since it implements
        // View.OnClickListener
        start.setOnClickListener(MainActivity.this);
    }

    // This is from View.OnClickListener implementation
    @Override
    public void onClick(View view) {
        // Switch statement that uses the view Id to differientate the views
        switch (view.getId()) {
            case R.id.btn_start:
                /*
                // This is a non global implementation stores the new Instance
                // in a variable
                // We can use that variable to access the methods
                AsyncTaskCounter taskCounter = new AsyncTaskCounter();
                taskCounter.execute();
                */

                // This is a inline implementation where we directly access the method
                new AsyncTaskCounter().execute();
                break;
        }
    }

    // Create AsyncTask to run task in the background
    private class AsyncTaskCounter extends AsyncTask<Void, Void, Void> {


        private static final String TAG = "AsyncTaskCounter";

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            Log.d(TAG, "onPreExecute: ");
            Toast.makeText(MainActivity.this, "onPreExecute()", Toast.LENGTH_SHORT).show();
            loading.setVisibility(View.VISIBLE);
        }

        @Override
        protected Void doInBackground(Void... voids) {
            Log.d(TAG, "doInBackground: ");

            // Create instance of Handler for UI thread(MainActivity)
            // context.getMainLooper() sets the Thread focus to the UI
           Handler mainHandler = new Handler(context.getMainLooper());
           // This is the Code we want to run
           Runnable toastRunnable = new Runnable() {
               @Override
               public void run() {
                   start.setVisibility(View.GONE);
                   Toast.makeText(context, "doInBackground: ", Toast.LENGTH_SHORT).show();
               }
           };
           // We use the Handler to run the Runnable
           mainHandler.post(toastRunnable);

            try {
                Thread.sleep(5000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

            return null;
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            super.onPostExecute(aVoid);
            Log.d(TAG, "onPostExecute: ");
            Toast.makeText(MainActivity.this, "onPostExecute()", Toast.LENGTH_SHORT).show();
            loading.setVisibility(View.GONE);
            start.setVisibility(View.VISIBLE);
        }
    }
}
