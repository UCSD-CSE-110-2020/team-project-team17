package edu.ucsd.cse110.walkwalkrevolution;

import org.junit.Test;

import edu.ucsd.cse110.walkwalkrevolution.route.Route;
import edu.ucsd.cse110.walkwalkrevolution.route.Tag;

import static org.junit.Assert.*;

// Test basic functionality of the Route class.

public class RouteTest {

    @Test
    public void createRouteIDTest(){
        Route route1 = new Route(1);
        assertEquals(route1.getId(), 1);
    }

    @Test
    public void setTitleLocationNotesTest(){
        Route route = new Route(1);
        route.setTitle("Test Title");
        route.setLocation("My house");

        String notes = "Some notes about the route.";
        route.setNotes(notes);

        assertEquals(route.getTitle(), "Test Title");
        assertEquals(route.getLocation(), "My house");
        assertEquals(route.getNotes(), notes);
    }

    @Test
    public void setTagsTest(){
        Route route = new Route(1);
        assertTrue(route.setTypeTag(Tag.OUT_BACK));
        assertTrue(route.setAreaTag(Tag.TRAIL));
        assertTrue(route.setHillTag(Tag.HILL));
        assertTrue(route.setSurfaceTag(Tag.UNEVEN));
        assertTrue(route.setDiffTag(Tag.DIFFICULT));

        assertEquals(route.getTypeTag(), Tag.OUT_BACK);
        assertEquals(route.getAreaTag(), Tag.TRAIL);
        assertEquals(route.getHillTag(), Tag.HILL);
        assertEquals(route.getSurfaceTag(), Tag.UNEVEN);
        assertEquals(route.getDiffTag(), Tag.DIFFICULT);
    }

    @Test
    public void setInvalidTagsTest(){
        Route route = new Route(1);
        assertFalse(route.setTypeTag(Tag.UNEVEN));
        assertEquals(route.getTypeTag(), 0);
    }

}
