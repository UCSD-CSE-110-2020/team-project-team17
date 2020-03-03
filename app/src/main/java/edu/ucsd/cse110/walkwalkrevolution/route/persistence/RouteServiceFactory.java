package edu.ucsd.cse110.walkwalkrevolution.route.persistence;

public class RouteServiceFactory {

    public RouteService createRouteService(){
        return new RouteFirestoreService();
    }

}
