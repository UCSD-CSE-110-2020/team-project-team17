package edu.ucsd.cse110.walkwalkrevolution;

import android.widget.Button;
import android.widget.TextView;

import androidx.test.core.app.ActivityScenario;
import androidx.test.ext.junit.runners.AndroidJUnit4;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

import edu.ucsd.cse110.walkwalkrevolution.route.Route;
import edu.ucsd.cse110.walkwalkrevolution.route.persistence.MockRouteDao;
import edu.ucsd.cse110.walkwalkrevolution.user.User;
import edu.ucsd.cse110.walkwalkrevolution.user.persistence.MockUserDao;

import static junit.framework.TestCase.assertEquals;
import static junit.framework.TestCase.assertNotNull;

import org.robolectric.shadows.ShadowLooper;


@RunWith(AndroidJUnit4.class)
public class CreateRouteTest {

    @Before
    public void setup(){
        WalkWalkRevolution.setRouteDao(new MockRouteDao());
        WalkWalkRevolution.setUserDao(new MockUserDao());
        WalkWalkRevolution.setUser(new User(1, 528*12));
    }

    @Test
    public void routeWithTitleSave(){
        try(ActivityScenario<CreateRouteActivity> scenario = ActivityScenario.launch(CreateRouteActivity.class)){
            scenario.onActivity(activity -> {
                TextView routeTitle = activity.findViewById(R.id.route_title);
                Button save = (Button) activity.findViewById(R.id.save_button);

                routeTitle.setText("Route 1");

                save.performClick();
                ShadowLooper.runUiThreadTasksIncludingDelayedTasks();
                assertEquals(true, activity.isFinishing());

                Route route = WalkWalkRevolution.getRouteDao().getRoute(1);
                assertNotNull(route);
                assertEquals(1, route.getId());
                assertEquals("Route 1", route.getTitle());
            });
        }
    }

    @Test
    public void routeWithNoTitleSave(){
        try(ActivityScenario<CreateRouteActivity> scenario = ActivityScenario.launch(CreateRouteActivity.class)){
            scenario.onActivity(activity -> {
                TextView routeTitle = activity.findViewById(R.id.route_title);
                Button save = (Button) activity.findViewById(R.id.save_button);
                save.performClick();
                assertEquals("Route Title is Required", routeTitle.getError().toString());
            });
        }
    }

    @Test
    public void routeWithTitleCancel(){
        try(ActivityScenario<CreateRouteActivity> scenario = ActivityScenario.launch(CreateRouteActivity.class)){
            scenario.onActivity(activity -> {
                TextView routeTitle = activity.findViewById(R.id.route_title);
                Button cancel = (Button) activity.findViewById(R.id.cancel_button);

                routeTitle.setText("Route 1");

                cancel.performClick();
                ShadowLooper.runUiThreadTasksIncludingDelayedTasks();
                assertEquals(true, activity.isFinishing());
            });
        }
    }

    @Test
    public void routeWithNoTitleCancel(){
        try(ActivityScenario<CreateRouteActivity> scenario = ActivityScenario.launch(CreateRouteActivity.class)){
            scenario.onActivity(activity -> {
                TextView routeTitle = activity.findViewById(R.id.route_title);
                Button cancel = (Button) activity.findViewById(R.id.cancel_button);
                cancel.performClick();
                ShadowLooper.runUiThreadTasksIncludingDelayedTasks();
                assertEquals(true, activity.isFinishing());
            });
        }
    }

}
