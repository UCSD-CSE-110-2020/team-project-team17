package edu.ucsd.cse110.walkwalkrevolution.user.persistence;

import edu.ucsd.cse110.walkwalkrevolution.user.User;

public interface BaseUserDao {
    void addUser(User user);
    User getUser(long id);
}
