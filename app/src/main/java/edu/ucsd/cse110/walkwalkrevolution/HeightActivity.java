package edu.ucsd.cse110.walkwalkrevolution;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.drawable.DrawableCompat;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Button;
import android.widget.TextView;

import static edu.ucsd.cse110.walkwalkrevolution.WalkWalkRevolution.fitnessServiceKey;

public class HeightActivity extends AppCompatActivity {

    private final double INCHES_PER_FOOT = 12;
    private final double CONVERSION_FACTOR = .413;
    private final double FEET_IN_MILE = 5280;

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

        Button submit = findViewById(R.id.submit_height);
        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                boolean validHeight = validField(feet) & validField(inches);

                if(validHeight) {
                    createUserDataFromFields(feet, inches);
                    Intent intent = new Intent(HeightActivity.this, HomeActivity.class);
                    intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NO_ANIMATION);
                    intent.putExtra(HomeActivity.FITNESS_SERVICE_KEY, fitnessServiceKey);
                    startActivity(intent);
                    finish();
                } else {
                    TextView error = findViewById(R.id.height_error);
                    error.setVisibility(View.VISIBLE);
                }
            }
        });
    }

    private boolean validField(EditText field){
        final int MAX_HEIGHT_VAL = 12;
        boolean validInput = field.getText().length() != 0 && Integer.parseInt(field.getText().toString()) < MAX_HEIGHT_VAL;

        if(!validInput){
            setBackgroundTint(field, R.color.red);
        } else {
            setBackgroundTint(field, R.color.white);
        }

        return validInput;
    }

    private void setBackgroundTint(EditText field, int color){
        DrawableCompat.setTint(field.getBackground(), getResources().getColor(color));
    }

    private void createUserDataFromFields(EditText feet, EditText inches){
        SharedPreferences prefs = getSharedPreferences("USER", MODE_PRIVATE);
        SharedPreferences.Editor editor = prefs.edit();

        // https://www.openfit.com/how-many-steps-walk-per-mile
        double heightFeet = Integer.parseInt(feet.getText().toString());
        double heightInches = heightFeet * INCHES_PER_FOOT + Integer.parseInt(inches.getText().toString());
        double stepsPerMile = FEET_IN_MILE / (CONVERSION_FACTOR * heightInches / 12);

        editor.putInt("height_feet", (int) heightFeet);
        editor.putInt("height_inches", (int) heightInches);
        editor.putFloat("steps_per_mile", (float) stepsPerMile);
        editor.putBoolean("height_set", true);
        editor.apply();
    }
}
