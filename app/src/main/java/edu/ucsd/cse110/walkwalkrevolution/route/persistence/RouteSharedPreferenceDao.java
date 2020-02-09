package edu.ucsd.cse110.walkwalkrevolution.route.persistence;

import android.content.Context;
import android.content.SharedPreferences;

import java.util.Map;

import edu.ucsd.cse110.walkwalkrevolution.WalkWalkRevolution;
import edu.ucsd.cse110.walkwalkrevolution.route.Route;
import edu.ucsd.cse110.walkwalkrevolution.route.RouteUtils;

import static android.content.Context.MODE_PRIVATE;

public class RouteSharedPreferenceDao implements BaseRouteDao {

    private static final String SP_ROUTE = "ROUTE";
    private static final String NEXT_ID_KEY = "NEXT_ID";

    @Override
    public void addRoute(Route route) {
        Context context = WalkWalkRevolution.getContext();
        SharedPreferences sp = context.getSharedPreferences(SP_ROUTE, MODE_PRIVATE);
        SharedPreferences.Editor editor = sp.edit();

        String jsonString;

        try {
            jsonString = RouteUtils.serialize(route);
        } catch (Exception e) {
            throw new RuntimeException("Invalid Route");
        }

        editor.putString(Long.toString(route.getId()), jsonString);

        editor.apply();
    }

    @Override
    public Route getRoute(long routeId) {
        Context context = WalkWalkRevolution.getContext();
        SharedPreferences sp = context.getSharedPreferences(SP_ROUTE, MODE_PRIVATE);

        String jsonString = sp.getString(Long.toString(routeId), "");

        if(jsonString == "") return null;

        try {
            return RouteUtils.deserialize(jsonString);
        } catch (Exception e) {
            throw new RuntimeException("Invalid Route JsonString");
        }
    }

    @Override
    public Map<String, ?> getAllRoutes() {
        Context context = WalkWalkRevolution.getContext();
        SharedPreferences sp = context.getSharedPreferences(SP_ROUTE, MODE_PRIVATE);
        Map<String, ?> Routes = sp.getAll();
        return Routes;
    }

    @Override
    public long getNextId(){
        Context context = WalkWalkRevolution.getContext();
        SharedPreferences sp = context.getSharedPreferences(SP_ROUTE, MODE_PRIVATE);

        long nextId = sp.getLong(NEXT_ID_KEY, 1);
        SharedPreferences.Editor editor = sp.edit();
        editor.putLong(NEXT_ID_KEY, nextId+1);

        editor.apply();

        return nextId;
    }
}
