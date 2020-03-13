package edu.ucsd.cse110.walkwalkrevolution;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.api.Distribution;

import java.util.Map;

import edu.ucsd.cse110.walkwalkrevolution.activity.Activity;
import edu.ucsd.cse110.walkwalkrevolution.activity.ActivityUtils;
import edu.ucsd.cse110.walkwalkrevolution.activity.Walk;
import edu.ucsd.cse110.walkwalkrevolution.proposal.ProposalFirestoreService;
import edu.ucsd.cse110.walkwalkrevolution.proposal.ProposalService;
import edu.ucsd.cse110.walkwalkrevolution.proposal.adapter.AcceptedRVAdapter;
import edu.ucsd.cse110.walkwalkrevolution.proposal.adapter.DeclineBRRVAdapter;
import edu.ucsd.cse110.walkwalkrevolution.proposal.adapter.DeclineBTRVAdapter;
import edu.ucsd.cse110.walkwalkrevolution.route.Route;
import edu.ucsd.cse110.walkwalkrevolution.route.RouteRecycleView.RoutesAdapter;
import edu.ucsd.cse110.walkwalkrevolution.team.TeamRecycleView.TeamAdapter;

public class ProposeScreenActivity extends AppCompatActivity {
    private static final String TAG = "ProposeScreenActivity";

    public static Boolean scheduled = false;
    Button schdWalk, wthdWalk, wthdWalk2;
    TextView screenTitle;
    View one, two;

    private ProposeScreenActivity PSA = this;

    private String routeId;
    private TextView title;
    private TextView location;
    private TextView note;
    private TextView text_date;
    //private Route route;

    private TextView tag1;
    private TextView tag2;
    private TextView tag3;
    private TextView tag4;
    private TextView tag5;

    private ScrollView sc;

    private Button Accept;
    private Button DeclineBT;
    private Button DeclineBR;

    RecyclerView rvACC;
    RecyclerView rvDBT;
    RecyclerView rvDBR;

    AcceptedRVAdapter adapterACC;
    DeclineBTRVAdapter adapterDBT;
    DeclineBRRVAdapter adapterDBR;

    private RecyclerView.LayoutManager layoutManagerACC;

