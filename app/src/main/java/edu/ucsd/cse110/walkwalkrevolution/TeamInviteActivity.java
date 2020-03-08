package edu.ucsd.cse110.walkwalkrevolution;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import edu.ucsd.cse110.walkwalkrevolution.invitation.Invitation;

public class TeamInviteActivity extends AppCompatActivity {

    private EditText emailEditText;
    private Button sendInviteButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_team_invite);

        emailEditText = findViewById(R.id.invite_email_edittext);

        sendInviteButton = findViewById(R.id.send_invite_button);
        sendInviteButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // TODO Send the invite.
                Invitation invitation = new Invitation(emailEditText.getText().toString(),
                        WalkWalkRevolution.getUser().getEmail(),
                        WalkWalkRevolution.getUser().getName());
                WalkWalkRevolution.getInvitationService().addInvitation(invitation);
                finish();
            }
        });
    }

//    private boolean isValidEmail(String email){
//        return !(email.length() == 0); // TODO this
//    }
}
