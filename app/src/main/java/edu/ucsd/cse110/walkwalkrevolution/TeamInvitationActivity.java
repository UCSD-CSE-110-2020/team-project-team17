package edu.ucsd.cse110.walkwalkrevolution;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import edu.ucsd.cse110.walkwalkrevolution.user.invite.Invitation;
import edu.ucsd.cse110.walkwalkrevolution.user.invite.InvitationService;
import edu.ucsd.cse110.walkwalkrevolution.user.invite.Invitations;

// This is the screen where a user can accept/decline team invitations.
public class TeamInvitationActivity extends AppCompatActivity {

    Invitation invite;
    TextView invitationText;
    Button acceptBtn;
    Button cancelBtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_team_invitation);

        acceptBtn = findViewById(R.id.accept_button);
        cancelBtn = findViewById(R.id.cancel_button);

        InvitationService is = WalkWalkRevolution.getInvitationService();

        //Retrieve invitations from the database
        Invitations invitations = new Invitations();
        is.getInvite(WalkWalkRevolution.getUser().getEmail(), this);


        //TODO: Accept the invitation: change user's team to sender's team and delete invitation
        acceptBtn.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                if(invite != null){
                    invite.acceptInvite();
                }
                is.deleteInvite();
                finish();
            }
        });

        //TODO: Cancel the invitation: delete the invitation
        cancelBtn.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                is.deleteInvite();
            }
        });
    }

    public void displayInvitation(Invitation invite){
        invitationText = findViewById(R.id.invite_text);
        if(invite == null){
            invitationText.setText(getResources().getString(R.string.no_invitation_text));
            return;
        }

        this.invite = invite;
        String inviteInfo = String.format("%s has sent you a team invitation! Join?", invite.getSenderName());
        invitationText.setText(inviteInfo);
    }
}
