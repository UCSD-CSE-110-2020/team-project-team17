package edu.ucsd.cse110.walkwalkrevolution.route;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;

import edu.ucsd.cse110.walkwalkrevolution.activity.Activity;

@JsonPropertyOrder({"id", "title", "activity"})
public class Route {

    private long id;
    private String title;
    private Activity activity;

    public Route(@JsonProperty("id") long id, @JsonProperty("title") String title,
                 @JsonProperty("activity") Activity activity){
        this.id = id;
        this.title = title;
        this.activity = activity;
    }

    public long getId(){
        return id;
    }

    public Activity getActivity(){
        return activity;
    }

    public String getTitle(){
        return title;
    }

    public void setTitle(String title){
        this.title = title;
    }

}