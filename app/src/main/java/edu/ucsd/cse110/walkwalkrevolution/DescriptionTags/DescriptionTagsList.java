package edu.ucsd.cse110.walkwalkrevolution.DescriptionTags;

import java.util.ArrayList;

// Creates list of tag groups
public class DescriptionTagsList {
    ArrayList<DescriptionTags> tagList;

    private void createAvailableTags() {
        DescriptionTags path = new DescriptionTags("loop", "out-and-back");
        DescriptionTags slope = new DescriptionTags("flat", "hilly");
        DescriptionTags terrain = new DescriptionTags("streets", "trail");
        DescriptionTags level = new DescriptionTags("even surface", "uneven surface");
        DescriptionTags difficulty = new DescriptionTags("easy", "moderate", "difficult");

        tagList.add(path);
        tagList.add(slope);
        tagList.add(terrain);
        tagList.add(level);
        tagList.add(difficulty);

    }

    public DescriptionTagsList() {
        tagList = new ArrayList<DescriptionTags>();
        createAvailableTags();
    }

    public ArrayList<DescriptionTags> getTagList() {
        return tagList;
    }

    public DescriptionTags get(int idx) {
        return tagList.get(idx);
    }

    public int getSize() { return tagList.size(); }
}
