package edu.ucsd.cse110.walkwalkrevolution.invitation;

import java.util.ArrayList;
import java.util.List;

import edu.ucsd.cse110.walkwalkrevolution.WalkWalkRevolution;

public class Invitations implements InvitationSubject{

    List<Invitation> invitations;
    List<InvitationsObserver> observers;

    public Invitations(){
        this.invitations = new ArrayList<>();
        this.observers = new ArrayList<>();
        WalkWalkRevolution.getInvitationService().
                getInvitations(this, WalkWalkRevolution.getUser());
    }

    public Invitations(List<Invitation> invitations){
        this.invitations = invitations;
    }

    public void add(Invitation invitation){
        this.invitations.add(invitation);
    }

    @Override
    public void subscribe(InvitationsObserver observer){
        this.observers.add(observer);
    }

    @Override
    public void unsubscribe(InvitationsObserver observer){
        this.observers.remove(observer);
    }

    @Override
    public void notifyObservers(){
        for(InvitationsObserver obs: observers){
            obs.update(invitations);
        }
    }


}
