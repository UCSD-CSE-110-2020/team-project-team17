package edu.ucsd.cse110.walkwalkrevolution;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.widget.TextView;

import androidx.test.core.app.ActivityScenario;
import androidx.test.core.app.ApplicationProvider;
import androidx.test.ext.junit.runners.AndroidJUnit4;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.robolectric.annotation.Config;

import edu.ucsd.cse110.walkwalkrevolution.fitness.FitnessService;
import edu.ucsd.cse110.walkwalkrevolution.fitness.FitnessServiceFactory;

import static com.google.common.truth.Truth.assertThat;

import java.util.concurrent.TimeUnit;

@RunWith(AndroidJUnit4.class)
@Config(sdk=28)
public class HomeActivityUnitTest {
    private static final String TEST_SERVICE = "TEST_SERVICE";

    private Intent intent;
    private long nextStepCount;

    @Before
    public void setUp() {
        FitnessServiceFactory.put(TEST_SERVICE, TestFitnessService::new);
        intent = new Intent(ApplicationProvider.getApplicationContext(), HomeActivity.class);
        intent.putExtra("FITNESS_SERVICE_KEY", TEST_SERVICE);
    }

    @Test
    public void testUpdateSteps() {
        nextStepCount = 0;

        // Hangs here:
        ActivityScenario<HomeActivity> scenario = ActivityScenario.launch(intent);
        scenario.onActivity(activity -> {
            TextView textSteps = activity.findViewById(R.id.steps);
            activity.setStepCount(nextStepCount);
            assertThat(textSteps.getText().toString()).isEqualTo("0");
            nextStepCount = 1337;
            activity.setStepCount(nextStepCount);
            assertThat(textSteps.getText().toString()).isEqualTo(String.valueOf(nextStepCount));
        });

    }

    @Test
    public void testStepsResetToZero(){
        nextStepCount = 1000;

        // Hangs here:
        ActivityScenario<HomeActivity> scenario = ActivityScenario.launch(intent);
        scenario.onActivity(activity -> {
            TextView textSteps = activity.findViewById(R.id.steps);
            activity.setStepCount(nextStepCount);
            assertThat(textSteps.getText().toString()).isEqualTo(String.valueOf(nextStepCount));
            nextStepCount = 0;
            activity.setStepCount(nextStepCount);
            assertThat(textSteps.getText().toString()).isEqualTo(String.valueOf(nextStepCount));
        });

    }

    @Test
    public void testMilesStory(){
        nextStepCount = 0;

        ActivityScenario<HomeActivity> scenario = ActivityScenario.launch(intent);
        scenario.onActivity(activity -> {
            TextView textMiles = activity.findViewById(R.id.miles);
            activity.setStepCount(nextStepCount);
            assertThat(textMiles.getText().toString()).isEqualTo("0.0");

            nextStepCount = 1337;

            SharedPreferences sharedPreferences = androidx.test.core.app.ApplicationProvider.getApplicationContext().getSharedPreferences(
                    "USER", Context.MODE_PRIVATE);
            SharedPreferences.Editor editor = sharedPreferences.edit();

            editor.putFloat("steps_per_mile", 100);
            editor.apply();

            activity.setStepCount(nextStepCount);
            assertThat(textMiles.getText().toString()).isEqualTo("13.37");
        });
    }

    @Test
    public void testUpdateMiles() {
        nextStepCount = 0;

        ActivityScenario<HomeActivity> scenario = ActivityScenario.launch(intent);
        scenario.onActivity(activity -> {
            TextView textMiles = activity.findViewById(R.id.miles);
            activity.setStepCount(nextStepCount);
            assertThat(textMiles.getText().toString()).isEqualTo("0.0");

            nextStepCount = 1337;

            SharedPreferences sharedPreferences = androidx.test.core.app.ApplicationProvider.getApplicationContext().getSharedPreferences(
                    "USER", Context.MODE_PRIVATE);
            SharedPreferences.Editor editor = sharedPreferences.edit();

            editor.putFloat("steps_per_mile", 100);
            editor.apply();

            activity.setStepCount(nextStepCount);
            assertThat(textMiles.getText().toString()).isEqualTo("13.37");
        });
    }

    @Test
    public void testMilesResetToZero(){
        nextStepCount = 1000;

        ActivityScenario<HomeActivity> scenario = ActivityScenario.launch(intent);
        scenario.onActivity(activity -> {
            SharedPreferences sharedPreferences = androidx.test.core.app.ApplicationProvider.getApplicationContext().getSharedPreferences("USER", Context.MODE_PRIVATE);
            SharedPreferences.Editor editor = sharedPreferences.edit();
            editor.putFloat("steps_per_mile", 100);
            editor.apply();

            TextView textMiles = activity.findViewById(R.id.miles);
            activity.setStepCount(nextStepCount);
            assertThat(textMiles.getText().toString()).isEqualTo("10.0");

            nextStepCount = 0;
            activity.setStepCount(nextStepCount);
            assertThat(textMiles.getText().toString()).isEqualTo("0.0");
        });
    }

}