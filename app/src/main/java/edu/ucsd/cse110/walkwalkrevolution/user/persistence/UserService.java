package edu.ucsd.cse110.walkwalkrevolution.user.persistence;

import java.util.List;

import edu.ucsd.cse110.walkwalkrevolution.team.Team;
import edu.ucsd.cse110.walkwalkrevolution.user.User;
import edu.ucsd.cse110.walkwalkrevolution.user.invite.Invitation;

public interface UserService {

    void addUser(User user);
    void refresh();
    void getTeam(Team team, User user);
    void getReceiver(Invitation invitation, String userEmail);

}
