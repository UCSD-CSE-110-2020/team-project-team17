package edu.ucsd.cse110.walkwalkrevolution;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
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
import org.robolectric.Robolectric;
import org.robolectric.RuntimeEnvironment;
import org.robolectric.annotation.Config;

import edu.ucsd.cse110.walkwalkrevolution.fitness.FitnessService;
import edu.ucsd.cse110.walkwalkrevolution.fitness.FitnessServiceFactory;

import static com.google.common.truth.Truth.assertThat;

import java.util.concurrent.TimeUnit;

@RunWith(AndroidJUnit4.class)
@Config(sdk=28)
public class HeightActivityUnitTest {
    private static final String TAG = "HeightActivityUnitTest";
    private HeightActivity heightActivity;
    private Button submit;
    private TextView feet;
    private TextView inches;
    private TextView error;

    @Before
    public void setUp() {
        heightActivity = Robolectric.buildActivity(HeightActivity.class).create().get();
        submit = heightActivity.findViewById(R.id.submit_height);
        error  = heightActivity.findViewById(R.id.height_error);
        feet   = heightActivity.findViewById(R.id.feet_user_input);
        inches = heightActivity.findViewById(R.id.inches_user_input);
    }

    @Test
    public void errorOnBigInches(){
        assert(error.getVisibility() == View.INVISIBLE);

        feet.setText("5");
        inches.setText("13");
        submit.performClick();

        assert(error.getVisibility() == View.VISIBLE);
    }

    @Test
    public void errorOnNoInput(){
        assert(error.getVisibility() == View.INVISIBLE);

        submit.performClick();

        assert(error.getVisibility() == View.VISIBLE);
    }

    @Test
    public void noErrorValidInput(){
        assert(error.getVisibility() == View.INVISIBLE);

        feet.setText("5");
        inches.setText("6");
        submit.performClick();

        assert(error.getVisibility() == View.INVISIBLE);
    }

    @Test
    public void updateSharedPreferences(){
        feet.setText("5");
        inches.setText("6");
        submit.performClick();

        SharedPreferences sp = heightActivity.getSharedPreferences("USER", Context.MODE_PRIVATE);
        assert(sp.getInt("height_feet", 0) == 5);
        assert(sp.getInt("height_inches", 0) == 6);
    }
}