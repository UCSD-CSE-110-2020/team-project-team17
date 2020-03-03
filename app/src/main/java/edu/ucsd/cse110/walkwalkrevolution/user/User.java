package edu.ucsd.cse110.walkwalkrevolution.user;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;

import java.util.HashMap;
import java.util.Map;

@JsonPropertyOrder({"id","height"})
public class User {

    public static final String NAME = "name";
    public static final String EMAIL = "email";

    private long id;
    private long height;

    @JsonIgnore
    private String name;
    @JsonIgnore
    private String email;

    public User(@JsonProperty("id") long id, @JsonProperty("height") long height) {
        this.id = id;
        this.height = height;
    }

    public User(long id, long ft, long in){
        this(id, ft*12+in);
    }

    public long getId() {
        return id;
    }

    public void setId() {
        this.id = id;
    }

    public long getHeight() {
        return height;
    }

    public void setHeight() {
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

    public Map<String, String> toMap(){
        return new HashMap<String, String>(){{
            put(NAME, name);
            put(EMAIL, email);
        }};
    }

}
