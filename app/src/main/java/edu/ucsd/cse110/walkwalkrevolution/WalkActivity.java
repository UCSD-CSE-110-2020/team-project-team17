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

import java.util.HashMap;
import java.util.Map;
import java.util.Observable;
import java.util.Observer;
import java.util.Timer;
import java.util.TimerTask;

import edu.ucsd.cse110.walkwalkrevolution.activity.Activity;
import edu.ucsd.cse110.walkwalkrevolution.activity.ActivityUtils;
import edu.ucsd.cse110.walkwalkrevolution.activity.Walk;
import edu.ucsd.cse110.walkwalkrevolution.fitness.StepSubject;
import edu.ucsd.cse110.walkwalkrevolution.fitness.Steps;
import edu.ucsd.cse110.walkwalkrevolution.route.Route;

import static edu.ucsd.cse110.walkwalkrevolution.RoutesDetailActivity.ROUTE_ID;

public class WalkActivity extends AppCompatActivity implements Observer {
    private static final String TAG = "WalkActivity";
    public static final String TEST = "edu.ucsd.cse110.walkwalkrevolution.TEST";

    long walkSteps ;
    long currentTotalSteps;

    private TextView steps;
    private TextView miles;
    private TextView routeTitle;
    private TextView timer;

    private Button stopWalk;

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

        Intent intent = getIntent();
        int i = intent.getIntExtra(RoutesDetailActivity.ROUTE, 0);
        int j = intent.getIntExtra(HomeActivity.PRE_EXISTING_ROUTE, 0);
        long s = intent.getLongExtra(ROUTE_ID, 0);

        //  Exit when stop walk clicked.
        stopWalk = findViewById(R.id.stop_walk);
        stopWalk.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {


                if(i == 1 && j == 0 && s != 0)
                {
                    saveWalk(s);
                }
                else if(i==0 && j==1)
                {
                    createRouteActivity();
                }
                finish();

            }
        });

        chronometer = findViewById(R.id.timer);
        chronometer.start();
    }

    public void createRouteActivity() {
        Intent createRoute = new Intent(this, CreateRouteActivity.class);
        createRoute.putExtra(Walk.STEP_COUNT, steps.getText().toString());
        createRoute.putExtra(Walk.MILES, miles.getText().toString());
        createRoute.putExtra(Walk.DURATION, timer.getText().toString());
        startActivity(createRoute);
    }

    public void saveWalk(long id){
        Route route = WalkWalkRevolution.getRouteDao().getRoute(id);
        Map<String, String> data = new HashMap<String, String>(){{
            put(Walk.STEP_COUNT, steps.getText().toString());
            put(Walk.MILES, miles.getText().toString());
            put(Walk.DURATION, timer.getText().toString());
        }};
        route.getActivity().setDetails(data);
        route.getActivity().setDate();

        Intent i = new Intent(this, RoutesDetailActivity.class);
        i.putExtra(TEST, id);
        startActivity(i);
        finish();

        /*
        route.getActivity().setDetail(Walk.STEP_COUNT, steps.getText().toString());
        route.getActivity().setDetail(Walk.MILES, miles.getText().toString());
        route.getActivity().setDetail(Walk.DURATION, timer.getText().toString());
        route.getActivity().setDate();
        */

    }

    @Override
    public void update(Observable observable, Object o) {
        Log.d(TAG, "update: Walk Screen");
        updateWalkSteps();
    }

    public void updateWalkSteps(){
        Log.d(TAG, "updateWalkSteps: UPDATING");
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
