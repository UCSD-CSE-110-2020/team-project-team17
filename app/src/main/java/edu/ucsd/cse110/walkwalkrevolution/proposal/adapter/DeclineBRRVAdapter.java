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

public class DeclineBRRVAdapter extends RecyclerView.Adapter< DeclineBRRVAdapter.DBRViewHolder>{
    private List<String> uList;

    public DeclineBRRVAdapter() {
        uList = new ArrayList<>();
    }

    public class DBRViewHolder extends RecyclerView.ViewHolder {

        public TextView userName;

        public DBRViewHolder(@NonNull View itemView) {
            super(itemView);
            userName = (TextView) itemView.findViewById(R.id.user_NAME);
        }
    }
    public DeclineBRRVAdapter.DBRViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        Context context = parent.getContext();
        LayoutInflater inflater = LayoutInflater.from(context);

        View tile = inflater.inflate(R.layout.item_user_team, parent, false);
        DeclineBRRVAdapter.DBRViewHolder viewHolder = new DeclineBRRVAdapter.DBRViewHolder(tile);

        return viewHolder;

    }

    @Override
    public void onBindViewHolder(@NonNull DeclineBRRVAdapter.DBRViewHolder holder, int position) {
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
            if(entry.getValue().equals("DECLINE_BAD_ROUTE")) {
                uList.add(entry.getKey());
            }
        }
        notifyDataSetChanged();
    }
}
