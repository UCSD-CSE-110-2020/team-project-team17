package edu.ucsd.cse110.walkwalkrevolution;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;

import edu.ucsd.cse110.walkwalkrevolution.activity.Walk;
import edu.ucsd.cse110.walkwalkrevolution.route.Route;

public class RoutesActivity extends AppCompatActivity {
    public static RecyclerView recyclerView;
    RoutesAdapter adapter;
    private RecyclerView.LayoutManager layoutManager;

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
    }

    @Override
    protected void onStart(){
        super.onStart();
        adapter.updateList();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.routes_menu, menu);
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
        Intent createRoute = new Intent(this, CreateRouteActivity.class);
        startActivity(createRoute);
    }
}
