package edu.ucsd.cse110.walkwalkrevolution.route.persistence;

import java.util.Map;

import edu.ucsd.cse110.walkwalkrevolution.route.Route;

public interface BaseRouteDao {

    void addRoute(Route route);

    void addTeamRoute(Route route);

    Route getRoute(long routeId);

    Map<String, ?> getAllRoutes();

    Map<String, ?> getTeamRoutes();

    long getNextId();

}
