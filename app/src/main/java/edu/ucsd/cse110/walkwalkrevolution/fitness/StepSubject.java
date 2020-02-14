package edu.ucsd.cse110.walkwalkrevolution.fitness;

import java.util.Observable;
import java.util.Timer;
import java.util.TimerTask;

public class StepSubject extends Observable {

    private Timer t;
    private TimerTask updateStep;
    FitnessService service;

    public StepSubject(FitnessService service) {
        this.service = service;
        updateStep = new TimerTask() {
            @Override
            public void run() {
                service.getUpdatedSteps();
                setChanged();
                notifyObservers();
            }
        };
        t = new Timer();
        t.schedule(updateStep, 0, 2000);
    }

}
