package edu.ucsd.cse110.walkwalkrevolution.DescriptionTags;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;

import androidx.recyclerview.widget.RecyclerView;

import edu.ucsd.cse110.walkwalkrevolution.R;

public class DescriptionTagsListAdapter
        extends RecyclerView.Adapter<DescriptionTagsListAdapter.ViewHolder> {
    private DescriptionTagsList descriptionTagsList;
    public DescriptionTagsListAdapter() {
        descriptionTagsList = new DescriptionTagsList();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        public RadioGroup descriptionTag;

        public ViewHolder(View itemView) {
            // Stores the itemView in a public final member variable that can be used
            // to access the context from any ViewHolder instance.
            super(itemView);
            descriptionTag = itemView.findViewById(R.id.tag_group);
        }
    }

    @Override
    public DescriptionTagsListAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        Context context = parent.getContext();
        LayoutInflater inflater = LayoutInflater.from(context);

        // Inflate the custom layout
        View contactView = inflater.inflate(R.layout.item_tag, parent, false);

        // Return a new holder instance
        ViewHolder viewHolder = new ViewHolder(contactView);
        return viewHolder;
    }

    // Involves populating data into the item through holder
    @Override
    public void onBindViewHolder(DescriptionTagsListAdapter.ViewHolder viewHolder, int position) {
        // Get the data model based on position
        Log.d("des-Check Pos", "pos: " + position);
        DescriptionTags descTags = descriptionTagsList.get(position);

        // Set item views based on your views and data model
        viewHolder.descriptionTag.setOrientation(LinearLayout.HORIZONTAL);
        int numTags = descTags.getSize();

        for (int i = 0; i < numTags; i++) {
            RadioButton tagOption = new RadioButton(viewHolder.descriptionTag.getContext());
            int radio_id = View.generateViewId();
            radio_id = radio_id * 10 + i;
            tagOption.setId(radio_id);
            tagOption.setText(descTags.get(i));
            tagOption.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    int tagIdx = Integer.valueOf(v.getId()) % 10;
                    Log.d("tagIdx", "tag idx: " + tagIdx );
                    descTags.selectTag(tagIdx);
                }
            });


            viewHolder.descriptionTag.addView(tagOption);

        }

    }

    // Returns the total count of items in the list
    @Override
    public int getItemCount() {
        return descriptionTagsList.getSize();
    }

    public void updateList() {
       descriptionTagsList = new DescriptionTagsList();
       notifyDataSetChanged();
    }

    public String getSelectedTags() {
        String tags = "";
        int i;
        for(i = 0; i < descriptionTagsList.getSize(); i++) {
            String sTag = descriptionTagsList.get(i).getSelectedTag();
            if (!sTag.equals("")) {
                if (tags.equals("")) {
                    tags = sTag;
                }
                else {
                    tags += "," + sTag;
                }
            }
        }
        //i++;
        //tags +=  descriptionTagsList.get(i).getSelectedTag();

        return tags;
    }
}
