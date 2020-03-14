package edu.ucsd.cse110.walkwalkrevolution;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.ToggleButton;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

import java.util.Calendar;
import java.util.HashMap;
import java.util.Map;

import edu.ucsd.cse110.walkwalkrevolution.activity.Activity;
import edu.ucsd.cse110.walkwalkrevolution.activity.ActivityUtils;
import edu.ucsd.cse110.walkwalkrevolution.activity.Walk;
import edu.ucsd.cse110.walkwalkrevolution.proposal.ProposalFirestoreService;
import edu.ucsd.cse110.walkwalkrevolution.proposal.ProposalService;
import edu.ucsd.cse110.walkwalkrevolution.route.Route;
import edu.ucsd.cse110.walkwalkrevolution.route.RouteRecycleView.RoutesAdapter;

import edu.ucsd.cse110.walkwalkrevolution.route.RouteUtils;
import edu.ucsd.cse110.walkwalkrevolution.team.Team;

public class RoutesDetailActivity extends AppCompatActivity {
    private static final String TAG = "RoutesDetailActivity";

    public static final String ROUTE = "edu.ucsd.cse110.walkwalkrevolution.ROUTE";
    public static final String ROUTE_ID = "edu.ucsd.cse110.walkwalkrevolution.ROUTE_ID";

    public static final String ACCEPT = "ACCEPTED";
    public static final String DECLINEBT = "DECLINE_BAD_TIME";
    public static final String DECLINEBR = "DECLINE_BAD_ROUTE";

    private static final int GET_DATE = 1337;

    public Route route;
    public Route override;
    public long id;
    public boolean isTeam;
    private Team current;

    private TextView title;
    private TextView steps;
    private TextView miles;
    private TextView duration;
    private TextView date;
    private TextView location;
    private TextView note;

    private TextView tag1;
    private TextView tag2;
    private TextView tag3;
    private TextView tag4;
    private TextView tag5;

    private Button start;
    private Button proposal;

    private ToggleButton favorite;

