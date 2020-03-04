package edu.ucsd.cse110.walkwalkrevolution;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.widget.TextView;

import androidx.test.core.app.ActivityScenario;
import androidx.test.core.app.ApplicationProvider;
import androidx.test.ext.junit.runners.AndroidJUnit4;

import com.google.android.gms.auth.api.signin.GoogleSignInAccount;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.robolectric.annotation.Config;

import edu.ucsd.cse110.walkwalkrevolution.activity.Activity;
import edu.ucsd.cse110.walkwalkrevolution.activity.ActivityUtils;
import edu.ucsd.cse110.walkwalkrevolution.activity.Walk;
import edu.ucsd.cse110.walkwalkrevolution.fitness.FitnessService;
import edu.ucsd.cse110.walkwalkrevolution.fitness.FitnessServiceFactory;
import edu.ucsd.cse110.walkwalkrevolution.fitness.MockFitnessService;
import edu.ucsd.cse110.walkwalkrevolution.route.Route;
import edu.ucsd.cse110.walkwalkrevolution.route.persistence.MockRouteDao;
import edu.ucsd.cse110.walkwalkrevolution.route.persistence.RouteService;
import edu.ucsd.cse110.walkwalkrevolution.route.persistence.RouteServiceFactory;
import edu.ucsd.cse110.walkwalkrevolution.user.User;
import edu.ucsd.cse110.walkwalkrevolution.user.persistence.MockUserDao;
import edu.ucsd.cse110.walkwalkrevolution.user.persistence.UserService;
import edu.ucsd.cse110.walkwalkrevolution.user.persistence.UserServiceFactory;

import static com.google.common.truth.Truth.assertThat;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.TimeUnit;

@RunWith(AndroidJUnit4.class)
@Config(sdk=28)
public class HomeActivityUnitTest {
    private static final String TEST_SERVICE = "TEST_SERVICE";
    RouteService routeService;
    RouteServiceFactory routeServiceFactory;
    UserService userService;
    UserServiceFactory userServiceFactory;
    private Intent intent;
    private MockFitnessService fitnessService;
    private GoogleSignInAccount acc;
//    private long nextStepCount;

    @Before
    public void setUp() {
        routeService = Mockito.mock(RouteService.class);
        routeServiceFactory = Mockito.mock(RouteServiceFactory.class);
        userService = Mockito.mock(UserService.class);
        userServiceFactory = Mockito.mock(UserServiceFactory.class);

        when(userServiceFactory.createUserService())
                .thenReturn(userService);

        when(routeServiceFactory.createRouteService())
                .thenReturn(routeService);

        WalkWalkRevolution.setRouteServiceFactory(routeServiceFactory);
        WalkWalkRevolution.setUserServiceFactory(userServiceFactory);

        WalkWalkRevolution.createUserService();
        WalkWalkRevolution.createRouteService();

        acc = mock(GoogleSignInAccount.class);

        when(acc.getEmail()).thenReturn("email");
        when(acc.getDisplayName()).thenReturn("user");

        WalkWalkRevolution.setGoogleSignInAccount(acc);
        WalkWalkRevolution.setUserDao(new MockUserDao());
        WalkWalkRevolution.setUser(new User(1, 528*12, "", ""));
        fitnessService = new MockFitnessService();
        WalkWalkRevolution.setRouteDao(new MockRouteDao());
        ActivityUtils.setConversionFactor(1);
        WalkWalkRevolution.setFitnessService(fitnessService);
        intent = new Intent(ApplicationProvider.getApplicationContext(), HomeActivity.class);
    }

    @Test
    public void testUpdateSteps() {
        // Hangs here:
        ActivityScenario<HomeActivity> scenario = ActivityScenario.launch(intent);
        scenario.onActivity(activity -> {
            TextView textSteps = activity.findViewById(R.id.steps);
            fitnessService.updateStepCount();
            activity.setStepCount(WalkWalkRevolution.getSteps());
            assertThat(textSteps.getText().toString()).isEqualTo("0");
            fitnessService.setNextStepCount(1337);
            fitnessService.updateStepCount();
            activity.setStepCount(WalkWalkRevolution.getSteps());
            assertThat(textSteps.getText().toString()).isEqualTo(String.valueOf(1337));
        });

    }

