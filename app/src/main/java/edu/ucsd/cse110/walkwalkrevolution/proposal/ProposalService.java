package edu.ucsd.cse110.walkwalkrevolution.proposal;

import java.util.List;
import java.util.Map;

import edu.ucsd.cse110.walkwalkrevolution.ProposeScreenActivity;
import edu.ucsd.cse110.walkwalkrevolution.route.Route;
import edu.ucsd.cse110.walkwalkrevolution.user.User;

public interface ProposalService {

    void scheduleWalk(Route route, String teamId, String userId);
    void addProposal(Route route, String teamId, String userId);
    void withdrawProposal(String teamId, ProposeScreenActivity act);
    void setAvailability(Route route, Route.Availability response, String teamId, String userId);
    //void withdrawProposal(String teamId, );
    void getProposalRoute(String teamId, ProposeScreenActivity act);
    // void getAvailability(Route route, String teamId, String userEmail);
   // Route getProposal(User user);
    //List<Route> getRoutes(User user);

}
