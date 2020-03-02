package edu.ucsd.cse110.walkwalkrevolution.user;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;

@JsonPropertyOrder({"id","height"})
public class User {

    private long id;
    private long height;
    private String name;
    private String email;

    public User(@JsonProperty("id") long id, @JsonProperty("height") long height, @JsonProperty("name") String name, @JsonProperty("email") String email) {
        this.id = id;
        this.height = height;
        this.name = name;
        this.email = email;
    }

    public User(long id, long ft, long in){
        this(id, ft*12+in, "", "");
    }

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

    public String getEmail(){
        return email;
    }

    public void setEmail(String email){
        this.email = email;
    }

}