    @Test
    public void testStepsResetToZero(){
        // Hangs here:
        ActivityScenario<HomeActivity> scenario = ActivityScenario.launch(intent);
        scenario.onActivity(activity -> {
            TextView textSteps = activity.findViewById(R.id.steps);
            fitnessService.setNextStepCount(1000);
            fitnessService.updateStepCount();
            activity.setStepCount(WalkWalkRevolution.getSteps());
            assertThat(textSteps.getText().toString()).isEqualTo(String.valueOf(1000));
            fitnessService.setNextStepCount(0);
            fitnessService.updateStepCount();
            activity.setStepCount(WalkWalkRevolution.getSteps());
            assertThat(textSteps.getText().toString()).isEqualTo(String.valueOf("0"));
        });

    }

    @Test
    public void testMilesStory(){
        ActivityScenario<HomeActivity> scenario = ActivityScenario.launch(intent);
        scenario.onActivity(activity -> {
            TextView textMiles = activity.findViewById(R.id.miles);
            fitnessService.updateStepCount();
            activity.setStepCount(WalkWalkRevolution.getSteps());
            assertThat(textMiles.getText().toString()).isEqualTo("0.0");
            fitnessService.setNextStepCount(1337);
            fitnessService.updateStepCount();
            activity.setStepCount(WalkWalkRevolution.getSteps());
            assertThat(textMiles.getText().toString()).isEqualTo("133.7");
        });
    }

    @Test
    public void testUpdateMiles() {
        ActivityScenario<HomeActivity> scenario = ActivityScenario.launch(intent);
        scenario.onActivity(activity -> {
            TextView textMiles = activity.findViewById(R.id.miles);
            fitnessService.updateStepCount();
            activity.setStepCount(WalkWalkRevolution.getSteps());
            assertThat(textMiles.getText().toString()).isEqualTo("0.0");
            fitnessService.setNextStepCount(1337);
            fitnessService.updateStepCount();
            activity.setStepCount(WalkWalkRevolution.getSteps());
            assertThat(textMiles.getText().toString()).isEqualTo("133.7");
        });
    }

    @Test
    public void testMilesResetToZero(){

        ActivityScenario<HomeActivity> scenario = ActivityScenario.launch(intent);
        scenario.onActivity(activity -> {
            TextView textMiles = activity.findViewById(R.id.miles);
            fitnessService.setNextStepCount(1000);
            fitnessService.updateStepCount();
            activity.setStepCount(WalkWalkRevolution.getSteps());
            assertThat(textMiles.getText().toString()).isEqualTo("100.0");
            fitnessService.setNextStepCount(0);
            fitnessService.updateStepCount();
            activity.setStepCount(WalkWalkRevolution.getSteps());
            assertThat(textMiles.getText().toString()).isEqualTo("0.0");
        });
    }

    @Test
    public void testNoLatestWalk(){
        ActivityScenario<HomeActivity> scenario = ActivityScenario.launch(intent);
        scenario.onActivity(activity -> {
            TextView latestSteps = activity.findViewById(R.id.latest_steps);
            TextView latestMiles = activity.findViewById(R.id.latest_miles);
            TextView latestDuration = activity.findViewById(R.id.latest_duration);

            assertThat(latestSteps.getText().toString()).isEqualTo("N/A");
            assertThat(latestMiles.getText().toString()).isEqualTo("N/A");
            assertThat(latestDuration.getText().toString()).isEqualTo("N/A");
        });
    }

