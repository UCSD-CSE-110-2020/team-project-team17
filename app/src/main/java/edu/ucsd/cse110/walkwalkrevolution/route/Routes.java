package edu.ucsd.cse110.walkwalkrevolution.route;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;

import edu.ucsd.cse110.walkwalkrevolution.WalkWalkRevolution;
import edu.ucsd.cse110.walkwalkrevolution.activity.Activity;
import edu.ucsd.cse110.walkwalkrevolution.activity.ActivityUtils;
import edu.ucsd.cse110.walkwalkrevolution.activity.Walk;

public class Routes {

    List<Route> routes;

    public Routes() {
        routes = new ArrayList<>();
        Map<String, ?> allRoutes = WalkWalkRevolution.getRouteDao().getAllRoutes();
        for(Map.Entry<String, ?> entry: allRoutes.entrySet()) {
            try {
                routes.add(RouteUtils.deserialize(entry.getValue().toString()));
            } catch (Exception e) {
                throw new RuntimeException(e.getMessage());
            }
        }

        Collections.sort(routes, (a,b) -> {
            return a.getTitle().compareTo(b.getTitle());
        });
    }

    public Route get(int idx){
        return routes.get(idx);
    }

    public int getSize(){
        return routes.size();
    }

    // Returns a list of "actual" activities in order of datetime
    public List<Activity> getActivities() {
        List<Activity> activities = new ArrayList<>();
        for(Route route: routes){
            if(Long.parseLong(route.getActivity().getDetail(Walk.STEP_COUNT)) > 0){
                activities.add(route.getActivity());
            }
        }
        Collections.sort(activities, (a,b) -> {
             return ActivityUtils.stringToTime(a.getDetail(Activity.DATE))
                     .compareTo(ActivityUtils.stringToTime(b.getDetail(Activity.DATE)));
        });
        return activities;
    }

}
