package edu.ucsd.cse110.walkwalkrevolution;

import android.app.Application;
import android.content.Context;

import edu.ucsd.cse110.walkwalkrevolution.fitness.FitnessService;
import edu.ucsd.cse110.walkwalkrevolution.fitness.FitnessServiceFactory;
import edu.ucsd.cse110.walkwalkrevolution.fitness.GoogleFitAdapter;
import edu.ucsd.cse110.walkwalkrevolution.route.persistence.BaseRouteDao;
import edu.ucsd.cse110.walkwalkrevolution.route.persistence.RouteSharedPreferenceDao;

public class WalkWalkRevolution extends Application {

    public static String fitnessServiceKey = "GOOGLE_FIT";

    private static Context context;

    private static BaseRouteDao routeDao;

    @Override
    public void onCreate() {
        super.onCreate();
        setupGoogleFitnessApi();

        WalkWalkRevolution.context = getApplicationContext();
        routeDao = new RouteSharedPreferenceDao();
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

    public static BaseRouteDao getRouteDao() {
        return routeDao;
    }

    public static void setRouteDao(BaseRouteDao rD) {
        routeDao = rD;
    }

}
