package edu.ucsd.cse110.walkwalkrevolution;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import androidx.test.core.app.ApplicationProvider;
import androidx.test.ext.junit.runners.AndroidJUnit4;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.robolectric.Robolectric;
import org.robolectric.annotation.Config;
import org.robolectric.shadows.ShadowApplication;


import edu.ucsd.cse110.walkwalkrevolution.fitness.FitnessServiceFactory;
import edu.ucsd.cse110.walkwalkrevolution.fitness.Steps;
import edu.ucsd.cse110.walkwalkrevolution.activity.ActivityUtils;
import edu.ucsd.cse110.walkwalkrevolution.route.persistence.MockRouteDao;
import edu.ucsd.cse110.walkwalkrevolution.user.User;
import edu.ucsd.cse110.walkwalkrevolution.user.persistence.MockUserDao;

import static android.content.Context.MODE_PRIVATE;
import static com.google.common.truth.Truth.assertThat;

@RunWith(AndroidJUnit4.class)
@Config(sdk=28)
public class WalkActivityUnitTest {
    private static final String TAG = "WalkActivityUnitTest";

    private WalkActivity walkActivity;
    private Steps stepTracker;

    private TextView steps;
    private TextView miles;

    @Before
    public void setUp() {
        Bundle bundle = new Bundle();
        bundle.putString("test", "");

        Intent intent = new Intent();
        intent.putExtras(bundle);

        WalkWalkRevolution.setUserDao(new MockUserDao());
        WalkWalkRevolution.setRouteDao(new MockRouteDao());
        WalkWalkRevolution.setUser(new User(1, 528*12));
        walkActivity = Robolectric.buildActivity(WalkActivity.class, intent).create().get();
        steps        = walkActivity.findViewById(R.id.steps);
        miles        = walkActivity.findViewById(R.id.miles);
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
