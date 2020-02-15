package edu.ucsd.cse110.walkwalkrevolution;

import android.content.Intent;
import android.os.Bundle;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import edu.ucsd.cse110.walkwalkrevolution.activity.Activity;
import edu.ucsd.cse110.walkwalkrevolution.activity.ActivityUtils;
import edu.ucsd.cse110.walkwalkrevolution.activity.Walk;
import edu.ucsd.cse110.walkwalkrevolution.route.Route;

public class RoutesDetailActivity extends AppCompatActivity {

    private TextView title;
    private TextView steps;
    private TextView miles;
    private TextView duration;
    private TextView date;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_routes_detail);

        Intent intent = getIntent();
        long id = intent.getLongExtra(RoutesAdapter.EXTRA_TEXT, 0);

        Route route = WalkWalkRevolution.getRouteDao().getRoute(id);

        title = (TextView) findViewById(R.id.title);
        steps = (TextView) findViewById(R.id.numOfSteps);
        miles = (TextView) findViewById(R.id.numOfMiles);
        duration = (TextView) findViewById(R.id.numOfDur);
        date = (TextView) findViewById(R.id.numOfDay);

        title.setText(route.getTitle());
        steps.setText(route.getActivity().getDetail(Walk.STEP_COUNT));
        miles.setText(route.getActivity().getDetail(Walk.MILES));
        duration.setText(route.getActivity().getDetail(Walk.DURATION));
        date.setText(ActivityUtils.timeToMonthDay(
                ActivityUtils.stringToTime(route.getActivity().getDetail(Activity.DATE))));


    }
}
