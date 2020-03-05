package edu.ucsd.cse110.walkwalkrevolution;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.renderscript.Script;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import edu.ucsd.cse110.walkwalkrevolution.user.invite.Invitation;

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
                if(!isValidEmail(emailEditText.getText().toString())){
                    emailEditText.setError("You dummy. Fill this in right.");
                } else {
                    // TODO Send the invite.
                    Invitation invite = new Invitation(emailEditText.getText().toString());
                    if(invite.getReceiver() == null) {
                        emailEditText.setError("Could not find user associated with this email.");
                    } else {
                        WalkWalkRevolution.getInvitationService().addInvite(invite);
                        Toast.makeText(getApplicationContext(), "Sent Invite", Toast.LENGTH_LONG).show();
                        finish();
                    }
                }
            }
        });
    }

    private boolean isValidEmail(String email){
        return !(email.length() == 0); // TODO this
    }
}
