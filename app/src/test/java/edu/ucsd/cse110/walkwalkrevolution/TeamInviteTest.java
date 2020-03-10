package edu.ucsd.cse110.walkwalkrevolution;

import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.recyclerview.widget.RecyclerView;
import androidx.test.core.app.ActivityScenario;
import androidx.test.ext.junit.runners.AndroidJUnit4;
import androidx.test.rule.ActivityTestRule;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.robolectric.Robolectric;
import org.robolectric.shadows.ShadowToast;

import edu.ucsd.cse110.walkwalkrevolution.invitation.persistence.InvitationService;
import edu.ucsd.cse110.walkwalkrevolution.invitation.persistence.InvitationServiceFactory;
import edu.ucsd.cse110.walkwalkrevolution.user.User;

import static junit.framework.TestCase.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doAnswer;
import static org.mockito.Mockito.when;

@RunWith(AndroidJUnit4.class)
public class TeamInviteTest {

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
    public void nonexistentEmail() {
        String noemail = "noemail@email.com";

        doAnswer(invocation -> {
            Toast.makeText(WalkWalkRevolution.getContext(), "Invitation Failed: Invalid Email",
                    Toast.LENGTH_LONG).show();
            return null;
        }).when(invitationService).addInvitation(any());

        try(ActivityScenario<TeamInviteActivity> scenario = ActivityScenario.launch(TeamInviteActivity.class)){
            scenario.onActivity(activity -> {
                EditText editText = activity.findViewById(R.id.invite_email_edittext);
                editText.setText(noemail);

                Button send = activity.findViewById(R.id.send_invite_button);
                send.performClick();

                assertEquals("Invitation Failed: Invalid Email", ShadowToast.getTextOfLatestToast());
            });
        }
    }

    @Test
    public void emailExists() {
        String noemail = "email@email.com";

        doAnswer(invocation -> {
            Toast.makeText(WalkWalkRevolution.getContext(), "Invitation Sent",
                    Toast.LENGTH_LONG).show();
            return null;
        }).when(invitationService).addInvitation(any());

        try(ActivityScenario<TeamInviteActivity> scenario = ActivityScenario.launch(TeamInviteActivity.class)){
            scenario.onActivity(activity -> {
                EditText editText = activity.findViewById(R.id.invite_email_edittext);
                editText.setText(noemail);

                Button send = activity.findViewById(R.id.send_invite_button);
                send.performClick();

                assertEquals("Invitation Sent", ShadowToast.getTextOfLatestToast());
            });
        }
    }

}
