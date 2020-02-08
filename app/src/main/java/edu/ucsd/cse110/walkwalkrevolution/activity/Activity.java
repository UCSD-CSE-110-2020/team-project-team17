package edu.ucsd.cse110.walkwalkrevolution.activity;

import com.fasterxml.jackson.annotation.JsonAnyGetter;
import com.fasterxml.jackson.annotation.JsonAnySetter;

import java.util.HashMap;
import java.util.Map;

public class Activity {

    private Map<String, String> details;

    public Activity() {
        this.details = new HashMap<>();
    }

    public Activity(Map<String, String> details){
        setDetails(details);
    }

    @JsonAnyGetter
    public Map<String, String> getDetails() {
        return new HashMap<>(details);
    }

    public void setDetails(Map<String, String> details){
        this.details = new HashMap<>(details);
    }

    public String getDetail(String key){
        return details.get(key);
    }

    @JsonAnySetter
    public void setDetail(String key, String value){
        details.put(key, value);
    }

}
