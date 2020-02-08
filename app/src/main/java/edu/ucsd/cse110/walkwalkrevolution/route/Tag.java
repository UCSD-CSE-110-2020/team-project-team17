package edu.ucsd.cse110.walkwalkrevolution.route;

// Constant values for each tag, use for setting tags in a route.
public class Tag {
    // Route types: (1) loop, (2) out-and-back
    public static final int LOOP = 1;
    public static final int OUT_BACK = 2;

    // Route hill types: (1) hilly, (2) flat
    public static final int HILL = 1;
    public static final int FLAT = 2;

    // Route area types: (1) street, (2) trail
    public static final int STREET = 1;
    public static final int TRAIL = 2;

    // Route surface types (1) even, (2) uneven
    public static final int EVEN = 1;
    public static final int UNEVEN = 2;

    // Route difficulty (1) easy, (2) moderate, (3) difficult
    public static final int EASY = 1;
    public static final int MODERATE = 2;
    public static final int DIFFICULT = 3;
}
