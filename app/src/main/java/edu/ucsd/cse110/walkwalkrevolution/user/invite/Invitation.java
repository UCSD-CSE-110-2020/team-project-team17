package edu.ucsd.cse110.walkwalkrevolution.user.invite;

import android.util.Log;

import java.util.HashMap;
import java.util.Map;

import edu.ucsd.cse110.walkwalkrevolution.WalkWalkRevolution;
import edu.ucsd.cse110.walkwalkrevolution.activity.Walk;
import edu.ucsd.cse110.walkwalkrevolution.user.User;

public class Invitation {

    private final String TAG = "Invitation";

    public static final String TO = "to";
    public static final String FROM = "from";
    public static final String TEAM = "team";

    private User sender;
    private User receiver;
    private String senderEmail;
    private String receiverEmail;
    private String teamId;


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

    public Invitation(Map<String, Object> map){
        this.senderEmail = (String) map.get(FROM);
        this.receiverEmail = (String) map.get(TO);
        this.teamId = (String) map.get(TEAM);
    }

    public Map<String, String> toMap(){
        Log.d(TAG, WalkWalkRevolution.getUser().getEmail());
        Map<String, String> map = new HashMap<>();
        map.put(FROM, this.sender.getName());
        map.put(TO, this.receiver.getEmail());
        return map;
    }

    //public User getSender(){
    //    return this.sender;
    //}

    public User getReceiver(){
        return this.receiver;
    }

    public String getSender() { return this.senderEmail; }

    public void setReceiver(User user){
        this.receiver = user;
    }

    public void setSender(String senderEmail) { this.senderEmail = senderEmail; }

    public void setTeam(String teamId){this.teamId = teamId; }

    public void acceptInvite(){
        String myEmail = WalkWalkRevolution.getUser().getEmail();
        //Verify that this user is the receiver? [Skipped for now TODO?]
        if(this.receiver.getEmail() != WalkWalkRevolution.getUser().getEmail()){
            Log.e(TAG, "Failed to verify this user as the receiver; changing their team anyway.");
        }

        //TODO: Re-query the sender to get their newest teamId

        //Change receiver's (this user's) team to the team of the sender.
        this.receiver.setTeamId(this.sender.getTeamId());
    }

}
