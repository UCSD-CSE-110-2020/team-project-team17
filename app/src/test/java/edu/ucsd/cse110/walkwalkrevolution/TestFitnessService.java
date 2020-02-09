package edu.ucsd.cse110.walkwalkrevolution;

import edu.ucsd.cse110.walkwalkrevolution.fitness.FitnessService;

public class TestFitnessService implements FitnessService {
    private static final String TAG = "[TestFitnessService]: ";
    private HomeActivity homeActivity;
    private int nextStepCount;

    public TestFitnessService(HomeActivity homeActivity) {
        this.homeActivity = homeActivity;
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
        homeActivity.setStepCount(nextStepCount);
    }

    public void updateStepCount(int nextStepCount){
        updateStepCount(nextStepCount);
    }
}