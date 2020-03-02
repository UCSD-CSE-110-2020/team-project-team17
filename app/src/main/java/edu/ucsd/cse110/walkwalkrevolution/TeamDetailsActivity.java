package edu.ucsd.cse110.walkwalkrevolution;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;

import java.util.ArrayList;

import edu.ucsd.cse110.walkwalkrevolution.team.Team;
import edu.ucsd.cse110.walkwalkrevolution.team.TeamRecycleView.TeamAdapter;
import edu.ucsd.cse110.walkwalkrevolution.user.User;

public class TeamDetailsActivity extends AppCompatActivity {

    ArrayList<User> users;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_team_details);

        RecyclerView rvUsers = findViewById(R.id.rvUsers);

        users = Team.generateTestEntries(50);

        TeamAdapter adapter = new TeamAdapter(new Team(users));
        rvUsers.setAdapter(adapter);

        rvUsers.setLayoutManager(new LinearLayoutManager(this));
    }

    // Make menu / toolbar the one with an add button.
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.add_button_menu, menu);
        return super.onCreateOptionsMenu(menu);
    }

    // handle button activities on the menu / toolbar.
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        if (id == R.id.add_button) {
            // inviteTeamMemberActivity(); This button is to invite new team members.
        }
        return super.onOptionsItemSelected(item);
    }

}
