package edu.ucsd.cse110.walkwalkrevolution.route;

public class Route {

    private final long id;
    private String title;
    private String location;
    private int typeTag;
    private int hillTag;
    private int areaTag;
    private int surfaceTag;
    private int diffTag;
    private String notes;
    //TODO: Set route's last walk information (time, steps, miles)

    //TODO: Generation of (unique) route id's
    public Route(long id){
        this.id = id;
    }
    /* Getters Below */
    public long getId(){
        return this.id;
    }

    public String getTitle(){
        return this.title;
    }

    public String getLocation(){
        return this.location;
    }

    public int getTypeTag(){
        return this.typeTag;
    }

    public int getHillTag(){
        return this.hillTag;
    }

    public int getAreaTag(){
        return this.areaTag;
    }

    public int getSurfaceTag(){
        return this.surfaceTag;
    }

    public int getDiffTag(){
        return this.diffTag;
    }

    public String getNotes(){
        return this.notes;
    }

    /* Setters Below */
    public void setTitle(String title){
        this.title = title;
    }

    public void setLocation(String location){
        this.location = location;
    }

    public boolean setTypeTag(int type){
        if(type < Tag.LOOP || type > Tag.OUT_BACK){
            return false;
        }
        this.typeTag = type;
        return true;
    }

    public boolean setHillTag(int hill){
        if(hill < Tag.HILL || hill > Tag.FLAT){
            return false;
        }
        this.hillTag = hill;
        return true;
    }

    public boolean setAreaTag(int area){
        if(area < Tag.STREET || area > Tag.TRAIL){
            return false;
        }
        this.areaTag = area;
        return true;
    }

    public boolean setSurfaceTag(int surface){
        if(surface < Tag.EVEN || surface > Tag.UNEVEN){
            return false;
        }
        this.surfaceTag = surface;
        return true;
    }

    public boolean setDiffTag(int diff){
        if(diff < Tag.EASY || diff > Tag.DIFFICULT){
            return false;
        }
        this.diffTag = diff;
        return true;
    }

    public void setNotes(String notes){
        this.notes = notes;
    }

}
