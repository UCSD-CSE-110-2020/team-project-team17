package edu.ucsd.cse110.walkwalkrevolution.route;

import edu.ucsd.cse110.walkwalkrevolution.team.TeamObserver;

public interface RouteSubject {
    void subscribe(RouteObserver observer);
    void unsubscribe(RouteObserver observer);
    void notifyObservers();
}
