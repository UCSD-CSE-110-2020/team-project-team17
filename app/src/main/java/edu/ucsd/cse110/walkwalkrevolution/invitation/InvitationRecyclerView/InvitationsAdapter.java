package edu.ucsd.cse110.walkwalkrevolution.invitation.InvitationRecyclerView;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import edu.ucsd.cse110.walkwalkrevolution.R;
import edu.ucsd.cse110.walkwalkrevolution.invitation.Invitation;
import edu.ucsd.cse110.walkwalkrevolution.invitation.Invitations;
import edu.ucsd.cse110.walkwalkrevolution.route.RouteRecycleView.RoutesAdapter;

public class InvitationsAdapter extends RecyclerView.Adapter<RoutesAdapter.ViewHolder> {

    private Invitations invitations;

    public InvitationsAdapter() {
        invitations = new Invitations();
        //this.context = context;
    }

    @NonNull
    @Override
    public InvitationsAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        Context context = parent.getContext();
        LayoutInflater inflater = LayoutInflater.from(context);

        View contactView = inflater.inflate(R.layout.item_user, parent, false);
        ViewHolder viewHolder = new ViewHolder(contactView);

        return viewHolder;
    }
}
