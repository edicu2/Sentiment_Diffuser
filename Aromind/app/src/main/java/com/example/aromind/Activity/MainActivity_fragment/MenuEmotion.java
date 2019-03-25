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
import android.widget.Button;
import com.eftimoff.viewpagertransformers.CubeOutTransformer;
import com.example.aromind.Activity.MenuEmotion_ImageSlider.SliderAdapter;
import com.example.aromind.R;
import com.github.mikephil.charting.data.PieEntry;
import java.util.ArrayList;
import static android.view.View.GONE;
import static android.view.View.VISIBLE;

public class MenuEmotion extends Fragment implements View.OnClickListener {

    private SliderAdapter adapter;
    private ViewPager viewPager;
    private Button btn_right, btn_left;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.menu_emotion, container, false);
        viewPager = (ViewPager) view.findViewById(R.id.view);
        btn_left = (Button) view.findViewById(R.id.customCardLeft);
        btn_left.setOnClickListener(this);
        btn_right = (Button) view.findViewById(R.id.customCardRight);
        btn_right.setOnClickListener(this);
        return view;
    }

    boolean isFragmentLoaded = false;

    @Override
    public void setUserVisibleHint(boolean isVisibleToUser) {
        super.setUserVisibleHint(isVisibleToUser);
        if (isVisibleToUser && !isFragmentLoaded) {
            //Load Your Data Here like.... new GetContacts().execute();

            isFragmentLoaded = true;
        } else {
        }
    }


    public void onResume() {
        super.onResume();
        ArrayList<ArrayList> itemList = new ArrayList<ArrayList>();
        ArrayList<int[]> itemList2 = new ArrayList<int[]>();
        ArrayList<String> title = new ArrayList<>();
        ArrayList<PieEntry> pie1 = new ArrayList<PieEntry>();
        ArrayList<PieEntry> pie2 = new ArrayList<PieEntry>();
        ArrayList<PieEntry> pie3 = new ArrayList<PieEntry>();
        ArrayList<PieEntry> pie4 = new ArrayList<PieEntry>();
        ArrayList<PieEntry> pie5 = new ArrayList<PieEntry>();
        ArrayList<PieEntry> pie6 = new ArrayList<PieEntry>();


        pie1.add(new PieEntry(40f, "aroma1"));
        pie1.add(new PieEntry(40f, "aroma2"));
        pie1.add(new PieEntry(70f, "aroma3"));
        itemList.add(pie1);
        itemList2.add(new int[]{Color.WHITE, R.color.aroma1, R.color.aroma2, R.color.aroma3, Color.WHITE});
        title.add("Rose Temple");


        pie2.add(new PieEntry(40f, "aroma1"));
        pie2.add(new PieEntry(40f, "aroma2"));
        pie2.add(new PieEntry(40f, "aroma3"));
        itemList.add(pie2);
        itemList2.add(new int[]{Color.WHITE, Color.BLUE, Color.BLACK, Color.GREEN, Color.WHITE});
        title.add("Purple");


        pie3.add(new PieEntry(10f, "aroma1"));
        pie3.add(new PieEntry(20f, "aroma2"));
        pie3.add(new PieEntry(40f, "aroma3"));
        itemList.add(pie3);
        itemList2.add(new int[]{Color.WHITE, Color.BLUE, Color.GREEN, Color.RED, Color.WHITE});
        title.add("Rainbow");


        pie4.add(new PieEntry(30f, "aroma1"));
        pie4.add(new PieEntry(70f, "aroma2"));

        itemList.add(pie4);
        itemList2.add(new int[]{Color.WHITE, R.color.aroma1, R.color.aroma2, R.color.aroma3, Color.WHITE});
        title.add("Spectrum");

        pie5.add(new PieEntry(20f, "aroma1"));
        pie5.add(new PieEntry(20f, "aroma2"));
        pie5.add(new PieEntry(10f, "aroma3"));

        itemList.add(pie5);
        itemList2.add(new int[]{Color.WHITE, R.color.aroma1, R.color.aroma2, R.color.aroma3, Color.WHITE});
        title.add("Purple");

        itemList.add(pie6);
        itemList2.add(new int[]{Color.WHITE, R.color.aroma1, R.color.aroma2, R.color.aroma3, Color.WHITE});
        title.add("New Custom Add");


        adapter = new SliderAdapter(getContext(), itemList, itemList2, title);
        viewPager.setAdapter(adapter);
        viewPager.setPageTransformer(true, new CubeOutTransformer());
        viewPager.setCurrentItem(2);
        viewPager.setOffscreenPageLimit(4);
        sliderBtnLeftRight();
        viewPager.setOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int i, float v, int i1) { }

            @Override
            public void onPageSelected(int i) { }

            @Override
            public void onPageScrollStateChanged(int i) {
                sliderBtnLeftRight();
            }
        });
    }

    @Override
    public void onClick(View v) {
        Log.i("currentPage", toString().valueOf(viewPager.getCurrentItem()));
        if (v.getId() == R.id.customCardRight) {
            viewPager.setCurrentItem(viewPager.getCurrentItem() + 1, true);
            //viewPager.setPageTransformer(true, new CubeOutTransformer(), viewPager.getLayerType()+1);
        } else {
            viewPager.setCurrentItem(viewPager.getCurrentItem() - 1, true);
            //viewPager.setPageTransformer(true, new CubeOutTransformer(), viewPager.getLayerType()-1);
        }

    }


    public void sliderBtnLeftRight() {
        if (viewPager.getCurrentItem() == 0) {
            btn_left.setVisibility(GONE);
            btn_right.setVisibility(VISIBLE);
        } else if (viewPager.getCurrentItem() == adapter.getCount()-1) {
            btn_left.setVisibility(VISIBLE);
            btn_right.setVisibility(GONE);
        } else {
            btn_left.setVisibility(VISIBLE);
            btn_right.setVisibility(VISIBLE);
        }
    }
}