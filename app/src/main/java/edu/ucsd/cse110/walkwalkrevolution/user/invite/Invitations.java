package edu.ucsd.cse110.walkwalkrevolution.user.invite;

import java.util.ArrayList;

public class Invitations {
    private ArrayList<Invitation> invitations;

    public Invitations(){
        invitations = new ArrayList<Invitation>();
    }

    public void addInvitation(Invitation invite){
        invitations.add(invite);
    }

    public Invitation getInvitation(){
        return invitations.isEmpty() ? null : invitations.remove(0);
    }
}
