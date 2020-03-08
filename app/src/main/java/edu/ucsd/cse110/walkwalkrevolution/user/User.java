package edu.ucsd.cse110.walkwalkrevolution.user;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;

import java.util.HashMap;
import java.util.Map;

import edu.ucsd.cse110.walkwalkrevolution.WalkWalkRevolution;

@JsonPropertyOrder({"id","height"})
public class User {

    public static final String NAME = "name";
    public static final String EMAIL = "email";
    public static final String TEAM = "team";

    private long id;
    private long height;

    @JsonIgnore
    private String name;
    @JsonIgnore
    private String email;
    @JsonIgnore
    private String teamId;

    public User(@JsonProperty("id") long id, @JsonProperty("height") long height) {
        this.id = id;
        this.height = height;
    }

    public User(long id, long ft, long in){
        this(id, ft*12+in);
    }

    public User(long id, long height, String name, String email){
        this(1, height);
        this.name = name;
        this.email = email;
    }

    public User(){}

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public long getHeight() {
        return height;
    }

    public void setHeight(long height) {
        this.height = height;
    }

    public void setName(String name){
        this.name = name;
    }

    public String getName(){
        return name;
    }

    public void setEmail(String email){
        this.email = email;
    }

    public String getEmail(){
        return email;
    }

    public void setTeamId(String teamId) {
        this.teamId = teamId;
    }

    public String getTeamId() {
        return teamId;
    }

    public Map<String, String> toMap(){
        return new HashMap<String, String>(){{
            put(NAME, name);
            put(EMAIL, email);
            if (teamId != null) put(TEAM, teamId);
        }};
    }

}
