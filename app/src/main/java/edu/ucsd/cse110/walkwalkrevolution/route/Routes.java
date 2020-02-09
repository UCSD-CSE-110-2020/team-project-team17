package edu.ucsd.cse110.walkwalkrevolution.route;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;

import edu.ucsd.cse110.walkwalkrevolution.WalkWalkRevolution;

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

}
