package edu.ucsd.cse110.walkwalkrevolution.team;

import java.util.ArrayList;

import edu.ucsd.cse110.walkwalkrevolution.user.User;

public class Team {
    ArrayList<User> users;

    public Team(ArrayList<User> users) { // TODO basically all team is right now is a User ArrayList wrapper.
        this.users = users;
    }

    public ArrayList<User> getUsers(){
        return users;
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
