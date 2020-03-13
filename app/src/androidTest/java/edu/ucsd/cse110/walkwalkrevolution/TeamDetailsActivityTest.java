package edu.ucsd.cse110.walkwalkrevolution;

import androidx.recyclerview.widget.RecyclerView;
import androidx.test.core.app.ActivityScenario;
import androidx.test.ext.junit.runners.AndroidJUnit4;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

import edu.ucsd.cse110.walkwalkrevolution.route.RouteRecycleView.RoutesAdapter;
import edu.ucsd.cse110.walkwalkrevolution.route.persistence.MockRouteDao;
import edu.ucsd.cse110.walkwalkrevolution.team.TeamRecycleView.TeamAdapter;

import static junit.framework.TestCase.assertEquals;

@RunWith(AndroidJUnit4.class)
public class TeamDetailsActivityTest {

    @Before
    public void setUp() {
        TeamDetailsActivity.testMode = true; // Bigbrain
        WalkWalkRevolution.setRouteDao(new MockRouteDao());
    }


    @Test
    public void correctNumberOfEntries(){
        try(ActivityScenario<TeamDetailsActivity> scenario = ActivityScenario.launch(TeamDetailsActivity.class)){
            scenario.onActivity(activity -> {
                RecyclerView currentRecyclerView = ((RecyclerView) activity.findViewById(R.id.rvUsers));
                assertEquals(50, currentRecyclerView.getAdapter().getItemCount());
            });
        }
    }

    @Test
    public void testCorrectEntries(){
        try(ActivityScenario<TeamDetailsActivity> scenario = ActivityScenario.launch(TeamDetailsActivity.class)){
            scenario.onActivity(activity -> {
                RecyclerView currentRecyclerView = ((RecyclerView) activity.findViewById(R.id.rvUsers));
                TeamAdapter.ViewHolder holder = (TeamAdapter.ViewHolder) currentRecyclerView.findViewHolderForLayoutPosition(0);
                assertEquals("a 0", holder.nameField.getText().toString());
                assertEquals("0@email.com", holder.emailField.getText().toString());
            });
        }
    }

}
