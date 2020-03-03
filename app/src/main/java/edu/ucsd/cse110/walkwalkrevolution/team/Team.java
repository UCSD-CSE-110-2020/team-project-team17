package edu.ucsd.cse110.walkwalkrevolution.team;

import java.util.ArrayList;
import java.util.List;

import edu.ucsd.cse110.walkwalkrevolution.WalkWalkRevolution;
import edu.ucsd.cse110.walkwalkrevolution.user.User;

public class Team implements TeamSubject{

    List<User> users;
    List<TeamObserver> observers;

    public Team(){
        observers = new ArrayList<>();
        users = new ArrayList<>();
        WalkWalkRevolution.getUserService().getTeam(this, WalkWalkRevolution.getUser());
    }

    public Team(List<User> users) { // TODO basically all team is right now is a User ArrayList wrapper.
        this.users = users;
    }

    public List<User> getUsers(){
        return users;
    }

    public void addUser(User u) {
        this.users.add(u);
    }

    public void subscribe(TeamObserver obs) {
        observers.add(obs);
    }

    public void unsubscribe(TeamObserver obs) {
        observers.remove(obs);
    }

    public void notifyObservers() {
        for(TeamObserver obs: observers){
            if(obs == null){
                unsubscribe(obs);
            } else {
                obs.update(users);
            }
        }
    }

    // TODO probably delete this, it's just here to test our RecyclerView.
    public static ArrayList<User> generateTestEntries(int num){
        ArrayList<User> users = new ArrayList<>();

        for(int i = 0; i < num; i++){
            User newUser = new User(i, 1, 1);
            newUser.setName(String.valueOf(i));
            newUser.setEmail(i + "@email.com");
            users.add(newUser);
        }

        return  users;
    }
}
