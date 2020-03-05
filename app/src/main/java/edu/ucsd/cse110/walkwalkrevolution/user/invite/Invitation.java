package edu.ucsd.cse110.walkwalkrevolution.user.invite;

import android.util.Log;

import java.util.HashMap;
import java.util.Map;

import edu.ucsd.cse110.walkwalkrevolution.WalkWalkRevolution;
import edu.ucsd.cse110.walkwalkrevolution.user.User;

public class Invitation {

    private final String TAG = "Invitation";

    private final String TO = "to";
    private final String FROM = "from";

    private User sender;
    private User receiver;
    private String senderTeamId;

    public Invitation(){
        this.sender = WalkWalkRevolution.getUser();
        this.senderTeamId = this.sender.getTeamId();
    }
    //assumption that only *this* user makes the invitation
    public Invitation(User receiver){
        super();
        this.receiver = receiver;
    }

    public Invitation(String receiverEmail){
        super();
        this.receiver = WalkWalkRevolution.getUserService().getUser(receiverEmail);
        if(this.receiver == null){
            Log.e(TAG, "Could not get receiver from email.");
        }
    }

    public Map<String, String> toMap(){
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

    public String getSenderTeamId(){
        return this.senderTeamId;
    }

}
