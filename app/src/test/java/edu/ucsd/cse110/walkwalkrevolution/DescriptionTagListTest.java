package edu.ucsd.cse110.walkwalkrevolution;


import androidx.test.ext.junit.runners.AndroidJUnit4;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.robolectric.annotation.Config;

import edu.ucsd.cse110.walkwalkrevolution.DescriptionTags.DescriptionTags;
import edu.ucsd.cse110.walkwalkrevolution.DescriptionTags.DescriptionTagsList;

import static com.google.common.truth.Truth.assertThat;
import static org.junit.Assert.assertEquals;

@RunWith(AndroidJUnit4.class)
@Config(sdk=28)
public class DescriptionTagListTest {
    @Test
    public void testGetSize() {
        DescriptionTagsList descTagsList = new DescriptionTagsList();
        assertEquals(descTagsList.getSize(), 5);
    }

    @Test
    public void testGet() {
        int idx = 2;
        DescriptionTagsList descTagsList = new DescriptionTagsList();
        assertEquals(descTagsList.get(idx).getSize(), 2);
        assertThat(descTagsList.get(idx).get(0)).isEqualTo("streets");

    }
}
