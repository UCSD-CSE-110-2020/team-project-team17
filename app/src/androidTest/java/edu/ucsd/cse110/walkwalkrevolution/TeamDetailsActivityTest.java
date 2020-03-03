package edu.ucsd.cse110.walkwalkrevolution;

import androidx.recyclerview.widget.RecyclerView;
import androidx.test.core.app.ActivityScenario;
import androidx.test.ext.junit.runners.AndroidJUnit4;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

import edu.ucsd.cse110.walkwalkrevolution.route.RouteRecycleView.RoutesAdapter;
import edu.ucsd.cse110.walkwalkrevolution.route.persistence.MockRouteDao;

import static junit.framework.TestCase.assertEquals;

@RunWith(AndroidJUnit4.class)
public class TeamDetailsActivityTest {

    @Before
    public void setUp() {
        TeamDetailsActivity.testMode = true; // Bigbrain
        WalkWalkRevolution.setRouteDao(new MockRouteDao());
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
