package edu.ucsd.cse110.walkwalkrevolution.user;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;

@JsonPropertyOrder({"id","height"})
public class User {

    private long id;
    private long height;

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

}
