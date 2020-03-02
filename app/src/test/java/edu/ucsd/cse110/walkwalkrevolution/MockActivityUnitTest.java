package edu.ucsd.cse110.walkwalkrevolution;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.test.ext.junit.runners.AndroidJUnit4;

import com.google.android.gms.auth.api.signin.GoogleSignInAccount;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.robolectric.Robolectric;
import org.robolectric.annotation.Config;

import static com.google.common.truth.Truth.assertThat;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

@RunWith(AndroidJUnit4.class)
@Config(sdk=28)
public class MockActivityUnitTest {
    private static final String TAG = "MockActivityUnitTest";

    private MockActivity mockActivity;

    private TextView offset;
    private TextView time;

    private Button add_steps_button;
    private Button set_time;

    private GoogleSignInAccount acc;

    @Before
    public void setUp() {
        acc = mock(GoogleSignInAccount.class);

        when(acc.getEmail()).thenReturn("email");
        when(acc.getDisplayName()).thenReturn("user");

        WalkWalkRevolution.setGoogleSignInAccount(acc);

        mockActivity = Robolectric.buildActivity(MockActivity.class).create().get();

        offset = mockActivity.findViewById(R.id.added_steps);
        time  = mockActivity.findViewById(R.id.mocked_time_text);

        add_steps_button = mockActivity.findViewById(R.id.add_steps_button);
        set_time = mockActivity.findViewById(R.id.mock_time_button);
    }

    @Test
    public void testUpdateSteps(){
        assertThat(offset.getText().toString()).isEqualTo("0");
        assertThat(time.getText().toString()).isEqualTo("");

        add_steps_button.performClick();

        assertThat(offset.getText().toString()).isEqualTo("500");
    }
}
