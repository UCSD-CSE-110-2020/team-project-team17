package edu.ucsd.cse110.walkwalkrevolution.route;

public interface RoutesSubject {
    void subscribe(RoutesObserver observer);
    void unsubscribe(RoutesObserver observer);
    void notifyObservers();
}
