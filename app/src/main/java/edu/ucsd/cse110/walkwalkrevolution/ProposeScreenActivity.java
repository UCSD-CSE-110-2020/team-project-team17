package edu.ucsd.cse110.walkwalkrevolution;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
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

        one = findViewById(R.id.buttons_layout);
        two = findViewById(R.id.buttons_after);

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
                screenTitle.setText("No Proposed Walk");
                one.setVisibility(View.GONE);
                two.setVisibility(View.GONE);
                ProposalService ps = WalkWalkRevolution.getProposalService();
                ps.withdrawProposal();
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
        title = (TextView) findViewById(R.id.title1);
        location = (TextView) findViewById(R.id.location_text);
        note = (TextView) findViewById(R.id.Note_view);

        tag1 = (TextView) findViewById(R.id.tag1);
        tag2 = (TextView) findViewById(R.id.tag2);
        tag3 = (TextView) findViewById(R.id.tag3);
        tag4 = (TextView) findViewById(R.id.tag4);
        tag5 = (TextView) findViewById(R.id.tag5);

        if (route != null) {
            title.setText(route.getTitle());
            location.setText(route.getLocation());
            setTags(route.getDescriptionTags());
            note.setText(route.getNotes());
        }
    }



}
