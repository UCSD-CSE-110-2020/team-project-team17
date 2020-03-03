package edu.ucsd.cse110.walkwalkrevolution;

import android.content.Intent;
import android.os.Bundle;

import androidx.recyclerview.widget.RecyclerView;
import androidx.test.core.app.ActivityScenario;
import androidx.test.ext.junit.runners.AndroidJUnit4;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;

import edu.ucsd.cse110.walkwalkrevolution.activity.Activity;
import edu.ucsd.cse110.walkwalkrevolution.activity.ActivityUtils;
import edu.ucsd.cse110.walkwalkrevolution.activity.Walk;
import edu.ucsd.cse110.walkwalkrevolution.route.Route;
import edu.ucsd.cse110.walkwalkrevolution.route.RouteRecycleView.RoutesAdapter;
import edu.ucsd.cse110.walkwalkrevolution.route.persistence.MockRouteDao;
import edu.ucsd.cse110.walkwalkrevolution.route.persistence.RouteService;
import edu.ucsd.cse110.walkwalkrevolution.route.persistence.RouteServiceFactory;
import edu.ucsd.cse110.walkwalkrevolution.team.Team;
import edu.ucsd.cse110.walkwalkrevolution.user.persistence.UserService;
import edu.ucsd.cse110.walkwalkrevolution.user.persistence.UserServiceFactory;

import static junit.framework.TestCase.assertEquals;
import static org.mockito.Mockito.when;

@RunWith(AndroidJUnit4.class)
public class TeamDetailsActivityTest {

    TeamDetailsActivity teamDetailsActivity;
    RecyclerView rvUsers;

    RouteService routeService;
    RouteServiceFactory routeServiceFactory;
    UserService userService;
    UserServiceFactory userServiceFactory;

    Team team;

    @Before
    public void setUp() {
        TeamDetailsActivity.testMode = true; // Bigbrain
        WalkWalkRevolution.setRouteDao(new MockRouteDao());

        team = Mockito.mock(Team.class);
    }


    @Test
    public void noTeammates(){
        try(ActivityScenario<TeamDetailsActivity> scenario = ActivityScenario.launch(TeamDetailsActivity.class)){
            scenario.onActivity(activity -> {
                RecyclerView currentRecyclerView = ((RecyclerView) activity.findViewById(R.id.routes));
                assertEquals(50, currentRecyclerView.getAdapter().getItemCount());
            });
        }
    }

    @Test
    public void testCorrectEntries(){
        try(ActivityScenario<TeamDetailsActivity> scenario = ActivityScenario.launch(TeamDetailsActivity.class)){
            scenario.onActivity(activity -> {
                RecyclerView currentRecyclerView = ((RecyclerView) activity.findViewById(R.id.routes));
                assertEquals(1, currentRecyclerView.getAdapter().getItemCount());
                RoutesAdapter.ViewHolder holder = (RoutesAdapter.ViewHolder) currentRecyclerView.findViewHolderForLayoutPosition(0);
                assertEquals("Route 1", holder.routeTitle.getText().toString());
                assertEquals("0", holder.steps.getText().toString());
            });
        }
    }

}
