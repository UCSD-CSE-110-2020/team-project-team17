package edu.ucsd.cse110.walkwalkrevolution;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;

import java.util.ArrayList;
import java.util.List;

import edu.ucsd.cse110.walkwalkrevolution.team.Team;
import edu.ucsd.cse110.walkwalkrevolution.team.TeamRecycleView.TeamAdapter;
import edu.ucsd.cse110.walkwalkrevolution.user.User;
import edu.ucsd.cse110.walkwalkrevolution.user.persistence.UserFirestoreService;

//TODO: Redesign to see both Invite and Invitations buttons
public class TeamDetailsActivity extends AppCompatActivity {

    public static boolean testMode;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_team_details);

        RecyclerView rvUsers = findViewById(R.id.rvUsers);

        TeamAdapter adapter;

        if(!true){
            adapter = new TeamAdapter(new Team(new ArrayList<User>())); // Empty list
            adapter.update(Team.generateTestEntries(50));         // Update to 50 list
        } else {
            adapter = new TeamAdapter(new Team());
        }

        rvUsers.setAdapter(adapter);
        rvUsers.setLayoutManager(new LinearLayoutManager(this));
    }

    // Make menu / toolbar the one with an add button.
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.add_or_list_menu, menu);
        return super.onCreateOptionsMenu(menu);
    }

    // handle button activities on the menu / toolbar.
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        if (id == R.id.add_button) {
            teamInviteActivity();
        } else if (id == R.id.list_button){
            teamInvitationActivity();
        }
        return super.onOptionsItemSelected(item);
    }

    public void teamInviteActivity(){
        Intent createRoute = new Intent(this, TeamInviteActivity.class);
        startActivity(createRoute);
    }

    public void teamInvitationActivity(){
        Intent seeInvitation = new Intent(this, TeamInvitationActivity.class);
        startActivity(seeInvitation);
    }

}
