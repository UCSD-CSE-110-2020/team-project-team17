package edu.ucsd.cse110.walkwalkrevolution.user.invite;

import edu.ucsd.cse110.walkwalkrevolution.TeamInvitationActivity;

public interface InvitationService {

    void addInvite(Invitation invite);
    void deleteInvite();
    void getInvite(String userEmail, TeamInvitationActivity act);

}
