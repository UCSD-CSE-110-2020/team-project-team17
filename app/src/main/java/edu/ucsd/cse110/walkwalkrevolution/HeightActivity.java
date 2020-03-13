package edu.ucsd.cse110.walkwalkrevolution;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;
import androidx.core.graphics.drawable.DrawableCompat;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Button;
import android.widget.TextView;

import edu.ucsd.cse110.walkwalkrevolution.user.User;
import edu.ucsd.cse110.walkwalkrevolution.user.persistence.UserSharedPreferenceDao;

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

        Button submit = findViewById(R.id.submit_height);
        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                boolean validHeight = validField(feet) & validField(inches);

                if(validHeight) {
                    createUserDataFromFields(feet, inches);
                    Intent intent = new Intent(HeightActivity.this, HomeActivity.class);
                    intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NO_ANIMATION);
                    startActivity(intent);
                    HeightActivity.this.overridePendingTransition(0,0);
                    HeightActivity.this.finish();
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
        DrawableCompat.setTint(field.getBackground(), ContextCompat.getColor(this, color));
    }

    private void createUserDataFromFields(EditText feet, EditText inches){
        long heightFeet = Long.parseLong(feet.getText().toString());
        long heightInches = Long.parseLong(inches.getText().toString());
        User user = new User(UserSharedPreferenceDao.USER_ID, heightFeet, heightInches);
        WalkWalkRevolution.getUserDao().addUser(user);
        WalkWalkRevolution.setUser(user);
    }
}
