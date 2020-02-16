package edu.ucsd.cse110.walkwalkrevolution.route;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;

import edu.ucsd.cse110.walkwalkrevolution.WalkWalkRevolution;
import edu.ucsd.cse110.walkwalkrevolution.activity.Activity;

@JsonPropertyOrder({"id", "title", "location", "activity"})
public class Route {

    private long id;
    private String title;
    private String location;
    private String descriptionTags;
    private Activity activity;

    public Route(@JsonProperty("id") long id, @JsonProperty("title") String title,
                 @JsonProperty("location") String location,
                 @JsonProperty("descriptionTags") String descriptionTags,
                 @JsonProperty("activity") Activity activity){
        this.id = id;
        this.title = title;
        this.location = location;
        this.descriptionTags = descriptionTags;
        this.activity = activity;
    }

    //Used for testing
    public Route(long id, String title, Activity activity){
        this.id = id;
        this.title = title;
        this.activity = activity;
    }

    public Route(String title, Activity activity){
        this.id = WalkWalkRevolution.getRouteDao().getNextId();
        this.title = title;
        this.activity = activity;
    }

    public long getId(){
        return id;
    }

    private void setId(){
        this.id = id;
    }

    public Activity getActivity(){
        return activity;
    }

    public void setActivity(Activity newAct) {
        this.activity = newAct;
    }

    public String getTitle(){
        return title;
    }

    public void setTitle(String title){
        this.title = title;
    }

    public String getLocation() {
        return location == null ? "": location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public String getDescriptionTags() {
        return descriptionTags == null ? "": descriptionTags;
    }

    public void setDescriptionTags(String descriptionTags) {
        this.descriptionTags = descriptionTags;
    }
}