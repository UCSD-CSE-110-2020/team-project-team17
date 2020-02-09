package edu.ucsd.cse110.walkwalkrevolution.activity;

import java.util.Map;

public class Walk extends Activity{

    public static final String STEP_COUNT = "STEP_COUNT";
    public static final String DURATION = "DURATION";
    public static final String MILES = "MILES";

    public Walk(){
        super();
        setDetail(STEP_COUNT, "0");
        setDetail(DURATION, "0");
        setDetail(MILES, "0");
    }

    public Walk(Map<String, String> walk){
        super(walk);
    }

}
