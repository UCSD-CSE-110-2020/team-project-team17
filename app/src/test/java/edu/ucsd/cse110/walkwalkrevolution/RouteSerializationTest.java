package edu.ucsd.cse110.walkwalkrevolution;

import org.junit.Test;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;

import edu.ucsd.cse110.walkwalkrevolution.activity.Activity;
import edu.ucsd.cse110.walkwalkrevolution.activity.ActivityUtils;
import edu.ucsd.cse110.walkwalkrevolution.activity.EmptyActivity;
import edu.ucsd.cse110.walkwalkrevolution.activity.Walk;
import edu.ucsd.cse110.walkwalkrevolution.route.Route;
import edu.ucsd.cse110.walkwalkrevolution.route.RouteUtils;

import static junit.framework.TestCase.assertEquals;

public class RouteSerializationTest {

    @Test
    public void serializeRouteWithNoActivity() throws Exception{
        Activity activity = new EmptyActivity();
        LocalDateTime time = LocalDateTime.of(2020, 01, 01, 0, 0);
        activity.setDate(time);

        Route route = new Route(1, "Route1", activity);


        String jsonString = RouteUtils.serialize(route);
        System.err.println(jsonString);

        Route deserial = RouteUtils.deserialize(jsonString);
        assertEquals(1, deserial.getId());
        assertEquals("Route1", deserial.getTitle());
        assertEquals(2, deserial.getActivity().getDetails().size());
        assertEquals("false", deserial.getActivity().getDetail(Activity.EXIST));
        assertEquals(time, ActivityUtils.stringToTime(deserial.getActivity().getDetail(Activity.DATE)));
    }

    @Test
    public void serializeRouteWithLocation() throws Exception{
        Activity activity = new Walk();
        LocalDateTime time = LocalDateTime.of(2020, 01, 01, 0, 0);
        activity.setDate(time);

        Route route = new Route(1, "Route1", activity);

        route.setLocation("Location");

        String jsonString = RouteUtils.serialize(route);
        System.err.println(jsonString);

        Route deserial = RouteUtils.deserialize(jsonString);
        assertEquals(1, deserial.getId());
        assertEquals("Route1", deserial.getTitle());
        assertEquals("Location", deserial.getLocation());
        assertEquals(5, deserial.getActivity().getDetails().size());
        assertEquals("0", deserial.getActivity().getDetail(Walk.STEP_COUNT));
        assertEquals("0", deserial.getActivity().getDetail(Walk.DURATION));
        assertEquals("0", deserial.getActivity().getDetail(Walk.MILES));
        assertEquals(time, ActivityUtils.stringToTime(deserial.getActivity().getDetail(Activity.DATE)));
    }

    @Test
    public void serializeRouteWithActivity() throws Exception{
        LocalDateTime time = LocalDateTime.of(2020, 01, 01, 0, 0);
        Map<String, String> data = new HashMap<String, String>() {{
            put(Walk.STEP_COUNT, "500");
            put(Walk.MILES, "0.25");
            put(Walk.DURATION, "5:00");
            put(Activity.DATE, ActivityUtils.timeToString(time));
        }};
        Route route = new Route(1, "Route1", new Walk(data));

        String jsonString = RouteUtils.serialize(route);

        Route deserial = RouteUtils.deserialize(jsonString);
        assertEquals(1, deserial.getId());
        assertEquals("Route1", deserial.getTitle());
        assertEquals(5, deserial.getActivity().getDetails().size());
        assertEquals("500", deserial.getActivity().getDetail(Walk.STEP_COUNT));
        assertEquals("5:00", deserial.getActivity().getDetail(Walk.DURATION));
        assertEquals("0.25", deserial.getActivity().getDetail(Walk.MILES));
        assertEquals(time, ActivityUtils.stringToTime(deserial.getActivity().getDetail(Activity.DATE)));
    }

    @Test
    public void serializeRouteWithNotes() throws Exception{
        String note = "A longer string that would realistically function as a note.";
        LocalDateTime time = LocalDateTime.of(2020, 1, 1, 0, 0);
        Map<String, String> data = new HashMap<String, String>() {{
            put(Walk.STEP_COUNT, "500");
            put(Walk.MILES, "0.25");
            put(Walk.DURATION, "5:00");
            put(Activity.DATE, ActivityUtils.timeToString(time));
        }};
        Route route = new Route(1, "Route1", new Walk(data));
        route.setNotes(note);

        String jsonString = RouteUtils.serialize(route);

        Route deserial = RouteUtils.deserialize(jsonString);
        assertEquals(1, deserial.getId());
        assertEquals("Route1", deserial.getTitle());
        assertEquals(5, deserial.getActivity().getDetails().size());
        assertEquals("500", deserial.getActivity().getDetail(Walk.STEP_COUNT));
        assertEquals("5:00", deserial.getActivity().getDetail(Walk.DURATION));
        assertEquals("0.25", deserial.getActivity().getDetail(Walk.MILES));
        assertEquals(time, ActivityUtils.stringToTime(deserial.getActivity().getDetail(Activity.DATE)));
        assertEquals(note, deserial.getNotes());
    }
}
