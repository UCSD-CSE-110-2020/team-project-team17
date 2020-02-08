package edu.ucsd.cse110.walkwalkrevolution.model;

public class Item {
    private String Title;

   // private boolean isExpandable;

    public Item(String text) {//boolean isExpandable) {
        this.Title = text;
        //this.isExpandable = isExpandable;
    }

    public String getTitle() {
        return Title;
    }

    public void setTitle(String title) {
        Title = title;
    }
/*
    public boolean isExpandable() {
        return isExpandable;
    }

    public void setExpandable(boolean expandable) {
        isExpandable = expandable;
    }
*/

}
