package edu.ucsd.cse110.walkwalkrevolution;

import android.app.Application;
import android.content.Context;

import edu.ucsd.cse110.walkwalkrevolution.fitness.FitnessService;
import edu.ucsd.cse110.walkwalkrevolution.fitness.FitnessServiceFactory;
import edu.ucsd.cse110.walkwalkrevolution.fitness.GoogleFitAdapter;
import edu.ucsd.cse110.walkwalkrevolution.fitness.Steps;
import edu.ucsd.cse110.walkwalkrevolution.route.persistence.BaseRouteDao;
import edu.ucsd.cse110.walkwalkrevolution.route.persistence.RouteSharedPreferenceDao;
import edu.ucsd.cse110.walkwalkrevolution.user.User;
import edu.ucsd.cse110.walkwalkrevolution.user.persistence.BaseUserDao;
import edu.ucsd.cse110.walkwalkrevolution.user.persistence.UserSharedPreferenceDao;

public class WalkWalkRevolution extends Application {

    public static String fitnessServiceKey = "GOOGLE_FIT";

    private static FitnessService fitnessService;

    private static Context context;

    private static BaseRouteDao routeDao;

    private static BaseUserDao userDao;

    private static Steps steps = new Steps();

    private static User user;

    private static boolean hasPermissions = false;

    @Override
    public void onCreate() {
        super.onCreate();
        setupGoogleFitnessApi();
        WalkWalkRevolution.context = getApplicationContext();
        routeDao = new RouteSharedPreferenceDao();
        userDao = new UserSharedPreferenceDao();
        user = userDao.getUser(UserSharedPreferenceDao.USER_ID);
    }

    private void setupGoogleFitnessApi() {
        FitnessServiceFactory.put(fitnessServiceKey, new FitnessServiceFactory.BluePrint() {
            @Override
            public FitnessService create(DummyActivity dummyActivity) {
                return new GoogleFitAdapter(dummyActivity);
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

    public static BaseUserDao getUserDao() {
        return userDao;
    }

    public static void setUserDao(BaseUserDao uD) {
        userDao = uD;
    }

    public static Steps getSteps(){
        return steps;
    }

    public static User getUser() {
        return user;
    }

    public static void setUser(User user) {
        WalkWalkRevolution.user = user;
    }

    public static void setFitnessService(FitnessService fitnessService){
        WalkWalkRevolution.fitnessService = fitnessService;
    }

    public static FitnessService getFitnessService(){
        return fitnessService;
    }

    public static void setHasPermissions(){
        hasPermissions = true;
    }

    public static boolean getHasPermissions(){
        return hasPermissions;
    }

}
