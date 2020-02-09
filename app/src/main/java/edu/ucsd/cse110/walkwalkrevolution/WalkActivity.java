package edu.ucsd.cse110.walkwalkrevolution;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

public class WalkActivity extends AppCompatActivity {
    long walkSteps ;
    long currentTotalSteps;

    private TextView steps;
    private TextView miles;
    private TextView routeTitle;
    private TextView timer;

    private SharedPreferences activityHistory;
    private SharedPreferences userInfo;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_walk);

        findViewById(R.id.route_title).setVisibility(View.INVISIBLE);

        // Activity history can be used to get the current number of steps.
        activityHistory = getSharedPreferences("activity_history", MODE_PRIVATE);

        // User Info can be used to get the steps / mile of the user.
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

    }

    private class SetWalkStepsAsync extends AsyncTask<String, String, String> {

        @Override
        protected String doInBackground(String... params) {
            long waitTime = 1000*(Integer.parseInt(params[0]));
            while(true){
                updateWalkSteps();
                try {
                    Thread.sleep(waitTime);
                } catch (Exception e) {
                    //ignore
                }
            }
        }
    }

    public void updateWalkSteps(){
        long newTotal = activityHistory.getLong("current_steps", 0);
        long userStepsPerMile = userInfo.getInt("steps_per_mile", 0);

        if(newTotal < currentTotalSteps){
            walkSteps += newTotal;
        } else {
            walkSteps += currentTotalSteps - newTotal;
        }

        currentTotalSteps =  newTotal;

        steps.setText(String.valueOf(walkSteps));
        miles.setText(String.valueOf(Math.round(walkSteps / userStepsPerMile * 100) / 100));
    }
}
