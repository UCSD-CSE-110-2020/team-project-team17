package edu.ucsd.cse110.walkwalkrevolution;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.Button;
import android.widget.TextView;

public class TeamInvitationActivity extends AppCompatActivity {

    TextView invitationText;
    Button acceptBtn;
    Button cancelBtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_team_invitation);

        invitationText = findViewById(R.id.invite_text);
        acceptBtn = findViewById(R.id.accept_button);
        cancelBtn = findViewById(R.id.cancel_button);

        //TODO: integrate team invitation functionality with screen
    }
}
