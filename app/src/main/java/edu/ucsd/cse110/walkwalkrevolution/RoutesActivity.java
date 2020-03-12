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

import edu.ucsd.cse110.walkwalkrevolution.route.RouteRecycleView.RoutesAdapter;

public class RoutesActivity extends AppCompatActivity {
    public static RecyclerView recyclerView;
    RoutesAdapter adapter;
    private RecyclerView.LayoutManager layoutManager;


    private Button indiv, team;

    boolean isTeam;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_routes);
        isTeam = false;

        recyclerView = (RecyclerView) findViewById(R.id.routes);
        recyclerView.setHasFixedSize(true);

        adapter = new RoutesAdapter();
        recyclerView.setAdapter(adapter);

        layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);


        this.indiv = findViewById(R.id.individual_button);

        this.indiv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                isTeam = false;
                adapter.updateRoute();
            }
        });

        this.team = findViewById(R.id.team_button);

        this.team.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                isTeam = true;
                adapter.updateTeam();
            }
        });
    }

    @Override
    protected void onStart(){
        super.onStart();
    }

    @Override
    protected void onResume() {
        super.onResume();
        if(!isTeam)
            adapter.updateRoute();
        else
            adapter.updateTeam();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.add_propose_menu, menu);
        return super.onCreateOptionsMenu(menu);
    }

    // handle button activities
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        if (id == R.id.add_button) {
            createRouteActivity();
        }

        if (id == R.id.proposeScreen_button) {
            createProposeScreenActivity();
        }

        return super.onOptionsItemSelected(item);
    }

    public void createRouteActivity() {
        Intent createRoute = new Intent(this, CreateRouteActivity.class);
        startActivity(createRoute);
    }

    public void createProposeScreenActivity() {
        Intent create = new Intent(this, ProposeScreenActivity.class);
        startActivity(create);
    }
}
