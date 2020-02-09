package edu.ucsd.cse110.walkwalkrevolution;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import edu.ucsd.cse110.walkwalkrevolution.activity.Activity;
import edu.ucsd.cse110.walkwalkrevolution.activity.ActivityUtils;
import edu.ucsd.cse110.walkwalkrevolution.activity.Walk;
import edu.ucsd.cse110.walkwalkrevolution.route.Route;
import edu.ucsd.cse110.walkwalkrevolution.route.Routes;

public class RoutesAdapter extends
        RecyclerView.Adapter<RoutesAdapter.ViewHolder> {

    private Routes routes;

    public RoutesAdapter() {
        routes = new Routes();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        public TextView routeTitle;
        public TextView steps;
        public TextView miles;
        public TextView duration;
        public TextView date;

        public ViewHolder(View itemView) {
            // Stores the itemView in a public final member variable that can be used
            // to access the context from any ViewHolder instance.
            super(itemView);

            routeTitle = (TextView) itemView.findViewById(R.id.route_title);
            steps = (TextView) itemView.findViewById(R.id.steps);
            miles = (TextView) itemView.findViewById(R.id.miles);
            duration = (TextView) itemView.findViewById(R.id.duration);
            date = (TextView) itemView.findViewById(R.id.date);
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
        return viewHolder;
    }

    // Involves populating data into the item through holder
    @Override
    public void onBindViewHolder(RoutesAdapter.ViewHolder viewHolder, int position) {
        // Get the data model based on position
        Route route = routes.get(position);

        // Set item views based on your views and data model
        TextView routeTitle = viewHolder.routeTitle;
        routeTitle.setText(route.getTitle());

        TextView steps = viewHolder.steps;
        steps.setText(route.getActivity().getDetail(Walk.STEP_COUNT));

        TextView miles = viewHolder.miles;
        miles.setText(route.getActivity().getDetail(Walk.MILES));

        TextView duration = viewHolder.duration;
        duration.setText(route.getActivity().getDetail(Walk.DURATION));

        TextView date = viewHolder.date;
        date.setText(ActivityUtils.timeToMonthDay(
                ActivityUtils.stringToTime(route.getActivity().getDetail(Activity.DATE))));
    }

    // Returns the total count of items in the list
    @Override
    public int getItemCount() {
        return routes.getSize();
    }
}