    private ProposalService ps;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_propose_screen);

        screenTitle = findViewById(R.id.pscreen_title);
        ps = WalkWalkRevolution.getProposalService();
        ps.getProposalRoute(WalkWalkRevolution.getUser().getTeamId(), this);

        schdWalk = findViewById(R.id.schedule_walk);
        wthdWalk = findViewById(R.id.withdraw_walk);
        wthdWalk2 = findViewById(R.id.withdraw_walkafter);
        title = (TextView) findViewById(R.id.title1);
        location = (TextView) findViewById(R.id.location_text);
        note = (TextView) findViewById(R.id.Note_view);
        text_date = findViewById(R.id.text_date_proposal);

        tag1 = (TextView) findViewById(R.id.tag1);
        tag2 = (TextView) findViewById(R.id.tag2);
        tag3 = (TextView) findViewById(R.id.tag3);
        tag4 = (TextView) findViewById(R.id.tag4);
        tag5 = (TextView) findViewById(R.id.tag5);
        one = findViewById(R.id.buttons_layout);
        two = findViewById(R.id.buttons_after);

        Accept = findViewById(R.id.Accepted);
        DeclineBR = findViewById(R.id.DeclineNAGR);
        DeclineBT = findViewById(R.id.DeclineBT);

        sc = findViewById(R.id.scv);

        ///*
        rvACC = (RecyclerView) findViewById(R.id.ACCPTEDlist);
        adapterACC = new AcceptedRVAdapter();
        rvACC.setAdapter(adapterACC);
        rvACC.setLayoutManager(new LinearLayoutManager(this));
         //*/
        //--------------------------

        rvDBT = (RecyclerView) findViewById(R.id.DCLNEBTlist);
        adapterDBT = new DeclineBTRVAdapter();
        rvDBT.setAdapter(adapterDBT);
        rvDBT.setLayoutManager(new LinearLayoutManager(this));

        //---------------------------

        rvDBR = (RecyclerView) findViewById(R.id.DCLNENAGRlist);
        adapterDBR = new DeclineBRRVAdapter();
        rvDBR.setAdapter(adapterDBR);
        rvDBR.setLayoutManager(new LinearLayoutManager(this));

        mapButton();
        renderPage();
    }

    public void mapButton(){

        TextView location_text = findViewById(R.id.location_text);

        location_text.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(location_text.getText().length() != 0) {
                    Uri gmmIntentUri = Uri.parse("geo:0,0?q=" + Uri.encode(location_text.getText().toString()));

                    //  Uri gmmIntentUri = Uri.parse("google.navigation:q=" + Uri.encode("UCSD Price Center")); Directions to query location

                    Intent mapIntent = new Intent(Intent.ACTION_VIEW, gmmIntentUri);
                    mapIntent.setPackage("com.google.android.apps.maps");
                    startActivity(mapIntent);
                }
            }
        });
    }

    private ProposeScreenActivity psa = this;
    public void renderPage() {
        Log.d("HELLOM", WalkWalkRevolution.getUser().getTeamId());

        if (ProposalFirestoreService.userProposed.equals("")) {
            one.setVisibility(View.GONE);
            two.setVisibility(View.GONE);
            title.setText("No Proposed Walk");
            text_date.setVisibility(View.GONE);
            location.setVisibility(View.GONE);
            tag1.setVisibility(View.GONE);
            tag2.setVisibility(View.GONE);
            tag3.setVisibility(View.GONE);
            tag4.setVisibility(View.GONE);
            tag5.setVisibility(View.GONE);
            note.setVisibility(View.GONE);
            sc.setVisibility(View.GONE);
        }
        else if (!WalkWalkRevolution.getUser().getEmail().equals(ProposalFirestoreService.userProposed)) {
            one.setVisibility(View.GONE);
            two.setVisibility(View.GONE);

            text_date.setVisibility(View.VISIBLE);
            location.setVisibility(View.VISIBLE);
            tag1.setVisibility(View.VISIBLE);
            tag2.setVisibility(View.VISIBLE);
            tag3.setVisibility(View.VISIBLE);
            tag4.setVisibility(View.VISIBLE);
            tag5.setVisibility(View.VISIBLE);
            note.setVisibility(View.VISIBLE);
            sc.setVisibility(View.VISIBLE);
        }
        else {
            text_date.setVisibility(View.VISIBLE);
            location.setVisibility(View.VISIBLE);
            tag1.setVisibility(View.VISIBLE);
            tag2.setVisibility(View.VISIBLE);
            tag3.setVisibility(View.VISIBLE);
            tag4.setVisibility(View.VISIBLE);
            tag5.setVisibility(View.VISIBLE);
            note.setVisibility(View.VISIBLE);
            sc.setVisibility(View.VISIBLE);

            if (ProposalFirestoreService.scheduled) {
                one.setVisibility(View.GONE);
                two.setVisibility(View.VISIBLE);
            }
            else {
                one.setVisibility(View.VISIBLE);
                two.setVisibility(View.GONE);
            }


        }



        wthdWalk2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                screenTitle.setText("No Proposed Walk");
                one.setVisibility(View.GONE);
                two.setVisibility(View.GONE);
                ProposalService ps = WalkWalkRevolution.getProposalService();
                Log.d("HELLOM", WalkWalkRevolution.getUser().getTeamId());
                ps.withdrawProposal(WalkWalkRevolution.getUser().getTeamId(), psa);
                ProposalFirestoreService.userProposed = "";
            }
        });

        schdWalk.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                screenTitle.setText("Scheduled Walk");
                one.setVisibility(View.GONE);
                two.setVisibility(View.VISIBLE);
                ProposalFirestoreService.scheduled = true;
                ps.scheduleWalk(ProposalFirestoreService.proposedRoute, WalkWalkRevolution.getUser().getTeamId(), ProposalFirestoreService.userProposed,
                        text_date.getText().toString());

            }
        });

        wthdWalk.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                screenTitle.setText("No Proposed Walk");
                one.setVisibility(View.GONE);
                two.setVisibility(View.GONE);
                ProposalService ps = WalkWalkRevolution.getProposalService();
                Log.d("HELLOM", WalkWalkRevolution.getUser().getTeamId());
                ps.withdrawProposal(WalkWalkRevolution.getUser().getTeamId(), psa);
                ProposalFirestoreService.userProposed = "";
            }
        });

        Accept.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Route route = ProposalFirestoreService.proposedRoute;
                Map<String, String> data = route.getResponses();
                for (Map.Entry<String, String> entry : data.entrySet()) {
                    if(entry.getKey().equals(WalkWalkRevolution.getUser().getName())) {
                        data.replace(entry.getKey(), "ACCEPTED");
                    }
                }
                route.setResponses(data);
                ProposalService ps = WalkWalkRevolution.getProposalService();
                ps.editProposal(route, WalkWalkRevolution.getUser().getTeamId(), ProposalFirestoreService.userProposed, text_date.getText().toString(), PSA);
            }
        });

        DeclineBT.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Route route = ProposalFirestoreService.proposedRoute;
                Map<String, String> data = route.getResponses();
                for (Map.Entry<String, String> entry : data.entrySet()) {
                    if(entry.getKey().equals(WalkWalkRevolution.getUser().getName())) {
                        data.replace(entry.getKey(), "DECLINE_BAD_TIME");
                    }
                }
                route.setResponses(data);
                ProposalService ps = WalkWalkRevolution.getProposalService();
                ps.editProposal(route, WalkWalkRevolution.getUser().getTeamId(), ProposalFirestoreService.userProposed, text_date.getText().toString(), PSA);
            }
        });

        DeclineBR.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Route route = ProposalFirestoreService.proposedRoute;
                Map<String, String> data = route.getResponses();
                for (Map.Entry<String, String> entry : data.entrySet()) {
                    if(entry.getKey().equals(WalkWalkRevolution.getUser().getName())) {
                        data.replace(entry.getKey(), "DECLINE_BAD_ROUTE");
                    }
                }
                route.setResponses(data);
                ProposalService ps = WalkWalkRevolution.getProposalService();
                ps.editProposal(route, WalkWalkRevolution.getUser().getTeamId(), ProposalFirestoreService.userProposed, text_date.getText().toString(), PSA);
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

    public void displayRouteDetail(Route route, String date) {
        if (route != null) {
            text_date.setText(date);

            title.setText(route.getTitle());
            location.setText(route.getLocation());
            setTags(route.getDescriptionTags());
            note.setText(route.getNotes());

            adapterACC.update(route.getResponses());
            rvACC.setAdapter(adapterACC);

            adapterDBT.update(route.getResponses());
            rvDBT.setAdapter(adapterDBT);

            adapterDBR.update(route.getResponses());
            rvDBR.setAdapter(adapterDBR);
        }
        renderPage();
    }

    public void displayRouteDetail(Route route) {
        if (route != null) {
            title.setText(route.getTitle());
            location.setText(route.getLocation());
            setTags(route.getDescriptionTags());
            note.setText(route.getNotes());

            adapterACC.update(route.getResponses());
            rvACC.setAdapter(adapterACC);

            adapterDBT.update(route.getResponses());
            rvDBT.setAdapter(adapterDBT);

            adapterDBR.update(route.getResponses());
            rvDBR.setAdapter(adapterDBR);
        }
        renderPage();
    }

    public void updateScreen() {ps.getProposalRoute(WalkWalkRevolution.getUser().getTeamId(), this);}

}
