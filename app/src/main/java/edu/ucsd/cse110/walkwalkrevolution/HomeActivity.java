package edu.ucsd.cse110.walkwalkrevolution;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import edu.ucsd.cse110.walkwalkrevolution.fitness.FitnessService;
import edu.ucsd.cse110.walkwalkrevolution.fitness.FitnessServiceFactory;

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
    private Button routesBtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        textSteps = findViewById(R.id.steps);
        routesBtn = (Button) findViewById(R.id.routesview_button);

        String fitnessServiceKey = getIntent().getStringExtra(FITNESS_SERVICE_KEY);

        fitnessService = FitnessServiceFactory.create(fitnessServiceKey, this);

        fitnessService.setup();

        if (!fitnessServiceKey.equals(TEST_SERVICE)) {
            FetchUpdatedStepsAsyncTask updater = new FetchUpdatedStepsAsyncTask();
            updater.execute(getString(R.string.daily_step_update_delay_sec));
        }

        routesBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startRoutesViewActivity();
            }
        });

    }

    public void startRoutesViewActivity() {
        Intent intent = new Intent(this, RouteViewActivity.class);
        startActivity(intent);
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
