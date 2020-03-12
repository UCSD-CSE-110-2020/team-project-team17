package edu.ucsd.cse110.walkwalkrevolution.proposal.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import edu.ucsd.cse110.walkwalkrevolution.R;
import edu.ucsd.cse110.walkwalkrevolution.RoutesDetailActivity;
import edu.ucsd.cse110.walkwalkrevolution.proposal.ProposalObserver;
import edu.ucsd.cse110.walkwalkrevolution.route.Route;
import edu.ucsd.cse110.walkwalkrevolution.user.User;

public class AcceptedRVAdapter extends RecyclerView.Adapter<AcceptedRVAdapter.AcceptViewHolder> {

    private List<String> uList;

    public AcceptedRVAdapter() {
        uList = new ArrayList<>();
    }

    public class AcceptViewHolder extends RecyclerView.ViewHolder {

        public TextView userName;

        public AcceptViewHolder(@NonNull View itemView) {
            super(itemView);
            userName = (TextView) itemView.findViewById(R.id.user_NAME);
        }
    }
    public AcceptedRVAdapter.AcceptViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        Context context = parent.getContext();
        LayoutInflater inflater = LayoutInflater.from(context);
        View tile = inflater.inflate(R.layout.item_user_team, parent, false);
        AcceptViewHolder viewHolder = new AcceptViewHolder(tile);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull AcceptedRVAdapter.AcceptViewHolder holder, int position) {
        String userN = uList.get(position);
        TextView name = holder.userName;
        name.setText(userN);
    }

    @Override
    public int getItemCount() {
        return uList.size();
    }

    public void update(Map<String, String> responses) {
        uList = new ArrayList<>();
        Map<String, String> map = responses;
        for (Map.Entry<String, String> entry : map.entrySet()) {
            if(entry.getValue().equals("ACCEPTED")) {
                uList.add(entry.getKey());
            }
        }
        notifyDataSetChanged();
    }
}
