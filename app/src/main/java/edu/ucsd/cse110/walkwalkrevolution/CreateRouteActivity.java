package edu.ucsd.cse110.walkwalkrevolution;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import java.util.HashMap;
import java.util.Map;

import edu.ucsd.cse110.walkwalkrevolution.DescriptionTags.DescriptionTagsListAdapter;
import edu.ucsd.cse110.walkwalkrevolution.activity.Activity;
import edu.ucsd.cse110.walkwalkrevolution.activity.Walk;
import edu.ucsd.cse110.walkwalkrevolution.route.Route;

public class CreateRouteActivity extends AppCompatActivity {    private RecyclerView recyclerView;
    DescriptionTagsListAdapter adapter;
    private RecyclerView.LayoutManager layoutManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_route);

        TextView routeTitle = findViewById(R.id.route_title);
        Button saveRoute = (Button) findViewById(R.id.save_button);
        Button cancelRoute = (Button) findViewById(R.id.cancel_button);

        Bundle extras = getIntent().getExtras();


        saveRoute.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(TextUtils.isEmpty(routeTitle.getText())){
                    routeTitle.setError("Route Title is Required");
                } else {
                    Activity activity;
                    Bundle extras = getIntent().getExtras();
                    if(extras != null){
                        Map<String, String> data = new HashMap<String, String>(){{
                            put(Walk.STEP_COUNT, extras.getString(Walk.STEP_COUNT));
                            put(Walk.MILES, extras.getString(Walk.MILES));
                            put(Walk.DURATION, extras.getString(Walk.DURATION));
                        }};
                        activity = new Walk(data);
                        activity.setDate();
                    } else {
                        activity = new Walk();
                    }
                    Route route = new Route(routeTitle.getText().toString(), activity);
                    WalkWalkRevolution.getRouteDao().addRoute(route);
                    finish();
                }
            }
        });

        cancelRoute.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        recyclerView = (RecyclerView) findViewById(R.id.route_tags);
        recyclerView.setHasFixedSize(true);

        adapter = new DescriptionTagsListAdapter();
        recyclerView.setAdapter(adapter);

        layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);
    }

    @Override
    protected void onStart(){
        super.onStart();
        adapter.updateList();
    }
}
