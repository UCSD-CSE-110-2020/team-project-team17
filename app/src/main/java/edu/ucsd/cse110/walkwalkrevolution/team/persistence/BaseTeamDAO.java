package edu.ucsd.cse110.walkwalkrevolution.team.persistence;

import java.util.Map;

import edu.ucsd.cse110.walkwalkrevolution.route.Route;
import edu.ucsd.cse110.walkwalkrevolution.team.Team;

public interface BaseTeamDAO {
    void addTeam(Team team);
    Team getTeam(long teamId);
    long getNextId();

}
