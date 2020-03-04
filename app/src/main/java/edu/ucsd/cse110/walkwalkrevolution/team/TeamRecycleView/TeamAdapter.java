package edu.ucsd.cse110.walkwalkrevolution.team.TeamRecycleView;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

import edu.ucsd.cse110.walkwalkrevolution.R;
import edu.ucsd.cse110.walkwalkrevolution.team.Team;
import edu.ucsd.cse110.walkwalkrevolution.team.TeamObserver;
import edu.ucsd.cse110.walkwalkrevolution.user.User;

public class TeamAdapter extends RecyclerView.Adapter<TeamAdapter.ViewHolder> implements TeamObserver {

    private Team team;
    private List<User> mUsers;

    public TeamAdapter(Team mTeam){
        this.team = mTeam;
        team.subscribe(this);
        mUsers = new ArrayList<>();
    }

    @NonNull
    @Override
    public TeamAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        Context context = parent.getContext();
        LayoutInflater inflater = LayoutInflater.from(context);

        View contactView = inflater.inflate(R.layout.item_user, parent, false);
        ViewHolder viewHolder = new ViewHolder(contactView);

        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull TeamAdapter.ViewHolder viewHolder, int position) {
        User user = mUsers.get(position);

        TextView nameView = viewHolder.nameField;
        TextView emailView = viewHolder.emailField;

        nameView.setText(user.getName());
        emailView.setText(user.getEmail());
    }

    @Override
    public int getItemCount() {
        return mUsers.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        public TextView nameField;
        public TextView emailField;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            nameField = itemView.findViewById(R.id.user_name);
            emailField = itemView.findViewById(R.id.user_email);
        }
    }

    public void update(List<User> users){
        mUsers = users;
        notifyDataSetChanged();
    }
}
