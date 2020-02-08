package edu.ucsd.cse110.walkwalkrevolution;

import android.app.Application;
import android.content.Context;
import android.content.Intent;

import edu.ucsd.cse110.walkwalkrevolution.fitness.FitnessService;
import edu.ucsd.cse110.walkwalkrevolution.fitness.FitnessServiceFactory;
import edu.ucsd.cse110.walkwalkrevolution.fitness.GoogleFitAdapter;

public class WalkWalkRevolution extends Application {

    public static String fitnessServiceKey = "GOOGLE_FIT";

    public static Context context;

    @Override
    public void onCreate() {
        super.onCreate();
        setupGoogleFitnessApi();
        WalkWalkRevolution.context = getApplicationContext();
    }

    private void setupGoogleFitnessApi() {
        FitnessServiceFactory.put(fitnessServiceKey, new FitnessServiceFactory.BluePrint() {
            @Override
            public FitnessService create(HomeActivity homeActivity) {
                return new GoogleFitAdapter(homeActivity);
            }
        });
    }

    public void setFitnessServiceKey(String fitnessServiceKey) {
        this.fitnessServiceKey = fitnessServiceKey;
    }

    public static Context getContext() {
        return context;
    }

}
