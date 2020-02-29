package edu.ucsd.cse110.walkwalkrevolution.DescriptionTags;

import java.util.ArrayList;

public class DescriptionTags {
    private ArrayList<String> tags;
    private String selectedTag;

    public DescriptionTags(String ...t) {
        tags = new ArrayList<String>();
        for (String tag : t) {
            tags.add(tag);
        }
        selectedTag = "";
    }

    public void selectTag(int idx) {
        if (tags.size() > 0 && idx < tags.size()) {
            selectedTag = tags.get(idx);
        }
    }

    public String getSelectedTag() {
        return selectedTag;
    }

    public String get(int idx) {
        return tags.get(idx);
    }

    public int getSize() { return tags.size(); }
}
