package edu.ucsd.cse110.walkwalkrevolution.team;

public interface TeamSubject {
    void subscribe(TeamObserver observer);
    void unsubscribe(TeamObserver observer);
    void notifyObservers();
}
