package edu.ucsd.cse110.walkwalkrevolution.invitation;

import java.util.HashMap;
import java.util.Map;

public class Invitation {

    public static final String TO = "to";
    public static final String FROM = "from";
    public static final String SENDER = "sender";

    private String firestoreId;
    private String to;
    private String from;
    private String sender;

    public Invitation(String to, String from, String sender){
        this.to = to;
        this.from = from;
        this.sender = sender;
    }

    public String getTo(){
        return to;
    }

    public String getFrom(){
        return from;
    }

    public String getSender(){
        return sender;
    }

    public String getFirestoreId(){
        return firestoreId;
    }

    public void setFirestoreId(String fid){
        this.firestoreId = fid;
    }

    public Map<String, String> toMap(){
        Map<String, String> map = new HashMap<String, String>(){{
            put(TO, to);
            put(FROM, from);
            put(SENDER, sender);
        }};
        return map;
    }

}
