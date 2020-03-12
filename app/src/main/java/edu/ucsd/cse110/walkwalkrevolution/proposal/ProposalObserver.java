package edu.ucsd.cse110.walkwalkrevolution.proposal;

import edu.ucsd.cse110.walkwalkrevolution.ProposeScreenActivity;

public class ProposalObserver {
    private ProposeScreenActivity act;
    public ProposalObserver(ProposeScreenActivity act) {
        this.act = act;
    }

    public void update() {
        act.renderPage();
    }

}
