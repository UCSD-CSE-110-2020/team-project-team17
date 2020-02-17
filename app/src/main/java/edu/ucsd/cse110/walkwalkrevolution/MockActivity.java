package edu.ucsd.cse110.walkwalkrevolution;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

public class MockActivity extends AppCompatActivity {
    private static final String TAG = "MockActivity";

    private long mockedSteps = 0;
    private long mockedTime  = 0;

    private TextView added_steps_text;
    private EditText mock_time_text;

    private Button mock_steps, mock_time_button, finish, cancel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mock_screen);

        mock_time_button = findViewById(R.id.mock_time_button);

        // Make password input visible.
        mock_time_text = findViewById(R.id.mocked_time_text);
        mock_time_text.setTransformationMethod(null);

        // Text field showing how many steps we've added / button to add more
        added_steps_text = findViewById(R.id.added_steps);
        mock_steps = findViewById(R.id.add_steps_button);

        finish = findViewById(R.id.finish_button);
        cancel = findViewById(R.id.cancel_button);

        mock_steps.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Log.d(TAG, "onClick: Updating mocked steps");
                updateMockedSteps();
            }
        });

        mock_time_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Log.d(TAG, "onClick: Updating mocked time");
                updateMockedTime();
            }
        });

        finish.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Log.d(TAG, "onClick: Sending results back to previous activity");

                Intent intent = new Intent();
                intent.putExtra("signal", 0);
                intent.putExtra("steps", mockedSteps);
                intent.putExtra("time", mockedTime);

                setResult(Activity.RESULT_OK, intent);
                finish();
            }
        });

        cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Log.d(TAG, "onClick: Sending results back to previous activity");

                Intent intent = new Intent();
                intent.putExtra("signal", 1);

                setResult(Activity.RESULT_OK, intent);
                finish();
            }
        });
    }

    public void updateMockedSteps(){
        mockedSteps += 500;
        added_steps_text.setText(String.valueOf(mockedSteps));
    }

    public void updateMockedTime(){
        try {
            mockedTime = Long.parseLong(mock_time_text.getText().toString());
        } catch (Exception e){
            mockedTime = 0;
        }
    }
}
