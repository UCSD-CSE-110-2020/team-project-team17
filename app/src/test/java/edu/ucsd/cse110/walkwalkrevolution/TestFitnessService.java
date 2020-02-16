package edu.ucsd.cse110.walkwalkrevolution;

import edu.ucsd.cse110.walkwalkrevolution.fitness.FitnessService;

public class TestFitnessService implements FitnessService {
    private static final String TAG = "[TestFitnessService]: ";
    private DummyActivity dummyActivity;
    private int nextStepCount;

    public TestFitnessService(DummyActivity dummyActivity) {
        this.dummyActivity = dummyActivity;
    }

    @Override
    public int getRequestCode() {
        return 0;
    }

    @Override
    public void setup() {
        System.out.println(TAG + "setup");
    }

    @Override
    public void updateStepCount() {
        System.out.println(TAG + "updateStepCount");
        WalkWalkRevolution.getSteps().updateStats(nextStepCount);
    }
}