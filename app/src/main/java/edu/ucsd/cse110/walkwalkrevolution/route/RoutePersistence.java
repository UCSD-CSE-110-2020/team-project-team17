package edu.ucsd.cse110.walkwalkrevolution.route;

import android.content.Context;
import android.content.SharedPreferences;

import java.util.Map;

import edu.ucsd.cse110.walkwalkrevolution.WalkWalkRevolution;

import static android.content.Context.MODE_PRIVATE;

public class RoutePersistence {

    private static final String SP_ROUTE = "ROUTE";

    public static void addRoute(Route route){
        Context context = WalkWalkRevolution.getContext();
        SharedPreferences sp = context.getSharedPreferences(SP_ROUTE, MODE_PRIVATE);
        SharedPreferences.Editor editor = sp.edit();

        //TODO: REPLACE VALUE WITH TITLE OF ROUTE -> USED AS NAME FOR SEPERATE SHARED PREF
        editor.putString(Long.toString(route.id), Long.toString(route.id));

        editor.apply();
    }

    public static Route getRoute(long routeId){
        Context context = WalkWalkRevolution.getContext();
        SharedPreferences sp = context.getSharedPreferences(SP_ROUTE, MODE_PRIVATE);

        String identifier = sp.getString(Long.toString(routeId), "");

        return new Route(Long.parseLong(identifier));
    }

    public static Map<String, ?> getAllRoutes() {
        Context context = WalkWalkRevolution.getContext();
        SharedPreferences sp = context.getSharedPreferences(SP_ROUTE, MODE_PRIVATE);
        Map<String, ?> Routes = sp.getAll();
        return Routes;
    }

}
