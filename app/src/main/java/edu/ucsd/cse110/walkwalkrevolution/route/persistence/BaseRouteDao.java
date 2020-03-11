package edu.ucsd.cse110.walkwalkrevolution.route.persistence;

import java.util.Map;

import edu.ucsd.cse110.walkwalkrevolution.route.Route;

public interface BaseRouteDao {

    void addRoute(Route route);

    void addTeamRoute(Route route);

    void addFavorite(Route route);

    Route getRoute(long routeId);

    boolean isFavorite(Route route);

    void removeFavorite(Route route);

    Map<String, ?> getTeamRoutes();

    Map<String, ?> getAllRoutes();

    boolean walkedTeamRoute(Route route);

    long getNextId();

}
