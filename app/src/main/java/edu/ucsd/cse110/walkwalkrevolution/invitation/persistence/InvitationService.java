package edu.ucsd.cse110.walkwalkrevolution.invitation.persistence;

import android.app.Activity;
import android.content.Context;

import edu.ucsd.cse110.walkwalkrevolution.invitation.Invitation;
import edu.ucsd.cse110.walkwalkrevolution.invitation.Invitations;
import edu.ucsd.cse110.walkwalkrevolution.user.User;

public interface InvitationService {
    void addInvitation(Invitation invitation);
    void getInvitations(Invitations invitations, User user);
}
