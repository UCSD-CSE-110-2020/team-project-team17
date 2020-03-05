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

    Button yourRoutes, teammateRoutes;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_routes);

        recyclerView = (RecyclerView) findViewById(R.id.routes);
        recyclerView.setHasFixedSize(true);

        adapter = new RoutesAdapter();
        recyclerView.setAdapter(adapter);

        layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);

        yourRoutes = findViewById(R.id.your_routes);
        teammateRoutes = findViewById(R.id.teammate_routes);


        yourRoutes.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
//               recyclerView.setVisibility(View.VISIBLE);
                adapter.updateRoute();
            }
        });

        teammateRoutes.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
//                recyclerView.setVisibility(View.GONE);
                adapter.updateTeam();
            }
        });

    }

    @Override
    protected void onStart(){
        super.onStart();
        adapter.updateRoute();
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
