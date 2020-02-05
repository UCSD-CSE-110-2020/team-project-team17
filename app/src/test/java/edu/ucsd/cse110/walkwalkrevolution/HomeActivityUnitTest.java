package edu.ucsd.cse110.walkwalkrevolution;

import android.content.Intent;
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

    private class TestFitnessService implements FitnessService {
        private static final String TAG = "[TestFitnessService]: ";
        private HomeActivity homeActivity;

        public TestFitnessService(HomeActivity homeActivity) {
            this.homeActivity = homeActivity;
        }

        @Override
        public int getRequestCode() {
            return 0;
        }

        @Override
        public void setup() {
            System.out.println(TAG + "setup");
        }

        @Override
        public void updateStepCount() {
            System.out.println(TAG + "updateStepCount");
            homeActivity.setStepCount(nextStepCount);
        }
    }
}