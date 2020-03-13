package edu.ucsd.cse110.walkwalkrevolution;

import android.content.Intent;
import android.widget.Button;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;
import androidx.test.core.app.ActivityScenario;
import androidx.test.core.app.ApplicationProvider;
import androidx.test.ext.junit.runners.AndroidJUnit4;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.robolectric.Robolectric;
import org.robolectric.annotation.Config;
import org.robolectric.shadows.ShadowLooper;

import java.util.HashMap;
import java.util.Map;

import edu.ucsd.cse110.walkwalkrevolution.activity.Activity;
import edu.ucsd.cse110.walkwalkrevolution.activity.EmptyActivity;
import edu.ucsd.cse110.walkwalkrevolution.activity.Walk;
import edu.ucsd.cse110.walkwalkrevolution.proposal.ProposalFirestoreService;
import edu.ucsd.cse110.walkwalkrevolution.proposal.ProposalService;
import edu.ucsd.cse110.walkwalkrevolution.proposal.ProposalServiceFactory;
import edu.ucsd.cse110.walkwalkrevolution.proposal.adapter.AcceptedRVAdapter;
import edu.ucsd.cse110.walkwalkrevolution.proposal.adapter.DeclineBTRVAdapter;
import edu.ucsd.cse110.walkwalkrevolution.route.Route;
import edu.ucsd.cse110.walkwalkrevolution.route.persistence.RouteService;
import edu.ucsd.cse110.walkwalkrevolution.route.persistence.RouteServiceFactory;
import edu.ucsd.cse110.walkwalkrevolution.team.TeamRecycleView.TeamAdapter;
import edu.ucsd.cse110.walkwalkrevolution.user.User;
import edu.ucsd.cse110.walkwalkrevolution.user.persistence.MockUserDao;
import edu.ucsd.cse110.walkwalkrevolution.user.persistence.UserService;
import edu.ucsd.cse110.walkwalkrevolution.user.persistence.UserServiceFactory;

import static junit.framework.TestCase.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doAnswer;
import static org.mockito.Mockito.when;

@RunWith(AndroidJUnit4.class)
@Config(sdk=28)
public class ProposalDetailsTest {

    Intent intent;
    ProposeScreenActivity activityPropose;

    RouteService routeService;
    RouteServiceFactory routeServiceFactory;
    UserService userService;
    UserServiceFactory userServiceFactory;
    ProposalService proposalService;
    ProposalServiceFactory proposalServiceFactory;

    Route route;

    @Before
    public void setup(){
        routeService = Mockito.mock(RouteService.class);
        routeServiceFactory = Mockito.mock(RouteServiceFactory.class);
        userService = Mockito.mock(UserService.class);
        userServiceFactory = Mockito.mock(UserServiceFactory.class);
        proposalService = Mockito.mock(ProposalService.class);
        proposalServiceFactory = Mockito.mock(ProposalServiceFactory.class);

        when(userServiceFactory.createUserService())
                .thenReturn(userService);

        when(routeServiceFactory.createRouteService())
                .thenReturn(routeService);

        when(proposalServiceFactory.createProposalService())
                .thenReturn(proposalService);

        WalkWalkRevolution.setRouteServiceFactory(routeServiceFactory);
        WalkWalkRevolution.setUserServiceFactory(userServiceFactory);
        WalkWalkRevolution.setProposalServiceFactory(proposalServiceFactory);

        WalkWalkRevolution.createUserService();
        WalkWalkRevolution.createRouteService();
        WalkWalkRevolution.createProposalService();

        WalkWalkRevolution.setUserDao(new MockUserDao());
        WalkWalkRevolution.setUser(new User(1, 528*12, "Brian Li", "email"));

        Activity activity;

        Map<String, String> data = new HashMap<String, String>(){{
            put(Walk.STEP_COUNT,"1000");
            put(Walk.MILES, "2");
            put(Walk.DURATION, "00:01");
        }};

        activity = new Walk(data);
        activity.setDate();
        activity.setExist(true);


        route = new Route("test", activity);
        route.setLocation("location test");
        route.setNotes("Testing the notes field to see it it actually shows");
        route.setDescriptionTags("a,b,c,d,e");
        route.setUserId("xxx");

        Map<String, String> responses = new HashMap<>();
        responses.put("Brian Li", "DECLINE_BAD_TIME");
        route.setResponses(responses);

        intent = new Intent(ApplicationProvider.getApplicationContext(), ProposeScreenActivity.class);
    }

    @After
    public void teardown(){
        ProposalFirestoreService.userProposed = "";
        ProposalFirestoreService.proposedRoute = null;
    }

    @Test
    public void noProposal(){
        ActivityScenario<ProposeScreenActivity> scenario = ActivityScenario.launch(intent);
        scenario.onActivity(activity -> {
            TextView title = activity.findViewById(R.id.title1);
            assertEquals("No Proposed Walk", title.getText().toString());
        });
    }

