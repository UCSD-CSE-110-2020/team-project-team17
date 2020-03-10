package edu.ucsd.cse110.walkwalkrevolution.invitation;

public interface InvitationSubject {
    void subscribe(InvitationsObserver observer);
    void unsubscribe(InvitationsObserver observer);
    void notifyObservers();
}
