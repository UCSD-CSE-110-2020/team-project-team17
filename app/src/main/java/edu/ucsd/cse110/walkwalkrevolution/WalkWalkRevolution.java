package edu.ucsd.cse110.walkwalkrevolution;

import android.app.Application;
import android.content.Context;
import android.content.Intent;

import edu.ucsd.cse110.walkwalkrevolution.fitness.FitnessService;
import edu.ucsd.cse110.walkwalkrevolution.fitness.FitnessServiceFactory;
import edu.ucsd.cse110.walkwalkrevolution.fitness.GoogleFitAdapter;

public class WalkWalkRevolution extends Application {

    public static Context context;

    private String fitnessServiceKey = "GOOGLE_FIT";

    @Override
    public void onCreate() {
        super.onCreate();

        context = getApplicationContext();
        setupGoogleFitnessApi();

        Intent intent = new Intent(context, HomeActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        intent.putExtra(HomeActivity.FITNESS_SERVICE_KEY, fitnessServiceKey);
        //startActivity(intent);
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

}
