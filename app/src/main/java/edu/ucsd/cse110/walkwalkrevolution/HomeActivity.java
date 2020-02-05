package edu.ucsd.cse110.walkwalkrevolution;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
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
    private TextView textSteps;
    private FitnessService fitnessService;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        textSteps = findViewById(R.id.steps);

        String fitnessServiceKey = getIntent().getStringExtra(FITNESS_SERVICE_KEY);

        fitnessService = FitnessServiceFactory.create(fitnessServiceKey, this);

        fitnessService.setup();

        if (!fitnessServiceKey.equals(TEST_SERVICE)) {
            FetchUpdatedStepsAsyncTask updater = new FetchUpdatedStepsAsyncTask();
            updater.execute(getString(R.string.daily_step_update_delay_sec));
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
        textSteps.setText(String.valueOf(steps));
    }

}
