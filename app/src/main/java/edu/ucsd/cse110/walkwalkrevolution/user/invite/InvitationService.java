package edu.ucsd.cse110.walkwalkrevolution.user.invite;

import edu.ucsd.cse110.walkwalkrevolution.TeamInvitationActivity;
import edu.ucsd.cse110.walkwalkrevolution.team.Team;
import edu.ucsd.cse110.walkwalkrevolution.user.User;

public interface InvitationService {

    void addInvite(Invitation invite);
    void confirmInvite(Invitation invite);
    void getInvite(String userEmail, TeamInvitationActivity act);

}
