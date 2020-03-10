package edu.ucsd.cse110.walkwalkrevolution;

import androidx.recyclerview.widget.RecyclerView;
import androidx.test.core.app.ActivityScenario;
import androidx.test.ext.junit.runners.AndroidJUnit4;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;

import edu.ucsd.cse110.walkwalkrevolution.activity.Activity;
import edu.ucsd.cse110.walkwalkrevolution.activity.ActivityUtils;
import edu.ucsd.cse110.walkwalkrevolution.activity.EmptyActivity;
import edu.ucsd.cse110.walkwalkrevolution.activity.Walk;
import edu.ucsd.cse110.walkwalkrevolution.route.Route;
import edu.ucsd.cse110.walkwalkrevolution.route.RouteRecycleView.RoutesAdapter;
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
        Activity walk = new EmptyActivity();
        walk.setDate(LocalDateTime.of(2020, 1, 1, 0, 0));
        WalkWalkRevolution.getRouteDao().addRoute(new Route(1, "Route 1", walk));

        try(ActivityScenario<RoutesActivity> scenario = ActivityScenario.launch(RoutesActivity.class)){
            scenario.onActivity(activity -> {
                RecyclerView currentRecyclerView = ((RecyclerView) activity.findViewById(R.id.routes));
                assertEquals(1, currentRecyclerView.getAdapter().getItemCount());
                RoutesAdapter.ViewHolder holder = (RoutesAdapter.ViewHolder) currentRecyclerView.findViewHolderForLayoutPosition(0);
                assertEquals("Route 1", holder.routeTitle.getText().toString());
                assertEquals("", holder.steps.getText().toString());
                assertEquals("", holder.miles.getText().toString());
                assertEquals("", holder.duration.getText().toString());
                assertEquals("", holder.date.getText().toString());
            });
        }
    }

    @Test
    public void hasMultipleStoredRoutes(){
        Activity walk = new EmptyActivity();
        walk.setDate(LocalDateTime.of(2020, 1, 1, 0, 0));
        WalkWalkRevolution.getRouteDao().addRoute(new Route(1, "z", walk));

        LocalDateTime time = LocalDateTime.of(2020, 01, 01, 0, 0);
        Map<String, String> data = new HashMap<String, String>() {{
            put(Walk.STEP_COUNT, "500");
            put(Walk.MILES, "0.25");
            put(Walk.DURATION, "5:00");
            put(Activity.DATE, ActivityUtils.timeToString(time));
        }};
        Activity walk2 = new Walk(data);
        walk2.setExist(true);

        WalkWalkRevolution.getRouteDao().addRoute(new Route(2, "a", walk2));


        try(ActivityScenario<RoutesActivity> scenario = ActivityScenario.launch(RoutesActivity.class)){
            scenario.onActivity(activity -> {
                RecyclerView currentRecyclerView = ((RecyclerView) activity.findViewById(R.id.routes));
                assertEquals(2, currentRecyclerView.getAdapter().getItemCount());

                //Note: Also, tests alphabetical ordering

                RoutesAdapter.ViewHolder holder = (RoutesAdapter.ViewHolder) currentRecyclerView.findViewHolderForLayoutPosition(0);
                assertEquals("a", holder.routeTitle.getText().toString());
                assertEquals("500", holder.steps.getText().toString());
                assertEquals("0.25", holder.miles.getText().toString());
                assertEquals("5:00", holder.duration.getText().toString());
                assertEquals("01/01", holder.date.getText().toString());

                holder = (RoutesAdapter.ViewHolder) currentRecyclerView.findViewHolderForLayoutPosition(1);
                assertEquals("z", holder.routeTitle.getText().toString());
                assertEquals("", holder.steps.getText().toString());
                assertEquals("", holder.miles.getText().toString());
                assertEquals("", holder.duration.getText().toString());
                assertEquals("", holder.date.getText().toString());
            });
        }
    }
}
