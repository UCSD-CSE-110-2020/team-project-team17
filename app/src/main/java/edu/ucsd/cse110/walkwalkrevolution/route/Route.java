package edu.ucsd.cse110.walkwalkrevolution.route;

public class Route {

    long id;

    public Route(long id){
        this.id = id;
    }

    public void save() {
        RoutePersistence.addRoute(this);
    }

}