package edu.ucsd.cse110.walkwalkrevolution;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import androidx.test.ext.junit.runners.AndroidJUnit4;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.robolectric.Robolectric;
import org.robolectric.annotation.Config;
import org.robolectric.shadows.ShadowApplication;


import static android.content.Context.MODE_PRIVATE;
import static com.google.common.truth.Truth.assertThat;

@RunWith(AndroidJUnit4.class)
@Config(sdk=28)
public class WalkActivityUnitTest {
    private static final String TAG = "WalkActivityUnitTest";

    private WalkActivity walkActivity;

    private TextView steps;
    private TextView miles;

    private SharedPreferences activityHistory;
    private SharedPreferences userInfo;

    private SharedPreferences.Editor editActivityHistory;
    private SharedPreferences.Editor editUserInfo;

    @Before
    public void setUp() {
        walkActivity = Robolectric.buildActivity(WalkActivity.class).create().get();
        steps        = walkActivity.findViewById(R.id.steps);
        miles        = walkActivity.findViewById(R.id.miles);

        activityHistory = walkActivity.getSharedPreferences("activity_history", MODE_PRIVATE);
        userInfo        = walkActivity.getSharedPreferences("USER", MODE_PRIVATE);

        editActivityHistory = activityHistory.edit();
        editUserInfo  = userInfo.edit();
    }

    @Test
    public void testUpdateSteps(){
        assertThat(steps.getText().toString()).isEqualTo("0");
        assertThat(miles.getText().toString()).isEqualTo("0.0");

        editActivityHistory.putLong("current_steps",  1000);
        editUserInfo.putFloat("steps_per_mile", 100);

        editActivityHistory.apply();
        editUserInfo.apply();

        walkActivity.updateWalkSteps();

        assertThat(steps.getText().toString()).isEqualTo("1000");
        assertThat(miles.getText().toString()).isEqualTo("10.0");
    }

    @Test
    public void testMidnightRollover(){
        assertThat(steps.getText().toString()).isEqualTo("0");
        assertThat(miles.getText().toString()).isEqualTo("0.0");

        editActivityHistory.putLong("current_steps",  1000);
        editUserInfo.putFloat("steps_per_mile", 100);

        editActivityHistory.apply();
        editUserInfo.apply();

        walkActivity.updateWalkSteps();

        assertThat(steps.getText().toString()).isEqualTo("1000");
        assertThat(miles.getText().toString()).isEqualTo("10.0");

        editActivityHistory.putLong("current_steps",  100);
        editActivityHistory.apply();

        walkActivity.updateWalkSteps();

        assertThat(steps.getText().toString()).isEqualTo("1100");
        assertThat(miles.getText().toString()).isEqualTo("11.0");
    }

    @Test
    public void testRouteTitleExtra(){
        Bundle bundle = new Bundle();
        bundle.putString("route_title", "Blueberry Lane");

        Intent intent = new Intent();
        intent.putExtras(bundle);

        walkActivity = Robolectric.buildActivity(WalkActivity.class, intent).create().get();

        assertThat(walkActivity.findViewById(R.id.route_title).getVisibility()).isEqualTo(View.VISIBLE);

    }


}
