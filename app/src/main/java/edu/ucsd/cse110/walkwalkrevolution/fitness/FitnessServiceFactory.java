package edu.ucsd.cse110.walkwalkrevolution.fitness;


import edu.ucsd.cse110.walkwalkrevolution.DummyActivity;

public class FitnessServiceFactory {

    public FitnessService createFitnessService(DummyActivity activity) {
        return new GoogleFitAdapter(activity);
    }

}