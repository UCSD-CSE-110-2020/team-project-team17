package edu.ucsd.cse110.walkwalkrevolution;

import org.junit.Test;

import edu.ucsd.cse110.walkwalkrevolution.activity.Walk;
import edu.ucsd.cse110.walkwalkrevolution.route.Route;

import static junit.framework.TestCase.assertEquals;

public class RouteTest {

    @Test
    public void createRouteTest(){
        Route route = new Route(1, "Route1", new Walk());
        assertEquals("Route1", route.getTitle());
    }

    @Test
    public void changeRouteTest(){
        Route route = new Route(1, "Old Route", new Walk());
        route.setTitle("New Route");

        assertEquals("New Route", route.getTitle());
    }


}
