package edu.ucsd.cse110.walkwalkrevolution.user.persistence;

import edu.ucsd.cse110.walkwalkrevolution.user.User;

public interface UserService {

    void addUser(User user);
    User getUser(String email);

}
