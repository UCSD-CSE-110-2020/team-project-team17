package edu.ucsd.cse110.walkwalkrevolution.route.persistence;

import android.content.Context;
import android.content.SharedPreferences;

import java.util.Map;

import edu.ucsd.cse110.walkwalkrevolution.WalkWalkRevolution;
import edu.ucsd.cse110.walkwalkrevolution.route.Route;

import static android.content.Context.MODE_PRIVATE;

public class RouteSharedPreferenceDao implements BaseRouteDao {

    private static final String SP_ROUTE = "ROUTE";

    @Override
    public void addRoute(Route route) {
        Context context = WalkWalkRevolution.getContext();
        SharedPreferences sp = context.getSharedPreferences(SP_ROUTE, MODE_PRIVATE);
        SharedPreferences.Editor editor = sp.edit();

        //TODO: REPLACE VALUE WITH TITLE OF ROUTE -> USED AS NAME FOR SEPARATE SHARED PREF
        editor.putLong(Long.toString(route.getId()), route.getId());

        editor.apply();
    }

    @Override
    public Route getRoute(long routeId) {
        Context context = WalkWalkRevolution.getContext();
        SharedPreferences sp = context.getSharedPreferences(SP_ROUTE, MODE_PRIVATE);

        String identifier = sp.getString(Long.toString(routeId), "");

        return new Route(Long.parseLong(identifier));
    }

    @Override
    public Map<String, ?> getAllRoutes() {
        Context context = WalkWalkRevolution.getContext();
        SharedPreferences sp = context.getSharedPreferences(SP_ROUTE, MODE_PRIVATE);
        Map<String, ?> Routes = sp.getAll();
        return Routes;
    }
}
