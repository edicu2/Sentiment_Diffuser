package com.example.aromind.Activity.MenuRemote_RecyclerView;

import com.github.mikephil.charting.data.PieEntry;

import java.util.List;

import static android.media.CamcorderProfile.get;

public class PieDataSetCustom extends com.github.mikephil.charting.data.PieDataSet {

    public PieDataSetCustom(List<PieEntry> yVals, String label) {
        super(yVals, label);
    }


    public int getColor(int index) {
        if(getEntryForIndex(index).getLabel() == "aroma3") // less than 95 green
            return mColors.get(2);
        else if(getEntryForIndex(index).getLabel() == "aroma2") // less than 100 orange
            return mColors.get(1);
        else if(getEntryForIndex(index).getLabel() == "aroma1") // greater or equal than 100 red
            return mColors.get(0);
        else{
            return mColors.get(3);
        }
    }

}
