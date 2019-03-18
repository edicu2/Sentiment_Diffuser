package com.example.aromind.Activity.MainActivity_fragment;

import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.example.aromind.Activity.MenuEmotion_ImageSlider.SliderAdapter;
import com.example.aromind.Activity.MenuRemote_RecyclerView.RecyclerViewAdapter;
import com.example.aromind.R;
import com.github.mikephil.charting.data.PieEntry;

import java.util.ArrayList;

public class MenuEmotion extends Fragment {

    private SliderAdapter adapter;
    ViewPager viewPager;
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.menu_emotion, container, false);
        viewPager = (ViewPager) view.findViewById(R.id.view);


        return view;
    }

    public void onResume() {
        super.onResume();
        ArrayList<ArrayList> itemList = new ArrayList<ArrayList>();
        ArrayList<int[]> itemList2 = new ArrayList<int[]>();
        ArrayList<PieEntry> pie1 = new ArrayList<PieEntry>();
        ArrayList<PieEntry> pie2 = new ArrayList<PieEntry>();
        ArrayList<PieEntry> pie3 = new ArrayList<PieEntry>();
        ArrayList<PieEntry> pie4 = new ArrayList<PieEntry>();
        ArrayList<PieEntry> pie5 = new ArrayList<PieEntry>();
        ArrayList<PieEntry> pie6 = new ArrayList<PieEntry>();


        pie1.add(new PieEntry(40f,"color"));
        pie1.add(new PieEntry(40f,"aroma1"));
        pie1.add(new PieEntry(40f,"aroma2"));

        itemList.add(pie1);
        itemList2.add(new int[]{Color.WHITE,R.color.aroma1,R.color.aroma2,R.color.aroma3,Color.WHITE});


        pie2.add(new PieEntry(40f,"color"));
        pie2.add(new PieEntry(40f,"aroma1"));
        pie2.add(new PieEntry(40f,"aroma3"));
        itemList.add(pie2);
        itemList2.add(new int[]{Color.WHITE,Color.BLUE, Color.BLACK, Color.GREEN,Color.WHITE});

        pie3.add(new PieEntry(40f,"color"));
        pie3.add(new PieEntry(40f,"aroma1"));
        pie3.add(new PieEntry(40f,"aroma2"));
        pie3.add(new PieEntry(40f,"aroma3"));
        itemList.add(pie3);
        itemList2.add(new int[]{Color.WHITE,Color.BLUE, Color.GREEN, Color.RED,Color.WHITE});

        adapter = new SliderAdapter(getContext(),itemList , itemList2);
        viewPager.setAdapter(adapter);
    }
}