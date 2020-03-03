package edu.ucsd.cse110.walkwalkrevolution.team.persistence;

import java.util.ArrayList;

import edu.ucsd.cse110.walkwalkrevolution.team.Team;

public class MockTeamDAO implements BaseTeamDAO {
    int id = 0;

    ArrayList<Team> teams;

    @Override
    public void addTeam(Team team) {
        teams.add(team);
        id++;
    }

    @Override
    public Team getTeam(long teamId) {
        return teams.get((int) teamId);
    }

    @Override
    public long getNextId() {
        return id;
    }
}
