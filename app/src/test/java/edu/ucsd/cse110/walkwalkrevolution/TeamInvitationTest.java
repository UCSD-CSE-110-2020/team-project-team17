package edu.ucsd.cse110.walkwalkrevolution;

import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import androidx.test.core.app.ActivityScenario;
import androidx.test.ext.junit.runners.AndroidJUnit4;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.robolectric.shadows.ShadowToast;

import java.util.Arrays;
import java.util.List;

import edu.ucsd.cse110.walkwalkrevolution.invitation.Invitation;
import edu.ucsd.cse110.walkwalkrevolution.invitation.Invitations;
import edu.ucsd.cse110.walkwalkrevolution.invitation.persistence.InvitationService;
import edu.ucsd.cse110.walkwalkrevolution.invitation.persistence.InvitationServiceFactory;
import edu.ucsd.cse110.walkwalkrevolution.user.User;

import static junit.framework.TestCase.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doAnswer;
import static org.mockito.Mockito.when;

@RunWith(AndroidJUnit4.class)
public class TeamInvitationTest {

    private InvitationService invitationService;
    private InvitationServiceFactory invitationServiceFactory;

    private User user;

    @Before
    public void setup(){
        user = new User();
        user.setEmail("dummyEmail");
        user.setName("name");

        WalkWalkRevolution.setUser(user);

        invitationService = Mockito.mock(InvitationService.class);
        invitationServiceFactory = Mockito.mock(InvitationServiceFactory.class);
        when(invitationServiceFactory.createInvitationService())
                .thenReturn(invitationService);

        WalkWalkRevolution.setInvitationServiceFactory(invitationServiceFactory);

        WalkWalkRevolution.createInvitationService();
    }

    @Test
    public void hasNoInvite(){
        try(ActivityScenario<TeamInvitationActivity> scenario = ActivityScenario.launch(TeamInvitationActivity.class)){
            scenario.onActivity(activity -> {
                activity.displayInvitation(null);
                TextView txtView = activity.findViewById(R.id.invite_text);
                assertEquals("No invitation was found.", txtView.getText().toString());

                Button accept = activity.findViewById(R.id.accept_button);
                Button cancel = activity.findViewById(R.id.cancel_button);

                assertEquals(View.INVISIBLE, accept.getVisibility());
                assertEquals(View.INVISIBLE, cancel.getVisibility());

            });
        }
    }

    @Test
    public void hasInvite(){
        Invitation invitation = new Invitation("to", "from", "sender");
        try(ActivityScenario<TeamInvitationActivity> scenario = ActivityScenario.launch(TeamInvitationActivity.class)){
            scenario.onActivity(activity -> {
                activity.displayInvitation(invitation);
                TextView txtView = activity.findViewById(R.id.invite_text);
                assertEquals("sender\n has sent you a \nteam invitation! \nJoin?", txtView.getText().toString());

                Button accept = activity.findViewById(R.id.accept_button);
                Button cancel = activity.findViewById(R.id.cancel_button);

                assertEquals(View.VISIBLE, accept.getVisibility());
                assertEquals(View.VISIBLE, cancel.getVisibility());
            });
        }
    }


}
