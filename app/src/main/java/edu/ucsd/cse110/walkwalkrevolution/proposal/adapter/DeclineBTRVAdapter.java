package edu.ucsd.cse110.walkwalkrevolution.proposal.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import edu.ucsd.cse110.walkwalkrevolution.R;

public class DeclineBTRVAdapter extends RecyclerView.Adapter<DeclineBTRVAdapter.DBTViewHolder>{

    private List<String> uList;

    public DeclineBTRVAdapter() {
        uList = new ArrayList<>();
    }

    public class DBTViewHolder extends RecyclerView.ViewHolder {

        public TextView userName;

        public DBTViewHolder(@NonNull View itemView) {
            super(itemView);

            userName = (TextView) itemView.findViewById(R.id.user_NAME);

        }
    }
    public DeclineBTRVAdapter.DBTViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        Context context = parent.getContext();
        LayoutInflater inflater = LayoutInflater.from(context);

        View tile = inflater.inflate(R.layout.item_user_team, parent, false);

        DeclineBTRVAdapter.DBTViewHolder viewHolder = new DeclineBTRVAdapter.DBTViewHolder(tile);

        return viewHolder;

    }

    @Override
    public void onBindViewHolder(@NonNull DeclineBTRVAdapter.DBTViewHolder holder, int position) {
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
            if(entry.getValue().equals("DECLINE_BAD_TIME")) {
                uList.add(entry.getKey());
            }
        }
        notifyDataSetChanged();
    }
}
