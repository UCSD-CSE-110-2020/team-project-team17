package edu.ucsd.cse110.walkwalkrevolution.user.invite;

import java.util.HashMap;
import java.util.Map;

import edu.ucsd.cse110.walkwalkrevolution.WalkWalkRevolution;
import edu.ucsd.cse110.walkwalkrevolution.user.User;

public class Invitation {

    private final String TO = "to";
    private final String FROM = "from";

    private User inviter;
    private User invitee;

    //assumption that only *this* user makes the invitation
    public Invitation(User invitee){
        this.inviter = WalkWalkRevolution.getUser();
        this.invitee = invitee;
    }

    public Map<String, String> toMap(){
        Map<String, String> map = new HashMap<>();
        map.put(FROM, this.inviter.getEmail());
        map.put(TO, this.invitee.getEmail());
        return map;
    }

    public User getInviter(){
        return this.inviter;
    }

    public User getInvitee(){
        return this.invitee;
    }

}
