package edu.ucsd.cse110.walkwalkrevolution;


import org.junit.Before;
import org.junit.Test;

import java.util.Map;

import edu.ucsd.cse110.walkwalkrevolution.activity.Activity;
import edu.ucsd.cse110.walkwalkrevolution.activity.Walk;
import edu.ucsd.cse110.walkwalkrevolution.route.Route;
import edu.ucsd.cse110.walkwalkrevolution.route.persistence.BaseRouteDao;
import edu.ucsd.cse110.walkwalkrevolution.route.persistence.MockRouteDao;

import static junit.framework.TestCase.assertEquals;
import static junit.framework.TestCase.assertNotNull;
import static junit.framework.TestCase.assertNull;

public class BaseRouteDaoTest {

    BaseRouteDao dao;

    @Before
    public void setup() {
        WalkWalkRevolution.setRouteDao(new MockRouteDao());
        dao = WalkWalkRevolution.getRouteDao();
    }

    @Test
    public void addValidRoute() {
        Route actual = new Route(1, "Route1", new Activity(), "");
        dao.addRoute(actual);

        Route persisted = dao.getRoute(1);
        assertNotNull(persisted);
        assertEquals(actual.getId(), persisted.getId());

        Map<String, ?> allRoutes = dao.getAllRoutes();
        assertEquals(1, allRoutes.size());
        assertEquals(true, allRoutes.containsKey("1"));
    }

    @Test
    public void getNonExistentRoute() {
        Route route = dao.getRoute(1);
        assertNull(route);

        Map<String, ?> allRoutes = dao.getAllRoutes();
        assertEquals(0, allRoutes.size());
    }

    @Test
    public void addMultipleRoutes() {
        Route r1 = new Route(1, "Route1", new Activity(), "");
        Route r2 = new Route(2, "Route2", new Activity(), "");
        Route r3 = new Route(3, "Route3", new Activity(), "");
        dao.addRoute(r1);
        dao.addRoute(r2);
        dao.addRoute(r3);

        Route persisted = dao.getRoute(1);
        assertNotNull(persisted);
        assertEquals(r1.getId(), persisted.getId());
        persisted = dao.getRoute(2);
        assertNotNull(persisted);
        assertEquals(r2.getId(), persisted.getId());
        persisted = dao.getRoute(3);
        assertNotNull(persisted);
        assertEquals(r3.getId(), persisted.getId());

        Map<String, ?> allRoutes = dao.getAllRoutes();
        assertEquals(3, allRoutes.size());
        assertEquals(true, allRoutes.containsKey("1"));
        assertEquals(true, allRoutes.containsKey("2"));
        assertEquals(true, allRoutes.containsKey("3"));
    }

    @Test
    public void testGetNextId() {
        Route r1 = new Route("Route1", new Walk());
        Route r2 = new Route("Route2", new Walk());
        Route r3 = new Route("Route3", new Walk());
        dao.addRoute(r1);
        dao.addRoute(r2);
        dao.addRoute(r3);

        Route persisted = dao.getRoute(1);
        assertNotNull(persisted);
        assertEquals(r1.getId(), persisted.getId());
        persisted = dao.getRoute(2);
        assertNotNull(persisted);
        assertEquals(r2.getId(), persisted.getId());
        persisted = dao.getRoute(3);
        assertNotNull(persisted);
        assertEquals(r3.getId(), persisted.getId());

        Map<String, ?> allRoutes = dao.getAllRoutes();
        assertEquals(3, allRoutes.size());
        assertEquals(true, allRoutes.containsKey("1"));
        assertEquals(true, allRoutes.containsKey("2"));
        assertEquals(true, allRoutes.containsKey("3"));
    }

}