    private ImageView walked;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_routes_detail);

        Intent intent = getIntent();
        String serialized = intent.getStringExtra(RoutesAdapter.ROUTE);

        try {
            route = RouteUtils.deserialize(serialized);
        } catch (Exception e){
            throw new RuntimeException(e.getLocalizedMessage());
        }

        if(intent.hasExtra(RoutesAdapter.OVER)){
            String serializedO = intent.getStringExtra(RoutesAdapter.OVER);
            try {
                override = RouteUtils.deserialize(serializedO);
            } catch (Exception e){
                throw new RuntimeException(e.getLocalizedMessage());
            }
        }

        current = new Team();

        title = (TextView) findViewById(R.id.title1);
        steps = (TextView) findViewById(R.id.numOfSteps);
        miles = (TextView) findViewById(R.id.numOfMiles);
        duration = (TextView) findViewById(R.id.numOfDur);
        date = (TextView) findViewById(R.id.numOfDay);
        location = (TextView) findViewById(R.id.dontchagne_location_texttt);
        start = (Button) findViewById(R.id.start_preroute);
        note = (TextView) findViewById(R.id.dont_changeNoteView);
        proposal = (Button) findViewById(R.id.propose_walk);

        tag1 = (TextView) findViewById(R.id.tag1);
        tag2 = (TextView) findViewById(R.id.tag2);
        tag3 = (TextView) findViewById(R.id.tag3);
        tag4 = (TextView) findViewById(R.id.tag4);
        tag5 = (TextView) findViewById(R.id.tag5);

        title.setText(route.getTitle());

        walked = findViewById(R.id.walked);
        favorite = findViewById(R.id.favorite);

        if((override != null && override.getActivity().isExist()) || route.getActivity().isExist()) {
            if(override != null){
                steps.setText(override.getActivity().getDetail(Walk.STEP_COUNT));
                miles.setText(override.getActivity().getDetail(Walk.MILES));
                duration.setText(override.getActivity().getDetail(Walk.DURATION));
                date.setText(ActivityUtils.timeToMonthDay(
                        ActivityUtils.stringToTime(override.getActivity().getDetail(Activity.DATE))));
            } else {
                steps.setText(route.getActivity().getDetail(Walk.STEP_COUNT));
                miles.setText(route.getActivity().getDetail(Walk.MILES));
                duration.setText(route.getActivity().getDetail(Walk.DURATION));
                date.setText(ActivityUtils.timeToMonthDay(
                        ActivityUtils.stringToTime(route.getActivity().getDetail(Activity.DATE))));
            }
        } else {
            TextView stepTitle = findViewById(R.id.Steps);
            TextView milesTitle = findViewById(R.id.Miles);
            TextView durationTitle = findViewById(R.id.dur);

            stepTitle.setVisibility(View.GONE);
            milesTitle.setVisibility(View.GONE);
            durationTitle.setVisibility(View.GONE);
            steps.setVisibility(View.GONE);
            miles.setVisibility(View.GONE);
            duration.setVisibility(View.GONE);
            date.setVisibility(View.GONE);
        }


        location.setText(route.getLocation());
        setTags(route.getDescriptionTags());
        note.setText(route.getNotes());

        start.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v)
            {
                Intent intent = new Intent(v.getContext(), WalkActivity.class);
                intent.putExtra(Route.ROUTE, serialized);
                intent.putExtra("route_title", route.getTitle());
                finish();
                startActivity(intent);
            }
        });

        favorite.setChecked(WalkWalkRevolution.getRouteDao().isFavorite(route));
        if(WalkWalkRevolution.getRouteDao().isFavorite(route)) {
            favorite.setBackgroundDrawable(ContextCompat.getDrawable(getApplicationContext(), R.drawable.img_yellow_star));
        } else {
            favorite.setBackgroundDrawable(ContextCompat.getDrawable(getApplicationContext(), R.drawable.img_star_grey));
        }

        favorite.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked)
            {
                if (isChecked) {
                    favorite.setBackgroundDrawable(ContextCompat.getDrawable(getApplicationContext(), R.drawable.img_yellow_star));
                    WalkWalkRevolution.getRouteDao().addFavorite(route);
                } else {
                    favorite.setBackgroundDrawable(ContextCompat.getDrawable(getApplicationContext(), R.drawable.img_star_grey));
                    WalkWalkRevolution.getRouteDao().removeFavorite(route);
                }
            }});

        proposal.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v)
            {
                if ( ProposalFirestoreService.proposedRoute == null ) {
                    Intent intent = new Intent(RoutesDetailActivity.this, DateAndTimeActivity.class);
                    startActivityForResult(intent, GET_DATE);
                }
                else {
                    Toast.makeText(RoutesDetailActivity.this, "A route is already proposed", Toast.LENGTH_SHORT).show();
                }
            }
        });

        if(route.getUserId().equals(WalkWalkRevolution.getUser().getEmail()) &&
                Boolean.parseBoolean(route.getActivity().getDetail(Activity.EXIST)) ||
                !route.getUserId().equals(WalkWalkRevolution.getUser().getEmail()) &&
                        WalkWalkRevolution.getRouteDao().walkedTeamRoute(route)){
            walked.setVisibility(View.VISIBLE);
        } else {
            walked.setVisibility(View.INVISIBLE);
        }

    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent intent){
        super.onActivityResult(requestCode, resultCode, intent);
        int year, month, dayOfMonth, hour, minute;
        Calendar cal = Calendar.getInstance();

        if(requestCode == GET_DATE && resultCode == RESULT_OK) {
            year = intent.getIntExtra(DateAndTimeActivity.YEAR, cal.get(Calendar.YEAR));
            month = intent.getIntExtra(DateAndTimeActivity.MONTH, cal.get(Calendar.MONTH));
            dayOfMonth = intent.getIntExtra(DateAndTimeActivity.DAY_OF_MONTH, cal.get(Calendar.DAY_OF_MONTH));
            hour = intent.getIntExtra(DateAndTimeActivity.HOUR, cal.get(Calendar.HOUR));
            minute = intent.getIntExtra(DateAndTimeActivity.MINUTE, cal.get(Calendar.MINUTE));

            Log.d(TAG, "onClick: " + year + " " + dayOfMonth + " " + month + " " + hour + " " + minute);

            ProposalService ps = WalkWalkRevolution.getProposalService();
            String teamId = WalkWalkRevolution.getUser().getTeamId();

            int length = current.getUsers().size();

            Map<String, String> data = new HashMap<>();

            data.put(WalkWalkRevolution.getUser().getName(), ACCEPT);

            route.setResponses(data);

            WalkWalkRevolution.getRouteService().updateRoute(route);

            if(route.getUserId().equals(WalkWalkRevolution.getUser().getEmail()))
                WalkWalkRevolution.getRouteDao().addRoute(route);

            cal.set(year, month, dayOfMonth, hour, minute);
            ps.addProposal(route, teamId, WalkWalkRevolution.getUser().getEmail(), cal.getTime());

            Intent newIntent = new Intent(this, ProposeScreenActivity.class);
            finish();
            startActivity(newIntent);
        } else {
            finish();
        }
    }


    public void setTags(String string) {

        String[] tags = string.split(",");
        int length = tags.length;

        if(length > 0)
        {
            tag1.setText(tags[0]);
            length = length - 1;
            if(length > 0)
            {
                tag2.setText(tags[1]);
                length = length - 1;
                if(length > 0)
                {
                    tag3.setText(tags[2]);
                    length = length - 1;
                    if(length > 0)
                    {
                        tag4.setText(tags[3]);
                        length = length - 1;
                        if(length > 0)
                        {
                            tag5.setText(tags[4]);
                        }
                    }
                }
            }
        }

    }


}
