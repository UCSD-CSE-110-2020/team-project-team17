package edu.ucsd.cse110.walkwalkrevolution;

import android.content.Intent;
import android.widget.TextView;

import androidx.test.ext.junit.runners.AndroidJUnit4;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.robolectric.Robolectric;
import org.robolectric.annotation.Config;


import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ExecutionException;

import edu.ucsd.cse110.walkwalkrevolution.activity.Activity;
import edu.ucsd.cse110.walkwalkrevolution.activity.Walk;
import edu.ucsd.cse110.walkwalkrevolution.route.Route;
import edu.ucsd.cse110.walkwalkrevolution.route.RouteRecycleView.RoutesAdapter;
import edu.ucsd.cse110.walkwalkrevolution.route.RouteUtils;
import edu.ucsd.cse110.walkwalkrevolution.route.persistence.MockRouteDao;
import edu.ucsd.cse110.walkwalkrevolution.user.User;
import edu.ucsd.cse110.walkwalkrevolution.user.persistence.MockUserDao;

import static com.google.common.truth.Truth.assertThat;
import static junit.framework.TestCase.assertEquals;
import static junit.framework.TestCase.assertNotNull;

@RunWith(AndroidJUnit4.class)
@Config(sdk=28)

public class RoutesDetailActivityTest {

    private RoutesDetailActivity activityRoutes;
    private String serializedRoute;

    @Before
    public void setUp() {
        WalkWalkRevolution.setUserDao(new MockUserDao());
        WalkWalkRevolution.setRouteDao(new MockRouteDao());
        WalkWalkRevolution.setUser(new User(1, 528*12, "", ""));

        Activity activity;

        Map<String, String> data = new HashMap<String, String>(){{
            put(Walk.STEP_COUNT,"1000");
            put(Walk.MILES, "2");
            put(Walk.DURATION, "00:01");
        }};

        activity = new Walk(data);
        activity.setDate();
        activity.setExist(true);


        Route route = new Route("test", activity);
        route.setLocation("location test");
        route.setNotes("Testing the notes field to see it it actually shows");
        route.setDescriptionTags("a,b,c,d,e");

        try {
            serializedRoute = RouteUtils.serialize(route);
        } catch (Exception e) {
            //
        }

        WalkWalkRevolution.getRouteDao().addRoute(route);

    }

    @Test
    public void testRouteTitleDetail(){
        Intent intent = new Intent();
        intent.putExtra(RoutesAdapter.ROUTE, serializedRoute);
        activityRoutes = Robolectric.buildActivity(RoutesDetailActivity.class, intent).create().get();
        TextView routeTitle = activityRoutes.findViewById(R.id.title1);
        assertEquals(routeTitle.getText().toString(), "test");
    }

    @Test
    public void testLocationDetail(){
        Intent intent = new Intent();
        intent.putExtra(RoutesAdapter.ROUTE, serializedRoute);
        activityRoutes = Robolectric.buildActivity(RoutesDetailActivity.class, intent).create().get();
        TextView location = activityRoutes.findViewById(R.id.location_text);
        assertEquals(location.getText().toString(), "location test");
    }

    @Test
    public void testSteps(){
        Intent intent = new Intent();
        intent.putExtra(RoutesAdapter.ROUTE, serializedRoute);
        activityRoutes = Robolectric.buildActivity(RoutesDetailActivity.class, intent).create().get();
        TextView steps = activityRoutes.findViewById(R.id.numOfSteps);
        assertEquals(steps.getText().toString(), "1000");
    }

    @Test
    public void testMiles(){
        Intent intent = new Intent();
        intent.putExtra(RoutesAdapter.ROUTE, serializedRoute);
        activityRoutes = Robolectric.buildActivity(RoutesDetailActivity.class, intent).create().get();
        TextView miles = activityRoutes.findViewById(R.id.numOfMiles);
        assertEquals(miles.getText().toString(), "2");
    }

    @Test
    public void testDuration(){
        Intent intent = new Intent();
        intent.putExtra(RoutesAdapter.ROUTE, serializedRoute);
        activityRoutes = Robolectric.buildActivity(RoutesDetailActivity.class, intent).create().get();
        TextView duration = activityRoutes.findViewById(R.id.numOfDur);
        assertEquals(duration.getText().toString(), "00:01");
    }

    @Test
    public void testTags(){
        Intent intent = new Intent();
        intent.putExtra(RoutesAdapter.ROUTE, serializedRoute);
        activityRoutes = Robolectric.buildActivity(RoutesDetailActivity.class, intent).create().get();
        TextView tag1 = activityRoutes.findViewById(R.id.tag1);
        TextView tag2 = activityRoutes.findViewById(R.id.tag2);
        TextView tag3 = activityRoutes.findViewById(R.id.tag3);
        TextView tag4 = activityRoutes.findViewById(R.id.tag4);
        TextView tag5 = activityRoutes.findViewById(R.id.tag5);

        assertEquals(tag1.getText().toString(), "a");
        assertEquals(tag2.getText().toString(), "b");
        assertEquals(tag3.getText().toString(), "c");
        assertEquals(tag4.getText().toString(), "d");
        assertEquals(tag5.getText().toString(), "e");
    }

    @Test
    public void testNotes(){
        Intent intent = new Intent();
        intent.putExtra(RoutesAdapter.ROUTE, serializedRoute);
        activityRoutes = Robolectric.buildActivity(RoutesDetailActivity.class, intent).create().get();
        TextView notes = activityRoutes.findViewById(R.id.Note_view);
        assertEquals(notes.getText().toString(), "Testing the notes field to see it it actually shows");
    }
}
