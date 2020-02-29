package edu.ucsd.cse110.walkwalkrevolution.fitness;

import edu.ucsd.cse110.walkwalkrevolution.WalkWalkRevolution;

public class MockFitnessService implements FitnessService {

    private static final String TAG = "[MockFitnessService]: ";
    private long nextStepCount;

    public MockFitnessService() {
        this.nextStepCount = 0;
    }

    @Override
    public int getRequestCode() {
        return 0;
    }

    @Override
    public void setup() {
        System.out.println(TAG + "setup");
        WalkWalkRevolution.setHasPermissions();
    }

    @Override
    public void updateStepCount() {
        System.out.println(TAG + "updateStepCount");
        WalkWalkRevolution.getSteps().updateStats(nextStepCount);
    }

    public void setNextStepCount(long nextStepCount){
        this.nextStepCount = nextStepCount;
    }

}
