package edu.ucsd.cse110.walkwalkrevolution.team;

import java.util.List;

import edu.ucsd.cse110.walkwalkrevolution.user.User;

public interface TeamObserver {
    void update(List<User> users);
}
