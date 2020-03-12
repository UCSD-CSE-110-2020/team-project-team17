package edu.ucsd.cse110.walkwalkrevolution.route;

import edu.ucsd.cse110.walkwalkrevolution.route.Route;
import edu.ucsd.cse110.walkwalkrevolution.user.User;

public class RouteResponse {

    public RouteResponse (Route route, User user) { }

    public static void addResponse(Route route, User user, String response) {
        route.getResponses().put(user.getEmail(), response);
    }

    public static void changeResponse(Route route, User user, String response) {
        route.getResponses().replace(user.getEmail(), response);
    }
}
