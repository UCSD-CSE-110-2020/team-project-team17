package edu.ucsd.cse110.walkwalkrevolution.route.persistence;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import edu.ucsd.cse110.walkwalkrevolution.route.Route;

public class MockRouteDao implements BaseRouteDao {

    Set<Long> persisted;

    public MockRouteDao(){
        this.persisted = new HashSet<>();
    }

    @Override
    public void addRoute(Route route) {
        persisted.add(route.getId());
    }

    @Override
    public Route getRoute(long routeId) {
        if(persisted.contains(routeId)){
            return new Route(routeId);
        }
        return null;
    }

    @Override
    public Map<String, ?> getAllRoutes() {
        Map<String, Long> map = new HashMap<>();
        for(Long l: persisted){
            map.put(Long.toString(l), l);
        }
        return map;
    }

}
