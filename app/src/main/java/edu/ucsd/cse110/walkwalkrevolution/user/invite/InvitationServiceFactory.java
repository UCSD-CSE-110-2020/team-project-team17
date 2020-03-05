package edu.ucsd.cse110.walkwalkrevolution.user.invite;

public class InvitationServiceFactory {

    public InvitationService createInvitationService(){
        return new InvitationFirestoreService();
    }

}