    @Test
    public void hasProposal(){
        ActivityScenario<ProposeScreenActivity> scenario = ActivityScenario.launch(intent);
        scenario.onActivity(activity -> {
            ProposalFirestoreService.userProposed = "email";
            activity.displayRouteDetail(route);
            TextView sctitle = activity.findViewById(R.id.pscreen_title);
            TextView title = activity.findViewById(R.id.title1);
            assertEquals("Proposed Walk", sctitle.getText().toString());
            assertEquals("test", title.getText().toString());

            TextView location = activity.findViewById(R.id.location_text);
            assertEquals(location.getText().toString(), "location test");

            TextView tag1 = activity.findViewById(R.id.tag1);
            TextView tag2 = activity.findViewById(R.id.tag2);
            TextView tag3 = activity.findViewById(R.id.tag3);
            TextView tag4 = activity.findViewById(R.id.tag4);
            TextView tag5 = activity.findViewById(R.id.tag5);
            assertEquals(tag1.getText().toString(), "a");
            assertEquals(tag2.getText().toString(), "b");
            assertEquals(tag3.getText().toString(), "c");
            assertEquals(tag4.getText().toString(), "d");
            assertEquals(tag5.getText().toString(), "e");

            TextView notes = activity.findViewById(R.id.Note_view);
            assertEquals(notes.getText().toString(), "Testing the notes field to see it it actually shows");
        });
    }

    @Test
    public void setScheduled(){
        ActivityScenario<ProposeScreenActivity> scenario = ActivityScenario.launch(intent);
        scenario.onActivity(activity -> {
            ProposalFirestoreService.userProposed = "email";
            activity.displayRouteDetail(route);

            Button btn = activity.findViewById(R.id.schedule_walk);
            btn.performClick();
            ShadowLooper.runUiThreadTasksIncludingDelayedTasks();

            TextView sctitle = activity.findViewById(R.id.pscreen_title);
            assertEquals("Scheduled Walk", sctitle.getText());
        });
    }

    @Test
    public void withdrawProposed(){
        ActivityScenario<ProposeScreenActivity> scenario = ActivityScenario.launch(intent);
        scenario.onActivity(activity -> {
            ProposalFirestoreService.userProposed = "email";
            activity.displayRouteDetail(route);

            Button btn = activity.findViewById(R.id.withdraw_walk);
            btn.performClick();
            ShadowLooper.runUiThreadTasksIncludingDelayedTasks();

            TextView sctitle = activity.findViewById(R.id.pscreen_title);
            assertEquals("No Proposed Walk", sctitle.getText());
        });
    }

    @Test
    public void withdrawScheduled(){
        ActivityScenario<ProposeScreenActivity> scenario = ActivityScenario.launch(intent);
        scenario.onActivity(activity -> {
            ProposalFirestoreService.userProposed = "email";
            activity.displayRouteDetail(route);

            Button btn = activity.findViewById(R.id.schedule_walk);
            btn.performClick();
            ShadowLooper.runUiThreadTasksIncludingDelayedTasks();

            TextView sctitle = activity.findViewById(R.id.pscreen_title);
            assertEquals("Scheduled Walk", sctitle.getText());

            btn = activity.findViewById(R.id.withdraw_walkafter);
            btn.performClick();
            ShadowLooper.runUiThreadTasksIncludingDelayedTasks();

            sctitle = activity.findViewById(R.id.pscreen_title);
            assertEquals("No Proposed Walk", sctitle.getText());
        });
    }

    @Test
    public void moveIsAccepted(){
        ActivityScenario<ProposeScreenActivity> scenario = ActivityScenario.launch(intent);
        scenario.onActivity(activity -> {
            ProposalFirestoreService.userProposed = "email";
            ProposalFirestoreService.proposedRoute = route;

            activity.displayRouteDetail(route);

            TextView sctitle = activity.findViewById(R.id.pscreen_title);
            assertEquals("Proposed Walk", sctitle.getText().toString());

            Button btn = activity.findViewById(R.id.Accepted);

            RecyclerView rvDBT = (RecyclerView) activity.findViewById(R.id.DCLNEBTlist);
            assertEquals(1, rvDBT.getAdapter().getItemCount());
            DeclineBTRVAdapter.DBTViewHolder adapterACC = (DeclineBTRVAdapter.DBTViewHolder)rvDBT.findViewHolderForLayoutPosition(0);
            assertEquals("Brian Li", adapterACC.userName.getText().toString());

            btn.performClick();
            ShadowLooper.runUiThreadTasksIncludingDelayedTasks();

            activity.displayRouteDetail(route);

            RecyclerView rvACC = (RecyclerView) activity.findViewById(R.id.ACCPTEDlist);
            assertEquals(1, rvACC.getAdapter().getItemCount());
            AcceptedRVAdapter.AcceptViewHolder holder = (AcceptedRVAdapter.AcceptViewHolder) rvACC.findViewHolderForLayoutPosition(0);
            assertEquals("Brian Li", holder.userName.getText().toString());
        });
    }

}
