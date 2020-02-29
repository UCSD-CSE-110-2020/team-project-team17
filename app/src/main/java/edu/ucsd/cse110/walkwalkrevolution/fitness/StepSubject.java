package edu.ucsd.cse110.walkwalkrevolution.fitness;

import android.util.Log;

import java.util.ArrayList;
import java.util.Observable;
import java.util.Observer;
import java.util.Timer;
import java.util.TimerTask;

public class StepSubject extends Observable {
    private static final String TAG = "StepSubject";

    private Timer t;
    private TimerTask updateStep;
    FitnessService service;

    private ArrayList<Observer> observers;

    public StepSubject(FitnessService service) {
        observers = new ArrayList<>();
        Log.d(TAG, "StepSubject: " + "Creating additional StepSubject");

        this.service = service;
        updateStep = new TimerTask() {
            @Override
            public void run() {
                Log.d(TAG, "run: " + "Notify Observers");

                service.updateStepCount();

                setChanged();
                notifyObservers();
            }
        };
        t = new Timer();
        t.schedule(updateStep, 0, 2000);
    }

    @Override
    public void notifyObservers(){
        Log.d(TAG, "notifyObservers: " + observers.size());
        for(Observer obs : observers){
            Log.d(TAG, "notifyObservers: " + obs.getClass());
            obs.update(this, null);
        }
    }

    @Override
    public void addObserver(Observer newObserver){
        observers.add(newObserver);
    }

    @Override
    public void deleteObserver(Observer obs){
        observers.remove(obs);
    }

}
