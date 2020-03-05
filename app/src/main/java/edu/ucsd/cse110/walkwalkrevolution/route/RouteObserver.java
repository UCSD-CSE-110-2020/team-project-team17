package edu.ucsd.cse110.walkwalkrevolution.route;

import java.util.List;

import edu.ucsd.cse110.walkwalkrevolution.user.User;

public interface RouteObserver {
    void update(List<Route> routes);
}
