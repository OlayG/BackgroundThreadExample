package com.noname.backgroundthreadexample;

import android.content.Context;
import android.os.AsyncTask;
import android.os.Handler;
import android.support.constraint.ConstraintLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Locale;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    // Declare global variables
    EditText input;
    ConstraintLayout loading;
    Button start;
    Context context;
    TextView result;

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
        result = findViewById(R.id.tv_result);
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

         /*       // This is a inline implementation where we directly access the method
                new AsyncTaskCounter().execute();*/

                // This is a inline implementation where we directly access the method
                // We are passing in a parameter in the execute method
                Integer sleepInSeconds = Integer.valueOf(input.getText().toString());
                new AsyncTaskWithParamsResult().execute(sleepInSeconds, sleepInSeconds * 2, sleepInSeconds * 3);
                break;
        }
    }

    // Create AsyncTask to run task in the background No Params, Progress, Result
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

    // Create AsyncTask to run task in the background With Params No Progress, Result
    private class AsyncTaskWithParams extends AsyncTask<Integer, Void, Void> {

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            loading.setVisibility(View.VISIBLE);
        }

        @Override
        protected Void doInBackground(Integer... nums) {
            // Create instance of Handler for UI thread(MainActivity)
            // context.getMainLooper() sets the Thread focus to the UI
            Handler mainHandler = new Handler(context.getMainLooper());
            // This is the Code we want to run
            Runnable toastRunnable = new Runnable() {
                @Override
                public void run() {
                    Toast.makeText(context, "doInBackground: ", Toast.LENGTH_SHORT).show();
                }
            };
            // We use the Handler to run the Runnable
            mainHandler.post(toastRunnable);

/*            // Example of inline Handler
            new Handler(context.getMainLooper()).post(new Runnable() {
                @Override
                public void run() {
                    Toast.makeText(context, "doInBackground: ", Toast.LENGTH_SHORT).show();
                }
            });*/

            // create a int variable sleep
            int sleep = nums[0];
            int sleepInMilis = sleep * 1000;

            try {
                Thread.sleep(sleepInMilis);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            return null;
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            super.onPostExecute(aVoid);
            loading.setVisibility(View.GONE);
        }
    }

    // Create AsyncTask to run task in the background With Params & Result No Progress
    private class AsyncTaskWithParamsResult extends AsyncTask<Integer, Void, String> {

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            loading.setVisibility(View.VISIBLE);
        }

        @Override
        protected String doInBackground(Integer... nums) {

            // Example of inline Handler
            new Handler(context.getMainLooper()).post(new Runnable() {
                @Override
                public void run() {
                    Toast.makeText(context, "doInBackground: ", Toast.LENGTH_SHORT).show();
                }
            });

            // create a int variable sleep
            int sleep = nums[0];
            int sleepInMilis = sleep * 1000;

            try {
                Thread.sleep(sleepInMilis);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

            String result = String.format(Locale.getDefault(), "Ran progress for %d seconds", nums[0]);
            return result;
        }

        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);
            loading.setVisibility(View.GONE);
            Toast.makeText(context, s, Toast.LENGTH_SHORT).show();
            result.setText(s);
        }
    }


}
