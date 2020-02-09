package edu.ucsd.cse110.walkwalkrevolution;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import edu.ucsd.cse110.walkwalkrevolution.fitness.FitnessService;
import edu.ucsd.cse110.walkwalkrevolution.fitness.FitnessServiceFactory;
import edu.ucsd.cse110.walkwalkrevolution.fitness.GoogleFitAdapter;

public class HomeActivity extends AppCompatActivity {

    private class FetchUpdatedStepsAsyncTask extends AsyncTask<String, String, String> {

        @Override
        protected String doInBackground(String... params) {
            long waitTime = 1000*(Integer.parseInt(params[0]));
            while(true){
                fitnessService.updateStepCount();
                try {
                    Thread.sleep(waitTime);
                } catch (Exception e) {
                    //ignore
                }
            }
        }

        @Override
        protected void onPostExecute(String result) {
        }

        @Override
        protected void onCancelled(String result) {
        }

        @Override
        protected void onPreExecute() {
        }

        @Override
        protected void onProgressUpdate(String... text) {
        }

    }

    public static final String FITNESS_SERVICE_KEY = "FITNESS_SERVICE_KEY";
    private static final String TEST_SERVICE = "TEST_SERVICE";
    private static final String TAG = "HomeActivity";

    private long steps;
    private double miles;
    private TextView textSteps, textMiles;
    private FitnessService fitnessService;
    private Button startWalk;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        textSteps = findViewById(R.id.steps);
        textMiles = findViewById(R.id.miles);

        String fitnessServiceKey = getIntent().getStringExtra(FITNESS_SERVICE_KEY);

        fitnessService = FitnessServiceFactory.create(fitnessServiceKey, this);

        fitnessService.setup();

        if (!fitnessServiceKey.equals(TEST_SERVICE)) {
            FetchUpdatedStepsAsyncTask updater = new FetchUpdatedStepsAsyncTask();
            updater.execute(getString(R.string.daily_step_update_delay_sec));
        }

        startWalk = findViewById(R.id.start_walk);
        startWalk.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startWalk();
            }
        });
    }

    private void startWalk(){
        Intent intent = new Intent(this,  WalkActivity.class);
        startActivity(intent);
    }

    @Override
    protected void onStart() {
        super.onStart();
        SharedPreferences sharedPreferences = getSharedPreferences("USER", MODE_PRIVATE);
        boolean heightIsSet = sharedPreferences.getBoolean("height_set", false);


        if(!heightIsSet){
            Intent heightActivity = new Intent(this, HeightActivity.class);
            heightActivity.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NO_ANIMATION);
            startActivity(heightActivity);
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

//       If authentication was required during google fit setup, this will be called after the user authenticates
        if (resultCode == Activity.RESULT_OK) {
            if (requestCode == fitnessService.getRequestCode()) {
                fitnessService.updateStepCount();
            }
        } else {
            Log.e(TAG, "ERROR, google fit result code: " + resultCode);
        }
    }

    public void setStepCount(long stepCount) {
        this.steps = stepCount;

        Log.d(TAG, "setStepCount: Updated Shared Prefs");
        
        SharedPreferences activityHistory = getSharedPreferences("activity_history", MODE_PRIVATE);
        SharedPreferences.Editor editor = activityHistory.edit();
        editor.putLong("current_steps", steps);
        editor.apply();

        textSteps.setText(String.valueOf(steps));

        SharedPreferences userInfo = getSharedPreferences("USER", MODE_PRIVATE);
        float stepsPerMile = userInfo.getFloat("steps_per_mile", 0);

        // Round miles to 2 decimal places.
        textMiles.setText(String.valueOf(Math.round((steps / stepsPerMile) * 100) / 100.0));
    }

}
