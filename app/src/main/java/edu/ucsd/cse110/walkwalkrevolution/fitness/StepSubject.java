package edu.ucsd.cse110.walkwalkrevolution.fitness;

import java.util.Observable;
import java.util.Timer;
import java.util.TimerTask;

public class StepSubject extends Observable {

    private Timer t;
    private TimerTask updateStep;
    FitnessService service;

    public StepSubject(FitnessService service) {
        updateStep = new TimerTask() {
            @Override
            public void run() {
                Steps steps = service.getUpdatedSteps();
                notifyObservers(steps);
            }
        };
        t = new Timer();
        t.schedule(updateStep, 0, 5000);
    }

}
