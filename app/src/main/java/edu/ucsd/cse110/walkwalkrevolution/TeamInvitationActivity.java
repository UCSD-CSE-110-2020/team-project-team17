package edu.ucsd.cse110.walkwalkrevolution;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import java.util.List;

import edu.ucsd.cse110.walkwalkrevolution.invitation.Invitation;
import edu.ucsd.cse110.walkwalkrevolution.invitation.Invitations;
import edu.ucsd.cse110.walkwalkrevolution.invitation.InvitationsObserver;


// This is the screen where a user can accept/decline team invitations.
public class TeamInvitationActivity extends AppCompatActivity implements InvitationsObserver {

    Invitations invitations;
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

        acceptBtn.setVisibility(View.INVISIBLE);
        cancelBtn.setVisibility(View.INVISIBLE);

        //Retrieve invitations from the database
        invitations = new Invitations();
        invitations.subscribe(this);
    }

    @Override
    public void update(List<Invitation> invitationList){
        if(invitationList.size() > 0){
            invite = invitationList.get(0);
            displayInvitation(invite);
        } else {
            displayInvitation(null);
        }
    }

    public void displayInvitation(Invitation invite){
        invitationText = findViewById(R.id.invite_text);
        if(invite == null){
            invitationText.setText(getResources().getString(R.string.no_invitation_text));
        } else {
            String inviteInfo = String.format("%s\n has sent you a \nteam invitation! \nJoin?", invite.getSender());
            invitationText.setText(inviteInfo);

            acceptBtn.setVisibility(View.VISIBLE);
            cancelBtn.setVisibility(View.VISIBLE);

            acceptBtn.setOnClickListener(new View.OnClickListener(){
                @Override
                public void onClick(View v){
                    if(invite != null){
                        WalkWalkRevolution.getInvitationService().acceptInvite(invite);
                    }
                    finish();
                }
            });

            cancelBtn.setOnClickListener(new View.OnClickListener(){
                @Override
                public void onClick(View v){
                    if(invite != null){
                        WalkWalkRevolution.getInvitationService().deleteInvite(invite);
                    }
                    finish();
                }
            });
        }
    }
}