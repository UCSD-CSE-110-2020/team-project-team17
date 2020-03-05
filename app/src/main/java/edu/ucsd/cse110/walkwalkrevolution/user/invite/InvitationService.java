package edu.ucsd.cse110.walkwalkrevolution.user.invite;

import edu.ucsd.cse110.walkwalkrevolution.team.Team;
import edu.ucsd.cse110.walkwalkrevolution.user.User;

public interface InvitationService {

    void addInvite(Invitation invite);
    Invitation getInvite(User user);

}
