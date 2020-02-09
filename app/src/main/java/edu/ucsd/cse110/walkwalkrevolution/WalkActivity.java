package edu.ucsd.cse110.walkwalkrevolution;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Chronometer;
import android.widget.TextView;

import java.util.Timer;
import java.util.TimerTask;

public class WalkActivity extends AppCompatActivity {
    private static final String TAG = "WalkActivity";

    long walkSteps ;
    long currentTotalSteps;

    private TextView steps;
    private TextView miles;
    private TextView routeTitle;
    private TextView timer;

    private Button stopWalk;

    private SharedPreferences activityHistory;
    private SharedPreferences userInfo;

    private Chronometer chronometer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_walk);

        findViewById(R.id.route_title).setVisibility(View.INVISIBLE);

        activityHistory = getSharedPreferences("activity_history", MODE_PRIVATE);
        userInfo        = getSharedPreferences("USER", MODE_PRIVATE);

        //  How many steps we begin with.
        walkSteps = 0;

        // How many steps we have to ignore from  home screen.
        currentTotalSteps = getSharedPreferences("activity_history", MODE_PRIVATE).getLong("current_steps",  0);

        steps      = findViewById(R.id.steps);
        miles      = findViewById(R.id.miles);
        routeTitle = findViewById(R.id.route_title);
        timer      = findViewById(R.id.timer);

        steps.setText("0");
        miles.setText("0.0");


        // Check if a route title was passed in as an extra.
        Intent intent = getIntent();
        if(intent.hasExtra("route_title")){
            routeTitle.setText(intent.getExtras().getString("route_title"));
            routeTitle.setVisibility(View.VISIBLE);
        }

        //  Exit when stop walk clicked.
        stopWalk = findViewById(R.id.stop_walk);
        stopWalk.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

        chronometer = findViewById(R.id.timer);
        chronometer.start();

        SetWalkStepsAsync updater = new SetWalkStepsAsync();
        updater.executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR);
    }

    private class SetWalkStepsAsync extends AsyncTask<String, String, String> {

        @Override
        protected String doInBackground(String... params) {
            Log.d(TAG, "doInBackground: Entering");

            long waitTime = 1000*((R.string.daily_step_update_delay_sec));
            while(true){
                try {
                    Log.d(TAG, "doInBackground: Updating steps");
                    publishProgress();

                    Thread.sleep(waitTime);
                } catch (Exception e) {
                    Log.d(TAG, "doInBackground: ERROR BIG ERROR");
                    e.printStackTrace();
                    while(true){}
                }
            }
        }

        @Override
        public void onProgressUpdate(String... text){
            updateWalkSteps();
        }
    }

    public void updateWalkSteps(){
        Log.d(TAG, "updateWalkSteps: Entering");

        long newTotal = activityHistory.getLong("current_steps", 0);
        float userStepsPerMile = userInfo.getFloat("steps_per_mile", 0);

        if(newTotal < currentTotalSteps){
            walkSteps += newTotal;
        } else {
            walkSteps += newTotal  - currentTotalSteps;
        }

        Log.d(TAG, "updateWalkSteps: Updated Walk Specific stats!");
        Log.d(TAG, "updateWalkSteps: Current total " + currentTotalSteps);
        Log.d(TAG, "updateWalkSteps: New total " + newTotal);
        Log.d(TAG, "updateWalkSteps: walkSteps " + walkSteps);

        currentTotalSteps =  newTotal;

        steps.setText(String.valueOf(walkSteps));
        miles.setText(String.valueOf(Math.round(walkSteps / userStepsPerMile * 100.0) / 100.0));
    }
}
