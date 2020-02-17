package edu.ucsd.cse110.walkwalkrevolution;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.SystemClock;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Chronometer;
import android.widget.TextView;

import com.google.android.gms.common.data.DataBufferObserver;

import java.time.LocalDateTime;
import java.util.Observable;
import java.util.Observer;
import java.util.Timer;
import java.util.TimerTask;

import edu.ucsd.cse110.walkwalkrevolution.activity.ActivityUtils;
import edu.ucsd.cse110.walkwalkrevolution.activity.Walk;
import edu.ucsd.cse110.walkwalkrevolution.fitness.StepSubject;
import edu.ucsd.cse110.walkwalkrevolution.fitness.Steps;

public class WalkActivity extends AppCompatActivity implements Observer {

    private final int MOCK_ID = 0;

    private static final String TAG = "WalkActivity";

    long walkSteps;
    long currentTotalSteps;

    private TextView steps;
    private TextView miles;
    private TextView routeTitle;
    private TextView timer;

    private Button stopWalk, mock;

    private LocalDateTime start;
    private Chronometer chronometer;
    private Steps stepTracker;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_walk);

        findViewById(R.id.route_title).setVisibility(View.INVISIBLE);

        stepTracker = WalkWalkRevolution.getSteps();
        if(!getIntent().hasExtra("test")){
            HomeActivity.getStepSubject().addObserver(this);
        }

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
        if(getIntent().hasExtra("route_title")){
            routeTitle.setText(getIntent().getExtras().getString("route_title"));
            routeTitle.setVisibility(View.VISIBLE);
        }

        mock = findViewById(R.id.mock_button);
        mock.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startMock();
            }
        });

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
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        Log.d(TAG, "onActivityResult: " + requestCode);
        if (requestCode == MOCK_ID) {
            int signal = data.getIntExtra("signal", 1);

            if(signal == 0) {
                walkSteps += data.getLongExtra("steps", 0);
                long time = data.getLongExtra("time", 0);
                long base = (SystemClock.elapsedRealtime() -
                        (SystemClock.elapsedRealtime() - chronometer.getBase() + time));
                chronometer.setBase(base);
                WalkWalkRevolution.setTimeOffset(time);
            }

            Log.d(TAG, "onActivityResult: " + walkSteps + ", " + WalkWalkRevolution.getTimeOffset());
        }
    }

    @Override
    protected void onResume(){
        super.onResume();
        updateWalkSteps();
    }

    private void startMock(){
        Intent intent = new Intent(this, MockActivity.class);
        startActivityForResult(intent, MOCK_ID, null);
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

    public void updateWalkSteps(){
        Log.d(TAG, "updateWalkSteps: UPDATING " + stepTracker.getLatest());
        walkSteps += stepTracker.getLatest();

        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                steps.setText(String.valueOf(walkSteps));
                miles.setText(String.valueOf(Math.round(
                        ActivityUtils.stepsToMiles(walkSteps, WalkWalkRevolution.getUser().getHeight()) * 100.0) / 100.0));
            }
        });

    }

    public void setSteps(Steps steps){
        this.stepTracker = steps;
    }
}
