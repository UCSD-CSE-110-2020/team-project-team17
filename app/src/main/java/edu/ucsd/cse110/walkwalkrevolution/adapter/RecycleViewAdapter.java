package edu.ucsd.cse110.walkwalkrevolution.adapter;

import android.content.Context;
import android.util.SparseBooleanArray;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

import edu.ucsd.cse110.walkwalkrevolution.R;
import edu.ucsd.cse110.walkwalkrevolution.model.Item;

class Tile extends RecyclerView.ViewHolder {

    public TextView title;

    public Tile(@NonNull View itemView) {
        super(itemView);
        title = (TextView) itemView.findViewById(R.id.title);
    }
}


public class RecycleViewAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    List<Item> items;
    Context context;

    public RecycleViewAdapter(List<Item> items) {
        this.items = items;

    }

   /* @Override
    public int getItemViewType(int position) {
        if()
    }
*/
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        this.context = parent.getContext();
        //if(viewType == 0)

        LayoutInflater inflater = LayoutInflater.from(context);
        View view = inflater.inflate(R.layout.layout_tile,parent,false);
        return new Tile(view);
    }

    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        //super.onBindViewHolder(holder, position);
        Tile t = (Tile)holder;
        Item i = items.get(position);
        t.setIsRecyclable(false);
        t.title.setText(i.getTitle());
    }

    public int getItemCount() {
        return items.size();
    }
}
