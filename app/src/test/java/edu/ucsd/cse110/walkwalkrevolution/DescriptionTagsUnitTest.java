package edu.ucsd.cse110.walkwalkrevolution;

import androidx.test.ext.junit.runners.AndroidJUnit4;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.robolectric.annotation.Config;

import edu.ucsd.cse110.walkwalkrevolution.DescriptionTags.DescriptionTags;

import static com.google.common.truth.Truth.assertThat;

@RunWith(AndroidJUnit4.class)
@Config(sdk=28)
public class DescriptionTagsUnitTest {

    @Before
    public void setUp() {

    }

    @Test
    public void testGetSize() {
        DescriptionTags descTags = new DescriptionTags("cookie", "crackers", "chips");
        assertThat(String.valueOf(descTags.getSize())).isEqualTo("3");
    }


    @Test
    public void testGetSelectedTagWhenNotSelected() {
        DescriptionTags descTags = new DescriptionTags("cookie", "crackers", "chips");
        assertThat(descTags.getSelectedTag()).isEqualTo("");
    }
}
