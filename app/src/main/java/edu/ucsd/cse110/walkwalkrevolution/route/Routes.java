package edu.ucsd.cse110.walkwalkrevolution.route;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import edu.ucsd.cse110.walkwalkrevolution.WalkWalkRevolution;
import edu.ucsd.cse110.walkwalkrevolution.activity.Activity;
import edu.ucsd.cse110.walkwalkrevolution.activity.ActivityUtils;
import edu.ucsd.cse110.walkwalkrevolution.activity.Walk;
import edu.ucsd.cse110.walkwalkrevolution.team.Team;
import edu.ucsd.cse110.walkwalkrevolution.team.TeamObserver;
import edu.ucsd.cse110.walkwalkrevolution.user.User;

public class Routes implements RoutesSubject, TeamObserver {

    List<Route> routes;
    List<RoutesObserver> observers;
    Map<String, Route> overrideTeamRoutes;
    Team team;

    public Routes() {
        routes = new ArrayList<>();
        observers = new ArrayList<>();
        overrideTeamRoutes = new HashMap<>();
    }

    public void subscribe(RoutesObserver obs) {
        observers.add(obs);
    }

    public void unsubscribe(RoutesObserver obs) {
        observers.remove(obs);
    }

    public void notifyObservers() {
        for(RoutesObserver obs: observers){
            obs.update(routes);
        }
    }

    public Route get(int idx){
        return routes.get(idx);
    }

    public void add(Route route){
        this.routes.add(route);
    }

    public int getSize(){
        return routes.size();
    }

    // Returns a list of "actual" activities in order of datetime
    public List<Activity> getActivities() {
        getRoutesFromDao();
        updateOverridenRoutes();
        List<Activity> activities = new ArrayList<>();
        for(Route route: routes){
            if(route.getActivity().isExist()){
                activities.add(route.getActivity());
            }
        }
        for(Route route: overrideTeamRoutes.values()){
            if(route.getActivity().isExist()){
                activities.add(route.getActivity());
            }
        }
        Collections.sort(activities, (a,b) -> {
             return ActivityUtils.stringToTime(a.getDetail(Activity.DATE))
                     .compareTo(ActivityUtils.stringToTime(b.getDetail(Activity.DATE)));
        });
        return activities;
    }

    private void getRoutesFromDao(){
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

    public void updateOverridenRoutes() {
        overrideTeamRoutes = new HashMap<>();
        Map<String, ?> override = WalkWalkRevolution.getRouteDao().getTeamRoutes();
        for(Map.Entry<String, ?> entry: override.entrySet()) {
            try {
                overrideTeamRoutes.put(entry.getKey(), RouteUtils.deserialize(entry.getValue().toString()));
            } catch (Exception e) {
                throw new RuntimeException(e.getMessage());
            }
        }
    }

    public Map<String, Route> getOverridenRoutes(){
        return new HashMap<>(overrideTeamRoutes);
    }

    public void getLocal(){
        routes = new ArrayList<>();
        getRoutesFromDao();
        notifyObservers();
    }

    public void getTeamRoutes(){
        routes = new ArrayList<>();
        updateOverridenRoutes();
        team = new Team();
        team.subscribe(this);
    }

    @Override
    public void update(List<User> users){
        if(users.size() == 1){
            notifyObservers();
        } else {
            for (User u : users) {
                if(!u.getEmail().equals(WalkWalkRevolution.getUser().getEmail()))
                    WalkWalkRevolution.getRouteService().getRoutes(this, u);
            }
        }
    }

}
