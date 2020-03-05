package edu.ucsd.cse110.walkwalkrevolution.user.invite;

import android.util.Log;

import java.util.HashMap;
import java.util.Map;

import edu.ucsd.cse110.walkwalkrevolution.WalkWalkRevolution;
import edu.ucsd.cse110.walkwalkrevolution.user.User;

public class Invitation {

    private final String TAG = "Invitation";

    public static final String TO = "to";
    public static final String FROM = "from";

    private User sender;
    private User receiver;


    //assumption that only *this* user makes the invitation
    public Invitation(User sender, User receiver){
        this.sender = sender;
        this.receiver = receiver;
    }

    public Invitation(User sender, String receiverEmail){
        this.sender = sender;
        WalkWalkRevolution.getUserService().getReceiver(this, receiverEmail);
    }

    // empty constructor
    public Invitation(){ }

    public Map<String, String> toMap(){
        Log.d(TAG, WalkWalkRevolution.getUser().getEmail());
        Map<String, String> map = new HashMap<>();
        map.put(FROM, this.sender.getEmail());
        map.put(TO, this.receiver.getEmail());
        return map;
    }

    public User getSender(){
        return this.sender;
    }

    public User getReceiver(){
        return this.receiver;
    }

    public void setReceiver(User user){
        this.receiver = user;
    }

    public void acceptInvitation(){
        this.receiver.setTeamId(this.sender.getTeamId());
    }

}
