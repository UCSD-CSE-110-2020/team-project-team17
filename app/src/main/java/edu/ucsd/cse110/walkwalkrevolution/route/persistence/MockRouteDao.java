package edu.ucsd.cse110.walkwalkrevolution.route.persistence;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import edu.ucsd.cse110.walkwalkrevolution.route.Route;
import edu.ucsd.cse110.walkwalkrevolution.route.RouteUtils;

public class MockRouteDao implements BaseRouteDao {

    Map<Long, String> persisted;
    Map<String, String> team;
    Set<String> favorite;
    long nextId;

    public MockRouteDao(){
        this.persisted = new HashMap<>();
        team = new HashMap<>();
        favorite = new HashSet<>();
        this.nextId = 1;
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
    public void addTeamRoute(Route route) {
        try {
            team.put(route.getFirestoreId(), RouteUtils.serialize(route));
        } catch (Exception e) {
            throw new RuntimeException(e.getMessage());
        }
    }

    @Override
    public void addFavorite(Route route) {
        favorite.add(route.getFirestoreId());
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
    public boolean isFavorite(Route route) {
        return favorite.contains(route.getFirestoreId());
    }

    @Override
    public void removeFavorite(Route route) {
        favorite.remove(route.getFirestoreId());
    }

    @Override
    public Map<String, ?> getAllRoutes() {
        Map<String, String> map = new HashMap<>();
        for(Map.Entry<Long, String> e: persisted.entrySet()) {
            map.put(Long.toString(e.getKey()), e.getValue());
        }
        return map;
    }

    @Override
    public Map<String, ?> getTeamRoutes() {
        return new HashMap<>(team);
    }

    @Override
    public boolean walkedTeamRoute(Route route) {
        return team.containsKey(route.getFirestoreId());
    }

    @Override
    public long getNextId(){
        return nextId++;
    }

}
