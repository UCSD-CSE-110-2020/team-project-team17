package edu.ucsd.cse110.walkwalkrevolution.route;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import edu.ucsd.cse110.walkwalkrevolution.WalkWalkRevolution;
import edu.ucsd.cse110.walkwalkrevolution.activity.Activity;
import edu.ucsd.cse110.walkwalkrevolution.activity.Walk;


@JsonPropertyOrder({"id", "title", "location", "notes", "activity"})
public class Route {

    public static final String USER_ID = "userId";
    public static final String TITLE = "title";
    public static final String LOCATION = "location";
    public static final String NOTES = "notes";
    public static final String DESCRIPTION_TAGS = "descriptionTags";
    public static final String DEFAULT_ID = "not stored in cloud";
    public static final String ACCEPT = "accept";
    public static final String DECLINE_BAD_TIME = "declineBadTime";
    public static final String DECLINE_BAD_ROUTE = "declineBadRoute";

    private String routeId;
    private long id;
    @JsonIgnore
    private String userId;
    private String title;
    private String location;
    private String descriptionTags;
    private String firestoreId;
    private Activity activity;
    private String notes;
    private List<String> accept;
    private List<String> declineBadTime;
    private List<String> declineBadRoute;


    public static class Builder {
        String title;
        String location;
        String notes;
        String description;
        String userId;
        String firestoreId;
        Activity activity;
        List<String> accept = new ArrayList();
        List<String> declineBadTime = new ArrayList();
        List<String> declineBadRoute = new ArrayList();

        public Builder setTitle(String title){
            this.title = title;
            return this;
        }

        public Builder setLocation(String location){
            this.location = location;
            return this;
        }

        public Builder setNotes(String notes){
            this.notes = notes;
            return this;
        }

        public Builder setDescription(String description){
            this.description = description;
            return this;
        }

        public Builder setUserId(String userId){
            this.userId = userId;
            return this;
        }


        public Builder setActivity(Activity activity){
            this.activity = activity;
            return this;
        }


        public Builder setFirestoreId(String fid){
            this.firestoreId = fid;
            return this;
        }

        public Builder setAccept(List<String> accept) {
            this.accept = accept;
            return this;
        }

        public Builder setDeclineBadTime(List<String> declineBadTime) {
            this.declineBadTime = declineBadTime;
            return this;
        }

        public Builder setDeclineBadRoute(List<String> declineBadRoute) {
            this.declineBadRoute = declineBadRoute;
            return this;
        }


        public Route build(){
            Route route = new Route(title, activity != null ? activity : new Walk());
            if(location != null) route.setLocation(location);
            if(notes != null) route.setNotes(notes);
            if(description != null) route.setDescriptionTags(description);
            if(userId != null) route.setUserId(userId);
            if(firestoreId != null) route.setFirestoreId(firestoreId);
            if(accept != null) route.setAccept(accept);
            if(declineBadTime != null) route.setDeclineBadTime(declineBadTime);
            if(declineBadRoute != null) route.setDeclineBadRoute(declineBadRoute);
            return route;
        }
    }

    public Route(@JsonProperty("id") long id, @JsonProperty("firestoreId") String firestoreId,
                 @JsonProperty("title") String title,
                 @JsonProperty("location") String location,
                 @JsonProperty("descriptionTags") String descriptionTags,
                 @JsonProperty("notes") String notes,
                 @JsonProperty("activity") Activity activity){
        this.routeId = DEFAULT_ID;
        this.id = id;
        this.firestoreId = firestoreId;
        this.title = title;
        this.location = location;
        this.descriptionTags = descriptionTags;
        this.notes = notes;
        this.activity = activity;
    }

    public Route(Map<String, Object> map) {
        this.title = (String) map.get(TITLE);
        this.descriptionTags = (String) map.get(DESCRIPTION_TAGS);
        this.location = (String) map.get(LOCATION);
        this.notes = (String) map.get(NOTES);
        this.activity = null;
        if (map.get(ACCEPT) != null) {
            this.accept = new ArrayList<String>(Arrays.asList(((String)(map.get(ACCEPT))).split(",")));
        }
        if (map.get(DECLINE_BAD_TIME) != null) {
            this.declineBadTime = new ArrayList<String>(Arrays.asList(((String)(map.get(DECLINE_BAD_TIME))).split(",")));
        }
        if (map.get(DECLINE_BAD_ROUTE) != null) {
            this.declineBadRoute = new ArrayList<String>(Arrays.asList(((String)(map.get(DECLINE_BAD_ROUTE))).split(",")));
        }
    }

    //Used for testing
    public Route(long id, String title, Activity activity){
        this.routeId = DEFAULT_ID;
        this.id = id;
        this.title = title;
        this.activity = activity;
    }

    public Route(String title, Activity activity){
        this.routeId = DEFAULT_ID;
        this.id = WalkWalkRevolution.getRouteDao().getNextId();
        this.title = title;
        this.activity = activity;
    }

    public String getRouteId() { return routeId; }

    public void setRouteId(String routeId) {
        this.routeId =  routeId;
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

    public void setUserId(String userId){
        this.userId = userId;
    }

    public String getUserId(){
        return userId;
    }

    public String getDescriptionTags() {
        return descriptionTags == null ? "": descriptionTags;
    }

    public void setDescriptionTags(String descriptionTags) {
        this.descriptionTags = descriptionTags;
    }

    public String getNotes(){
        return notes == null ? "": notes;
    }

    public void setNotes(String notes) {
        this.notes = notes;
    }


    public String getFirestoreId(){
        return this.firestoreId;
    }

    public void setFirestoreId(String fid){
        this.firestoreId = fid;
    }

    public List<String> getAccept() { return accept; }

    public void setAccept(List<String> accept) { this.accept = accept; }

    public List<String> getDeclineBadTime() { return declineBadTime; }

    public void setDeclineBadTime(List<String> declineBadTime) { this.declineBadTime = declineBadTime; }

    public List<String> getDeclineBadRoute() { return declineBadRoute; }

    public void setDeclineBadRoute(List<String> declineBadRoute) { this.declineBadRoute = declineBadRoute; }


    public Map<String, String> toMap(){
        Map<String, String> map = new HashMap<>();
        if(title != null)
            map.put(TITLE, title);
        if(location != null)
            map.put(LOCATION, location);
        if(descriptionTags != null)
            map.put(DESCRIPTION_TAGS, descriptionTags);
        if(notes != null)
            map.put(NOTES, notes);
        if(userId != null)
            map.put(USER_ID, userId);
        return map;
    }

    public void setAvailability(String userId, Availability availability) {
        accept.remove(userId);
        declineBadTime.remove(userId);
        declineBadRoute.remove(userId);

        if (availability == Availability.ACCEPT) {
            accept.add(userId);
        } else if (availability == Availability.DECLINE_BAD_TIME) {
            declineBadTime.add(userId);
        } else if (availability == Availability.DECLINE_BAD_ROUTE) {
            declineBadRoute.add(userId);
        }
    }

    public enum Availability {
        ACCEPT,
        DECLINE_BAD_TIME,
        DECLINE_BAD_ROUTE;
    }

}