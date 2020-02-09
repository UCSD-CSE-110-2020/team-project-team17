package edu.ucsd.cse110.walkwalkrevolution.route.persistence;

import java.util.HashMap;
import java.util.Map;
import edu.ucsd.cse110.walkwalkrevolution.route.Route;
import edu.ucsd.cse110.walkwalkrevolution.route.RouteUtils;

public class MockRouteDao implements BaseRouteDao {

    Map<Long, String> persisted;

    public MockRouteDao(){
        this.persisted = new HashMap<>();
    }

    @Override
    public void addRoute(Route route) {
        try {
            persisted.put(route.getId(), RouteUtils.serialize(route));
        } catch (Exception e) {
            throw new RuntimeException(e.getMessage());
        }
    }

    @Override
    public Route getRoute(long routeId) {
        if(persisted.containsKey(routeId)) {
            try {
                String jsonString = persisted.get(routeId);
                Route route = RouteUtils.deserialize(jsonString);
                return route;
            } catch (Exception e) {
                throw new RuntimeException(e.getMessage());
            }
        }
        return null;
    }

    @Override
    public Map<String, ?> getAllRoutes() {
        Map<String, String> map = new HashMap<>();
        for(Map.Entry<Long, String> e: persisted.entrySet()) {
            map.put(Long.toString(e.getKey()), e.getValue());
        }
        return map;
    }

}