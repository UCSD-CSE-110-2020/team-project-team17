package edu.ucsd.cse110.walkwalkrevolution;

import androidx.recyclerview.widget.RecyclerView;
import androidx.test.core.app.ActivityScenario;
import androidx.test.ext.junit.runners.AndroidJUnit4;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

import java.time.LocalDateTime;

import edu.ucsd.cse110.walkwalkrevolution.activity.Activity;
import edu.ucsd.cse110.walkwalkrevolution.activity.Walk;
import edu.ucsd.cse110.walkwalkrevolution.route.Route;
import edu.ucsd.cse110.walkwalkrevolution.route.persistence.MockRouteDao;

import static junit.framework.TestCase.assertEquals;


@RunWith(AndroidJUnit4.class)
public class RoutesTest {

    @Before
    public void setup(){
        WalkWalkRevolution.setRouteDao(new MockRouteDao());

    }

    @Test
    public void noStoredRoutes(){
        try(ActivityScenario<RoutesActivity> scenario = ActivityScenario.launch(RoutesActivity.class)){
            scenario.onActivity(activity -> {
                RecyclerView currentRecyclerView = ((RecyclerView) activity.findViewById(R.id.routes));
                assertEquals(0, currentRecyclerView.getAdapter().getItemCount());
            });
        }
    }

    @Test
    public void hasStoredRoutes(){
        Activity walk = new Walk();
        walk.setDate(LocalDateTime.of(2020, 1, 1, 0, 0));
        WalkWalkRevolution.getRouteDao().addRoute(new Route(1, "Route 1", walk));

        try(ActivityScenario<RoutesActivity> scenario = ActivityScenario.launch(RoutesActivity.class)){
            scenario.onActivity(activity -> {
                RecyclerView currentRecyclerView = ((RecyclerView) activity.findViewById(R.id.routes));
                assertEquals(1, currentRecyclerView.getAdapter().getItemCount());
                RoutesAdapter.ViewHolder holder = (RoutesAdapter.ViewHolder) currentRecyclerView.findViewHolderForLayoutPosition(0);
                assertEquals("Route 1", holder.routeTitle.getText().toString());
                assertEquals("0", holder.steps.getText().toString());
                assertEquals("0", holder.miles.getText().toString());
                assertEquals("0", holder.duration.getText().toString());
                assertEquals("01/01", holder.date.getText().toString());
            });
        }
    }
}
