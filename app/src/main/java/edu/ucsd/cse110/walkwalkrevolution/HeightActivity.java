package edu.ucsd.cse110.walkwalkrevolution;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.EditText;

public class HeightActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_height);

        // Show characters on number password inputs
        // https://stackoverflow.com/a/40477513

        EditText feet = findViewById(R.id.feet_user_input);
        feet.setTransformationMethod(null);

        EditText inches = findViewById(R.id.inches_user_input);
        inches.setTransformationMethod(null);


    }
}
