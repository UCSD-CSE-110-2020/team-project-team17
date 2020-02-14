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

import com.google.android.gms.common.data.DataBufferObserver;

import java.util.Observable;
import java.util.Observer;
import java.util.Timer;
import java.util.TimerTask;

import edu.ucsd.cse110.walkwalkrevolution.activity.Walk;
import edu.ucsd.cse110.walkwalkrevolution.fitness.StepSubject;
import edu.ucsd.cse110.walkwalkrevolution.fitness.Steps;

public class WalkActivity extends AppCompatActivity implements Observer {
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
    private Steps stepTracker;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_walk);

        findViewById(R.id.route_title).setVisibility(View.INVISIBLE);

        stepTracker = WalkWalkRevolution.getSteps();
        HomeActivity.getStepSubject().addObserver(this);

        userInfo        = getSharedPreferences("USER", MODE_PRIVATE);

        //  How many steps we begin with.
        walkSteps = 0;

        // How many steps we have to ignore from  home screen.
        currentTotalSteps = stepTracker.getDailyTotal();

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
                createRouteActivity();
            }
        });

        chronometer = findViewById(R.id.timer);
        chronometer.start();

        SetWalkStepsAsync updater = new SetWalkStepsAsync();
        updater.executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR);
    }

    public void createRouteActivity() {
        Intent createRoute = new Intent(this, CreateRouteActivity.class);
        createRoute.putExtra(Walk.STEP_COUNT, steps.getText().toString());
        createRoute.putExtra(Walk.MILES, miles.getText().toString());
        createRoute.putExtra(Walk.DURATION, timer.getText().toString());
        startActivity(createRoute);
    }

    @Override
    public void update(Observable observable, Object o) {
        Log.d(TAG, "update: Walk Screen");
        updateWalkSteps();
    }

    private class SetWalkStepsAsync extends AsyncTask<String, String, String> {

        @Override
        protected String doInBackground(String... params) {
            Log.d(TAG, "doInBackground: Entering");

            long waitTime = 1000*Integer.parseInt(getString(R.string.daily_step_update_delay_sec));
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
        Log.d(TAG, "updateWalkSteps: UPDATING");
        float userStepsPerMile = userInfo.getFloat("steps_per_mile", 0);
        walkSteps += stepTracker.getLatest();

        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                steps.setText(String.valueOf(walkSteps));
                miles.setText(String.valueOf(Math.round(walkSteps / userStepsPerMile * 100.0) / 100.0));
            }
        });

    }
}
