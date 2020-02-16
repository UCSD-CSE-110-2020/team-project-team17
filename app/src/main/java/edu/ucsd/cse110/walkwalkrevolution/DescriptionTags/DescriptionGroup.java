package edu.ucsd.cse110.walkwalkrevolution.DescriptionTags;

import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;

import edu.ucsd.cse110.walkwalkrevolution.CreateRouteActivity;

public class DescriptionGroup {
    private DescriptionTagsList descriptionTagsList;
    private LinearLayout linearLayout;

    public DescriptionGroup(LinearLayout ll) {
        descriptionTagsList = new DescriptionTagsList();
        linearLayout = ll;
    }

    public void createRadioGroup() {
        int numGroups = descriptionTagsList.getSize();
        for(int i = 0; i < numGroups; i++) {
            RadioGroup tagGroup = new RadioGroup(linearLayout.getContext());
            tagGroup.setId(View.generateViewId());
            createRadios(tagGroup, descriptionTagsList.get(i));
            tagGroup.setOrientation(LinearLayout.HORIZONTAL);
            linearLayout.addView(tagGroup);
        }
    }

    public void createRadios(RadioGroup rg, DescriptionTags desTags) {
        for (int i = 0; i < desTags.getSize(); i++) {
            RadioButton tagOption = new RadioButton(rg.getContext());
            int radio_id = View.generateViewId();
            radio_id = radio_id * 10 + i;
            tagOption.setId(radio_id);
            tagOption.setText(desTags.get(i));
            tagOption.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    int tagIdx = Integer.valueOf(v.getId()) % 10;
                    Log.d("tagIdx", "tag idx: " + tagIdx );
                    desTags.selectTag(tagIdx);
                }
            });


            rg.addView(tagOption);

        }
    }

    public String getSelectedTags() {
        String tags = "";
        for(int i = 0; i < descriptionTagsList.getSize(); i++) {
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
        return tags;
    }
}
