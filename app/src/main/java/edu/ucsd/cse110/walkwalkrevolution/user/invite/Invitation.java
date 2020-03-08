package edu.ucsd.cse110.walkwalkrevolution.user.invite;

import android.util.Log;
import android.widget.Toast;

import java.util.HashMap;
import java.util.Map;

import edu.ucsd.cse110.walkwalkrevolution.WalkWalkRevolution;
import edu.ucsd.cse110.walkwalkrevolution.activity.Walk;
import edu.ucsd.cse110.walkwalkrevolution.user.User;

public class Invitation {

    private final String TAG = "Invitation";

    public static final String TO = "to";
    public static final String FROM = "from";
    public static final String FROM_E = "from (email)";

    private String senderName;
    private String senderEmail;
    private String receiver;

    public Invitation(User sender, String receiverEmail){
        this.senderEmail = sender.getEmail();
        this.senderName = sender.getName();
        WalkWalkRevolution.getUserService().getReceiver(this, receiverEmail);
    }

    // empty constructor
    public Invitation(){ }

    public Invitation(Map<String, Object> map){
        this.senderName = (String) map.get(FROM);
        this.senderEmail = (String) map.get(FROM_E);
        this.receiver = (String) map.get(TO);
    }

    public Map<String, String> toMap(){
        Log.d(TAG, WalkWalkRevolution.getUser().getEmail());
        Map<String, String> map = new HashMap<>();
        map.put(FROM, this.senderName);
        map.put(FROM_E, this.senderEmail);
        map.put(TO, this.receiver);
        return map;
    }

    public String getReceiver(){
        return this.receiver;
    }
    public String getSenderName() { return this.senderName; }
    public String getSenderEmail() {return this.senderEmail; }

    public void setReceiver(String receiver){
        this.receiver = receiver;
    }
    public void setSenderName(String senderName) {this.senderName = senderName;}
    public void setSenderEmail(String senderEmail) {this.senderEmail = senderEmail;}

    public void acceptInvite(){
        String myEmail = WalkWalkRevolution.getUser().getEmail();
        if(!this.receiver.equals(myEmail)){
            Log.e(TAG, "Failed to verify this user as the receiver; changing their team anyway.");
        }

        WalkWalkRevolution.getUserService().getSender(this, senderEmail);
    }

}
