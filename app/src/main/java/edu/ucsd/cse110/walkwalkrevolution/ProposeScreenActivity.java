package edu.ucsd.cse110.walkwalkrevolution;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import edu.ucsd.cse110.walkwalkrevolution.activity.Activity;
import edu.ucsd.cse110.walkwalkrevolution.activity.ActivityUtils;
import edu.ucsd.cse110.walkwalkrevolution.activity.Walk;
import edu.ucsd.cse110.walkwalkrevolution.proposal.ProposalFirestoreService;
import edu.ucsd.cse110.walkwalkrevolution.proposal.ProposalService;
import edu.ucsd.cse110.walkwalkrevolution.route.Route;
import edu.ucsd.cse110.walkwalkrevolution.route.RouteRecycleView.RoutesAdapter;

public class ProposeScreenActivity extends AppCompatActivity {

    public static Boolean scheduled = false;
    public static String userProposed = "";

    Button schdWalk, wthdWalk, afterBtn;
    TextView screenTitle;
    View one, two;

    private String routeId;
    private TextView title;
    private TextView location;
    private TextView note;
    //private Route route;

    private TextView tag1;
    private TextView tag2;
    private TextView tag3;
    private TextView tag4;
    private TextView tag5;

    private ScrollView sc;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_propose_screen);

        screenTitle = findViewById(R.id.pscreen_title);
        ProposalService ps = WalkWalkRevolution.getProposalService();
        ps.getProposalRoute(WalkWalkRevolution.getUser().getTeamId(), this);


        schdWalk = findViewById(R.id.schedule_walk);
        wthdWalk = findViewById(R.id.withdraw_walk);
        afterBtn = findViewById(R.id.withdraw_walkafter);
        title = (TextView) findViewById(R.id.title1);
        location = (TextView) findViewById(R.id.location_text);
        note = (TextView) findViewById(R.id.Note_view);

        tag1 = (TextView) findViewById(R.id.tag1);
        tag2 = (TextView) findViewById(R.id.tag2);
        tag3 = (TextView) findViewById(R.id.tag3);
        tag4 = (TextView) findViewById(R.id.tag4);
        tag5 = (TextView) findViewById(R.id.tag5);
        one = findViewById(R.id.buttons_layout);
        two = findViewById(R.id.buttons_after);



        if (!WalkWalkRevolution.getUser().getEmail().equals(userProposed)) {
            one.setVisibility(View.GONE);
            two.setVisibility(View.GONE);
            title.setText("No Proposed Walk");
            location.setVisibility(View.GONE);
            tag1.setVisibility(View.GONE);
            tag2.setVisibility(View.GONE);
            tag3.setVisibility(View.GONE);
            tag4.setVisibility(View.GONE);
            tag5.setVisibility(View.GONE);
            note.setVisibility(View.GONE);
        }
        else {
            schdWalk.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    screenTitle.setText("Scheduled Walk");
                    one.setVisibility(View.GONE);
                    two.setVisibility(View.VISIBLE);
                }
            });

            wthdWalk.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    one.setVisibility(View.GONE);
                    two.setVisibility(View.GONE);
                    ProposalService ps = WalkWalkRevolution.getProposalService();
                    ps.withdrawProposal(WalkWalkRevolution.getUser().getTeamId());
                    userProposed = "";
                }
            });

            afterBtn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    screenTitle.setText("No Proposed Walk");
                    one.setVisibility(View.GONE);
                    two.setVisibility(View.GONE);
                }
            });
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

    public void displayRouteDetail(Route route) {

        if (route != null) {
            title.setText(route.getTitle());
            location.setText(route.getLocation());
            setTags(route.getDescriptionTags());
            note.setText(route.getNotes());
        }
    }



}
