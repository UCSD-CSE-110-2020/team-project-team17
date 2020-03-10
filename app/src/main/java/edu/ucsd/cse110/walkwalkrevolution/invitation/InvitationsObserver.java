package edu.ucsd.cse110.walkwalkrevolution.invitation;

import java.util.List;

public interface InvitationsObserver {
    void update(List<Invitation> invitations);
}
