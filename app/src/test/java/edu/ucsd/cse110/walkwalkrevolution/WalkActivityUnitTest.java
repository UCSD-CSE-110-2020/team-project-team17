package edu.ucsd.cse110.walkwalkrevolution;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.test.core.app.ActivityScenario;
import androidx.test.core.app.ApplicationProvider;
import androidx.test.ext.junit.runners.AndroidJUnit4;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.robolectric.Robolectric;
import org.robolectric.annotation.Config;
import org.robolectric.shadows.ShadowApplication;
import org.robolectric.shadows.ShadowLooper;


import edu.ucsd.cse110.walkwalkrevolution.activity.Walk;
import edu.ucsd.cse110.walkwalkrevolution.fitness.FitnessServiceFactory;
import edu.ucsd.cse110.walkwalkrevolution.fitness.Steps;
import edu.ucsd.cse110.walkwalkrevolution.activity.ActivityUtils;
import edu.ucsd.cse110.walkwalkrevolution.route.Route;
import edu.ucsd.cse110.walkwalkrevolution.route.persistence.MockRouteDao;
import edu.ucsd.cse110.walkwalkrevolution.route.persistence.RouteService;
import edu.ucsd.cse110.walkwalkrevolution.route.persistence.RouteServiceFactory;
import edu.ucsd.cse110.walkwalkrevolution.user.User;
import edu.ucsd.cse110.walkwalkrevolution.user.persistence.MockUserDao;
import edu.ucsd.cse110.walkwalkrevolution.user.persistence.UserService;
import edu.ucsd.cse110.walkwalkrevolution.user.persistence.UserServiceFactory;

import static android.content.Context.MODE_PRIVATE;
import static com.google.common.truth.Truth.assertThat;
import static junit.framework.TestCase.assertEquals;
import static junit.framework.TestCase.assertNotNull;
import static org.mockito.Mockito.when;

@RunWith(AndroidJUnit4.class)
@Config(sdk=28)
public class WalkActivityUnitTest {
    private static final String TAG = "WalkActivityUnitTest";

    RouteService routeService;
    RouteServiceFactory routeServiceFactory;
    UserService userService;
    UserServiceFactory userServiceFactory;

    private WalkActivity walkActivity;
    private Steps stepTracker;

    private TextView steps;
    private TextView miles;

    private Button stop;

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

        Bundle bundle = new Bundle();
        bundle.putString("test", "");

        Intent intent = new Intent();
        intent.putExtras(bundle);

        WalkWalkRevolution.setUserDao(new MockUserDao());
        WalkWalkRevolution.setRouteDao(new MockRouteDao());
        WalkWalkRevolution.setUser(new User(1, 528*12, "", ""));
        walkActivity = Robolectric.buildActivity(WalkActivity.class, intent).create().get();
        steps        = walkActivity.findViewById(R.id.steps);
        miles        = walkActivity.findViewById(R.id.miles);
        stop         = walkActivity.findViewById(R.id.stop_walk);
        stepTracker  = new Steps();
        ActivityUtils.setConversionFactor(1);

        walkActivity.setSteps(stepTracker);
    }

    @Test
    public void testUpdateSteps(){
        assertThat(steps.getText().toString()).isEqualTo("0");
        assertThat(miles.getText().toString()).isEqualTo("0.0");

        stepTracker.updateStats(1000);
        walkActivity.updateWalkSteps();

        assertThat(steps.getText().toString()).isEqualTo("1000");
        assertThat(miles.getText().toString()).isEqualTo("100.0");
    }

    @Test
    public void testMidnightRollover(){
        assertThat(steps.getText().toString()).isEqualTo("0");
        assertThat(miles.getText().toString()).isEqualTo("0.0");

        stepTracker.updateStats(1000);
        walkActivity.updateWalkSteps();

        assertThat(steps.getText().toString()).isEqualTo("1000");
        assertThat(miles.getText().toString()).isEqualTo("100.0");

        stepTracker.updateStats(100);
        walkActivity.updateWalkSteps();

        assertThat(steps.getText().toString()).isEqualTo("1100");
        assertThat(miles.getText().toString()).isEqualTo("110.0");
    }

    @Test
    public void testRouteTitleExtra(){
        Bundle bundle = new Bundle();
        bundle.putString("route_title", "Blueberry Lane");
        bundle.putString("test", "");

        Intent intent = new Intent();
        intent.putExtras(bundle);

        walkActivity = Robolectric.buildActivity(WalkActivity.class, intent).create().get();

        assertThat(walkActivity.findViewById(R.id.route_title).getVisibility()).isEqualTo(View.VISIBLE);
    }

}
