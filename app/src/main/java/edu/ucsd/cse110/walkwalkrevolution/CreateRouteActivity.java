package edu.ucsd.cse110.walkwalkrevolution;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import edu.ucsd.cse110.walkwalkrevolution.activity.Walk;
import edu.ucsd.cse110.walkwalkrevolution.route.Route;
import edu.ucsd.cse110.walkwalkrevolution.route.RouteUtils;

public class CreateRouteActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_route);

        TextView routeTitle = findViewById(R.id.route_title);
        Button saveRoute = (Button) findViewById(R.id.save_button);
        Button cancelRoute = (Button) findViewById(R.id.cancel_button);


        saveRoute.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(TextUtils.isEmpty(routeTitle.getText())){
                    routeTitle.setError("Route Title is Required");
                } else {
                    Route route = new Route(routeTitle.getText().toString(), new Walk());
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
    }
}
