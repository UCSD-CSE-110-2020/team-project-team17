package edu.ucsd.cse110.walkwalkrevolution.route.RouteRecycleView;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import edu.ucsd.cse110.walkwalkrevolution.R;
import edu.ucsd.cse110.walkwalkrevolution.RoutesActivity;
import edu.ucsd.cse110.walkwalkrevolution.RoutesDetailActivity;
import edu.ucsd.cse110.walkwalkrevolution.WalkWalkRevolution;
import edu.ucsd.cse110.walkwalkrevolution.activity.Activity;
import edu.ucsd.cse110.walkwalkrevolution.activity.ActivityUtils;
import edu.ucsd.cse110.walkwalkrevolution.activity.Walk;
import edu.ucsd.cse110.walkwalkrevolution.route.Route;
import edu.ucsd.cse110.walkwalkrevolution.route.RouteUtils;
import edu.ucsd.cse110.walkwalkrevolution.route.Routes;
import edu.ucsd.cse110.walkwalkrevolution.route.RoutesObserver;

public class RoutesAdapter extends RecyclerView.Adapter<RoutesAdapter.ViewHolder> implements RoutesObserver {

    private List<Route> rList;
    private Routes routes;
    public static final String ROUTE = "ROUTE";
    public static final String TEAM = "TEAM";
    //private Context context;

    public RoutesAdapter() {
        routes = new Routes();
        routes.subscribe(this);
        rList = new ArrayList<>();
        updateRoute();
        //this.context = context;
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        public TextView routeTitle;
        public TextView steps;
        public TextView miles;
        public TextView duration;
        public TextView date;
        public TextView favorite;
        public TextView walked;

        public ViewHolder(View itemView) {
            // Stores the itemView in a public final member variable that can be used
            // to access the context from any ViewHolder instance.
            super(itemView);

            routeTitle = (TextView) itemView.findViewById(R.id.route_title);
            steps = (TextView) itemView.findViewById(R.id.steps);
            miles = (TextView) itemView.findViewById(R.id.miles);
            duration = (TextView) itemView.findViewById(R.id.duration);
            date = (TextView) itemView.findViewById(R.id.date);
            favorite = itemView.findViewById(R.id.favorite);
            walked = itemView.findViewById(R.id.walked);
        }
    }

    @Override
    public RoutesAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        Context context = parent.getContext();
        LayoutInflater inflater = LayoutInflater.from(context);

        // Inflate the custom layout
        View contactView = inflater.inflate(R.layout.item_route, parent, false);

        // Return a new holder instance
        ViewHolder viewHolder = new ViewHolder(contactView);


        contactView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int index = RoutesActivity.recyclerView.getChildAdapterPosition(view);
                Route item = rList.get(index);

                //Toast.makeText(context, item.getTitle(), Toast.LENGTH_LONG).show();
                try {
                    openRoutesDetailActivity(view, item);
                } catch (Exception e){
                    throw new RuntimeException(e.getLocalizedMessage());
                }

            }
        });
        return viewHolder;
    }

    public void openRoutesDetailActivity(View view, Route item) throws Exception{
        Intent intent = new Intent(view.getContext(), RoutesDetailActivity.class);
        String serialized = RouteUtils.serialize(item);
        intent.putExtra(ROUTE, serialized);
        view.getContext().startActivity(intent);
    }

    // Involves populating data into the item through holder
    @Override
    public void onBindViewHolder(RoutesAdapter.ViewHolder viewHolder, int position) {
        // Get the data model based on position
        Route route = rList.get(position);

        // Set item views based on your views and data model
        TextView routeTitle = viewHolder.routeTitle;
        routeTitle.setText(route.getTitle());

        TextView steps = viewHolder.steps;
        TextView miles = viewHolder.miles;
        TextView duration = viewHolder.duration;
        TextView date = viewHolder.date;
        TextView favorite = viewHolder.favorite;
        TextView walked = viewHolder.walked;

        if(Boolean.parseBoolean(route.getActivity().getDetail(Activity.EXIST))) {
            steps.setVisibility(View.VISIBLE);
            miles.setVisibility(View.VISIBLE);
            duration.setVisibility(View.VISIBLE);
            date.setVisibility(View.VISIBLE);
            steps.setText(route.getActivity().getDetail(Walk.STEP_COUNT));

            miles.setText(route.getActivity().getDetail(Walk.MILES));

            duration.setText(route.getActivity().getDetail(Walk.DURATION));

            date.setText(ActivityUtils.timeToMonthDay(
                    ActivityUtils.stringToTime(route.getActivity().getDetail(Activity.DATE))));
        } else {
            steps.setVisibility(View.INVISIBLE);
            miles.setVisibility(View.INVISIBLE);
            duration.setVisibility(View.INVISIBLE);
            date.setVisibility(View.INVISIBLE);
        }

        favorite.setBackgroundResource(R.drawable.img_star_blue);
        walked.setBackgroundResource(R.drawable.img_green_check);


        if(WalkWalkRevolution.getRouteDao().isFavorite(route)) {
            favorite.setVisibility(View.VISIBLE);
        } else {
            favorite.setVisibility(View.INVISIBLE);
        }

        if(route.getUserId().equals(WalkWalkRevolution.getUser().getEmail()) &&
                Boolean.parseBoolean(route.getActivity().getDetail(Activity.EXIST)) ||
                !route.getUserId().equals(WalkWalkRevolution.getUser().getEmail()) &&
                        WalkWalkRevolution.getRouteDao().walkedTeamRoute(route)){
            walked.setVisibility(View.VISIBLE);
        } else {
            walked.setVisibility(View.INVISIBLE);
        }
    }

    // Returns the total count of items in the list
    @Override
    public int getItemCount() {
        return rList.size();
    }

    public void add(Route route){
        this.rList.add(route);
    }

    public void update(List<Route> rList) {
        for(Route r: rList){
            this.rList.add(r);
        }
        Collections.sort(rList, (a, b) -> {
            return a.getTitle().compareTo(b.getTitle());
        });
        notifyDataSetChanged();
    }

    public void updateTeam() {
        rList = new ArrayList<>();
        routes.getTeamRoutes();
    }

    public void updateRoute(){
        rList = new ArrayList<>();
        routes.getLocal();
    }

    public Routes getRoutes(){
        return routes;
    }
}