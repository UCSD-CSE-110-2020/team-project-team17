package edu.ucsd.cse110.walkwalkrevolution;

import com.fasterxml.jackson.databind.ObjectMapper;

import org.junit.Test;

import java.util.HashMap;
import java.util.Map;

import edu.ucsd.cse110.walkwalkrevolution.activity.Walk;
import edu.ucsd.cse110.walkwalkrevolution.route.Route;
import edu.ucsd.cse110.walkwalkrevolution.route.RouteUtils;

import static junit.framework.TestCase.assertEquals;

public class RouteSerializationTest {

    @Test
    public void serializeRouteWithNoActivity() throws Exception{
        Route route = new Route(1, new Walk());

        String jsonString = RouteUtils.serialize(route);

        Route deserial = RouteUtils.deserialize(jsonString);
        assertEquals(1, deserial.getId());
        assertEquals(3, deserial.getActivity().getDetails().size());
        assertEquals("0", deserial.getActivity().getDetail(Walk.STEP_COUNT));
        assertEquals("0", deserial.getActivity().getDetail(Walk.DURATION));
        assertEquals("0", deserial.getActivity().getDetail(Walk.MILES));
    }

    @Test
    public void serializeRouteWithActivity() throws Exception{
        Map<String, String> data = new HashMap<String, String>() {{
            put(Walk.STEP_COUNT, "500");
            put(Walk.MILES, "0.25");
            put(Walk.DURATION, "5:00");
        }};
        Route route = new Route(1, new Walk(data));

        String jsonString = RouteUtils.serialize(route);

        Route deserial = RouteUtils.deserialize(jsonString);
        assertEquals(1, deserial.getId());
        assertEquals(3, deserial.getActivity().getDetails().size());
        assertEquals("500", deserial.getActivity().getDetail(Walk.STEP_COUNT));
        assertEquals("5:00", deserial.getActivity().getDetail(Walk.DURATION));
        assertEquals("0.25", deserial.getActivity().getDetail(Walk.MILES));
    }
}
