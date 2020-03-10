package edu.ucsd.cse110.walkwalkrevolution.invitation.persistence;

public class InvitationServiceFactory {

    public InvitationService createInvitationService() {
        return new InvitationFirestoreService();
    }

}
