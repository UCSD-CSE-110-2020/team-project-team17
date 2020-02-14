package edu.ucsd.cse110.walkwalkrevolution;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import java.util.Observable;
import java.util.Observer;

import edu.ucsd.cse110.walkwalkrevolution.fitness.FitnessService;
import edu.ucsd.cse110.walkwalkrevolution.fitness.FitnessServiceFactory;
import edu.ucsd.cse110.walkwalkrevolution.fitness.StepSubject;
import edu.ucsd.cse110.walkwalkrevolution.fitness.Steps;

public class HomeActivity extends AppCompatActivity implements Observer {

    public static final String FITNESS_SERVICE_KEY = "FITNESS_SERVICE_KEY";
    private static final String TEST_SERVICE = "TEST_SERVICE";
    private static final String TAG = "HomeActivity";

    private TextView textSteps, textMiles;
    private FitnessService fitnessService;
    private Button startWalk;
    private static StepSubject stepSubject;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        textSteps = findViewById(R.id.steps);
        textMiles = findViewById(R.id.miles);

        String fitnessServiceKey = getIntent().getStringExtra(FITNESS_SERVICE_KEY);

        fitnessService = FitnessServiceFactory.create(fitnessServiceKey, this);
        stepSubject = new StepSubject(fitnessService);
        stepSubject.addObserver(this);

        fitnessService.setup();

        startWalk = findViewById(R.id.start_walk);
        startWalk.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startWalk();
            }
        });

    }

    public void createRouteActivity() {
        Intent createRoute = new Intent(this, CreateRouteActivity.class);
        startActivity(createRoute);
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
            heightActivity.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NO_ANIMATION);
            startActivity(heightActivity);
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

//       If authentication was required during google fit setup, this will be called after the user authenticates
        if (resultCode == Activity.RESULT_OK) {
            if (requestCode == fitnessService.getRequestCode()) {
                setStepCount(WalkWalkRevolution.getSteps());
            }
        } else {
            Log.e(TAG, "ERROR, google fit result code: " + resultCode);
        }
    }

    private void setStepCount(Steps steps){
        setStepCount(steps.getDailyTotal());
    }

    public void setStepCount(long stepCount) {
        textSteps.setText(String.valueOf(stepCount));

        SharedPreferences userInfo = getSharedPreferences("USER", MODE_PRIVATE);
        float stepsPerMile = userInfo.getFloat("steps_per_mile", 0);

        // Round miles to 2 decimal places.
        textMiles.setText(String.valueOf(Math.round((stepCount / stepsPerMile) * 100) / 100.0));
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.home_menu, menu);
        return super.onCreateOptionsMenu(menu);
    }

    // handle button activities
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        if (id == R.id.list_button) {
            Intent intent = new Intent(this, RoutesActivity.class);
            startActivity(intent);
        } else if (id == R.id.add_button) {
            createRouteActivity();
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void update(Observable o, Object arg) {
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                setStepCount(WalkWalkRevolution.getSteps());
            }
        });

    }

    public static StepSubject getStepSubject(){
        return stepSubject;
    }

}
