package edu.ucsd.cse110.walkwalkrevolution.route;

import com.fasterxml.jackson.annotation.JsonPropertyOrder;

import edu.ucsd.cse110.walkwalkrevolution.activity.Activity;

@JsonPropertyOrder({"id", "wa"})
public class Route {

    private long id;
    private Activity activity;

    //Default constructor required by Jackson
    public Route(){
        this.id = -1;
        this.activity = null;
    }

    public Route(long id, Activity activity){
        this.id = id;
        this.activity = activity;
    }

    public long getId(){
        return id;
    }

    public Activity getActivity(){
        return activity;
    }

}