    @Test
    public void testValidLatestWalk(){
        LocalDateTime time = LocalDateTime.of(2020, 01, 01, 0, 0);
        Map<String, String> data = new HashMap<String, String>() {{
            put(Walk.STEP_COUNT, "500");
            put(Walk.MILES, "0.25");
            put(Walk.DURATION, "5:00");
            put(Activity.DATE, ActivityUtils.timeToString(time));
        }};
        Route route = new Route(1, "Route1", new Walk(data));
        WalkWalkRevolution.getRouteDao().addRoute(route);

        LocalDateTime currTime = LocalDateTime.of(2020, 01, 01, 12, 0);

        ActivityScenario<HomeActivity> scenario = ActivityScenario.launch(intent);
        scenario.onActivity(activity -> {
            activity.setMockedTime(currTime);
            activity.onStart();
            activity.onResume();

            TextView latestSteps = activity.findViewById(R.id.latest_steps);
            TextView latestMiles = activity.findViewById(R.id.latest_miles);
            TextView latestDuration = activity.findViewById(R.id.latest_duration);

            assertThat(latestSteps.getText().toString()).isEqualTo("500");
            assertThat(latestMiles.getText().toString()).isEqualTo("0.25");
            assertThat(latestDuration.getText().toString()).isEqualTo("5:00");
        });
    }

    @Test
    public void testValidMultipleLatestWalk(){
        LocalDateTime time = LocalDateTime.of(2020, 01, 01, 0, 0);
        Map<String, String> data = new HashMap<String, String>() {{
            put(Walk.STEP_COUNT, "500");
            put(Walk.MILES, "0.25");
            put(Walk.DURATION, "5:00");
            put(Activity.DATE, ActivityUtils.timeToString(time));
        }};
        Route route = new Route(1, "Route1", new Walk(data));

        LocalDateTime time2 = LocalDateTime.of(2020, 01, 01, 0, 5);
        Map<String, String> data2 = new HashMap<String, String>() {{
            put(Walk.STEP_COUNT, "750");
            put(Walk.MILES, "1");
            put(Walk.DURATION, "3:59");
            put(Activity.DATE, ActivityUtils.timeToString(time2));
        }};
        Route route2 = new Route(2, "Route2", new Walk(data2));

        WalkWalkRevolution.getRouteDao().addRoute(route);
        WalkWalkRevolution.getRouteDao().addRoute(route2);

        LocalDateTime currTime = LocalDateTime.of(2020, 01, 01, 12, 0);

        ActivityScenario<HomeActivity> scenario = ActivityScenario.launch(intent);
        scenario.onActivity(activity -> {
            activity.setMockedTime(currTime);
            activity.onStart();
            activity.onResume();

            TextView latestSteps = activity.findViewById(R.id.latest_steps);
            TextView latestMiles = activity.findViewById(R.id.latest_miles);
            TextView latestDuration = activity.findViewById(R.id.latest_duration);

            assertThat(latestSteps.getText().toString()).isEqualTo("750");
            assertThat(latestMiles.getText().toString()).isEqualTo("1");
            assertThat(latestDuration.getText().toString()).isEqualTo("3:59");
        });
    }

    @Test
    public void testOldWalk(){
        LocalDateTime time = LocalDateTime.of(2020, 01, 01, 0, 0);
        Map<String, String> data = new HashMap<String, String>() {{
            put(Walk.STEP_COUNT, "500");
            put(Walk.MILES, "0.25");
            put(Walk.DURATION, "5:00");
            put(Activity.DATE, ActivityUtils.timeToString(time));
        }};
        Route route = new Route(1, "Route1", new Walk(data));
        WalkWalkRevolution.getRouteDao().addRoute(route);

        LocalDateTime currTime = LocalDateTime.of(2020, 01, 02, 12, 0);

        ActivityScenario<HomeActivity> scenario = ActivityScenario.launch(intent);
        scenario.onActivity(activity -> {
            activity.setMockedTime(currTime);
            activity.onStart();
            activity.onResume();

            TextView latestSteps = activity.findViewById(R.id.latest_steps);
            TextView latestMiles = activity.findViewById(R.id.latest_miles);
            TextView latestDuration = activity.findViewById(R.id.latest_duration);

            assertThat(latestSteps.getText().toString()).isEqualTo("N/A");
            assertThat(latestMiles.getText().toString()).isEqualTo("N/A");
            assertThat(latestDuration.getText().toString()).isEqualTo("N/A");
        });
    }

}