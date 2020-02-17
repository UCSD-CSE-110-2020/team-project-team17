package edu.ucsd.cse110.walkwalkrevolution;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Observable;
import java.util.Observer;

import edu.ucsd.cse110.walkwalkrevolution.activity.Activity;
import edu.ucsd.cse110.walkwalkrevolution.activity.ActivityUtils;
import edu.ucsd.cse110.walkwalkrevolution.activity.Walk;
import edu.ucsd.cse110.walkwalkrevolution.fitness.FitnessService;
import edu.ucsd.cse110.walkwalkrevolution.fitness.StepSubject;
import edu.ucsd.cse110.walkwalkrevolution.fitness.Steps;
import edu.ucsd.cse110.walkwalkrevolution.route.Routes;

public class HomeActivity extends AppCompatActivity implements Observer {

    private final int MOCK_ID = 0;

    private static final String TEST_SERVICE = "TEST_SERVICE";
    private static final String TAG = "HomeActivity";
    public static final String PRE_EXISTING_ROUTE = "edu.ucsd.cse110.walkwalkrevolution.PRE_EXISTING_ROUTE";

    FitnessService fitnessService;
    private TextView textSteps, textMiles, latestSteps, latestMiles, latestDuration;

    private Button startWalk;
    private Button mockButton;

    private static StepSubject stepSubject;

    private long offsetStep;
    private LocalDateTime mockedTime;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        textSteps = findViewById(R.id.steps);
        textMiles = findViewById(R.id.miles);

        latestSteps = findViewById(R.id.latest_steps);
        latestMiles = findViewById(R.id.latest_miles);
        latestDuration = findViewById(R.id.latest_duration);

        fitnessService = WalkWalkRevolution.getFitnessService();

        stepSubject = new StepSubject(fitnessService);
        stepSubject.addObserver(this);

        startWalk = findViewById(R.id.start_walk);
        startWalk.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startWalk();
            }
        });

        mockButton = findViewById(R.id.mock_button);
        mockButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startMock();
            }
        });

    }

    public void createRouteActivity() {
        Intent createRoute = new Intent(this, CreateRouteActivity.class);
        startActivity(createRoute);
    }

    private void startWalk(){
        Intent intent = new Intent(this,  WalkActivity.class);
        intent.putExtra(PRE_EXISTING_ROUTE, 1);
        startActivity(intent);
    }

    private void startMock(){
        Intent intent = new Intent(this, MockActivity.class);
        startActivityForResult(intent, MOCK_ID, null);
    }

    @Override
    protected void onStart() {
        super.onStart();

        if(WalkWalkRevolution.getUser() == null){
            Intent heightActivity = new Intent(this, HeightActivity.class);
            heightActivity.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NO_ANIMATION);
            startActivity(heightActivity);
            HomeActivity.this.overridePendingTransition(0, 0);
            HomeActivity.this.finish();
        }
    }

    @Override
    protected void onResume(){
        super.onResume();

        setStepCount(WalkWalkRevolution.getSteps());

        if(mockedTime == null)
            populateLatestInfo(getLatestDailyWalk());
        else
            populateLatestInfo(getLatestDailyWalk(mockedTime));
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        Log.d(TAG, "onActivityResult: " + requestCode);

        // If authentication was required during google fit setup, this will be called after the user authenticates
        if (resultCode == android.app.Activity.RESULT_OK) {
//       If authentication was required during google fit setup, this will be called after the user authenticates
            if (resultCode == android.app.Activity.RESULT_OK) {
                if (requestCode == fitnessService.getRequestCode()) {
                    setStepCount(WalkWalkRevolution.getSteps());
                }
            } else {
                Log.e(TAG, "ERROR, google fit result code: " + resultCode);
            }

            // Enter mock mode, add additional steps from mock screen.
            if (requestCode == MOCK_ID) {
                int signal = data.getIntExtra("signal", 1);

                if(signal == 0) {
                    offsetStep += data.getLongExtra("steps", 0);
                    long time = data.getLongExtra("time", 0);
                    WalkWalkRevolution.setTimeOffset(time);
                }

                Log.d(TAG, "onActivityResult: " + offsetStep + ", " + WalkWalkRevolution.getTimeOffset());
            }
        }
    }

    public void setStepCount(Steps steps){
        setStepCount(steps.getDailyTotal());
    }

    public void setStepCount(long stepCount) {
        textSteps.setText(String.valueOf(offsetStep + stepCount));

        if(WalkWalkRevolution.getUser() != null) {
            // Round miles to 2 decimal places.
            textMiles.setText(String.valueOf(Math.round(
                    ActivityUtils.stepsToMiles(stepCount + offsetStep,
                            WalkWalkRevolution.getUser().getHeight()) * 100) / 100.0));
        }
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
                if(mockedTime == null)
                    populateLatestInfo(getLatestDailyWalk());
                else
                    populateLatestInfo(getLatestDailyWalk(mockedTime));
            }
        });

    }

    public static StepSubject getStepSubject(){
        return stepSubject;
    }

    public void populateLatestInfo(Activity activity){
        if(activity != null){
            latestDuration.setText(activity.getDetail(Walk.DURATION));
            latestSteps.setText(activity.getDetail(Walk.STEP_COUNT));
            latestMiles.setText(activity.getDetail(Walk.MILES));
        } else {
            latestDuration.setText(getResources().getString(R.string.default_latest));
            latestSteps.setText(getResources().getString(R.string.default_latest));
            latestMiles.setText(getResources().getString(R.string.default_latest));
        }
    }

    //For testing
    public void setMockedTime(LocalDateTime time){
        this.mockedTime = time;
    }

    public Activity getLatestDailyWalk(){
        return getLatestDailyWalk(WalkWalkRevolution.getTime());
    }

    //O(logn) - Binary Search for the latest time
    public Activity getLatestDailyWalk(LocalDateTime time){
        Routes routes = new Routes();
        List<Activity> activities = routes.getActivities();
        int l = 0, r = activities.size();
        while(l < r){
            int m = l + (r-l)/2;
            if(ActivityUtils.stringToTime(activities.get(m).getDetail(Activity.DATE))
                    .compareTo(time) < 0){
                l = m+1;
            } else {
                r = m;
            }
        }
        if(l == 0 ||
                !ActivityUtils.isSameDay(ActivityUtils.stringToTime(activities.get(l-1).getDetail(Activity.DATE)), time))
            return null;
        else return activities.get(l-1);
    }

}
