package edu.ucsd.cse110.walkwalkrevolution.proposal;

import java.util.ArrayList;

public class ProposalSubject {
    private ArrayList<ProposalObserver> po;
    private Boolean listening = false;
    public ProposalSubject() {
        po = new ArrayList<ProposalObserver>();
    }

    public void addObserver(ProposalObserver pob) {
        po.add(pob);
    }

    public void listen() {
        listening = true;
    }

    public void unListen() {
        listening = false;
    }

    public void notifyObs() {
        for (int i = 0; i < po.size(); i++) {
            po.get(i).update();
        }
    }
}
