package edu.ucsd.cse110.walkwalkrevolution.proposal;

public class ProposalServiceFactory {
    public ProposalService createProposalService(){
        return new ProposalFirestoreService();
    }

}
