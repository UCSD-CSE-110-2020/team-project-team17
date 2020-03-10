package edu.ucsd.cse110.walkwalkrevolution.proposal.adapter;

import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class AcceptedRVAdapter extends RecyclerView.Adapter<AcceptedRVAdapter.AcceptViewHolder> {


    public AcceptedRVAdapter() {

    }

    public class AcceptViewHolder extends RecyclerView.ViewHolder {

        public AcceptViewHolder(@NonNull View itemView) {
            super(itemView);
        }
    }
    public AcceptedRVAdapter.AcceptViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return null;
    }

    @Override
    public void onBindViewHolder(@NonNull AcceptedRVAdapter.AcceptViewHolder holder, int position) {

    }

    @Override
    public int getItemCount() {
        return 0;
    }
}
