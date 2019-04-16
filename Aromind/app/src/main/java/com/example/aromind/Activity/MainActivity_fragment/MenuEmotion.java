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
import com.example.aromind.Model.Custom_gradient_DBHelper;
import com.example.aromind.Model.Custom_power_DBHelper;
import com.example.aromind.R;
import com.github.mikephil.charting.data.PieEntry;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import static android.view.View.GONE;
import static android.view.View.VISIBLE;

public class MenuEmotion extends Fragment implements View.OnClickListener {

    private SliderAdapter adapter;
    private ViewPager viewPager;
    private Button btn_right, btn_left;

    private Custom_power_DBHelper custom_powerDB;
    private Custom_gradient_DBHelper custom_gradient_DB;
    private ArrayList<int[]> itemList2 ;
    private ArrayList<ArrayList> itemList ;
    private ArrayList<String> title ;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.menu_emotion, container, false);
        viewPager = (ViewPager) view.findViewById(R.id.view);
        btn_left = (Button) view.findViewById(R.id.customCardLeft);
        btn_left.setOnClickListener(this);
        btn_right = (Button) view.findViewById(R.id.customCardRight);
        btn_right.setOnClickListener(this);
        custom_powerDB = new Custom_power_DBHelper(getContext(), "custom_power", null, 1);
        custom_gradient_DB = new Custom_gradient_DBHelper(getContext(),"custom_color", null, 1);
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
        itemList = new ArrayList<ArrayList>();
        itemList2 = new ArrayList<int[]>();
        title = new ArrayList<>();
        ArrayList<PieEntry> pie;
        int custom_power_DB_size = (int)custom_powerDB.getDataSize();
        JSONArray json_db = custom_powerDB.getRecentData(custom_power_DB_size);

        int[] color = null;
        Log.i("custom_power_size", String.valueOf(custom_power_DB_size));
        for(int i=0 ; i < custom_power_DB_size  ; i++){
            try {
                JSONObject power_object = (JSONObject) json_db.get(i);
                pie = new ArrayList<PieEntry>();
                pie.add(new PieEntry(Integer.parseInt((String) power_object.get("positive")),"aroma1"));
                pie.add(new PieEntry(Integer.parseInt((String) power_object.get("neutral")),"aroma2"));
                pie.add(new PieEntry(Integer.parseInt((String) power_object.get("negative")),"aroma3"));
                itemList.add(pie);
                title.add((String) power_object.get("custom_name"));
                JSONArray json_db_color = custom_gradient_DB.getData((String) power_object.get("custom_name"));
                Log.i("custom_color_size", String.valueOf(json_db_color.length()));
                if(json_db_color.length() != 0) {
                    for (int j = 0; j < json_db_color.length(); j++) {
                        JSONObject color_object = (JSONObject)json_db_color.get(j);
                        Log.i("colors", (String) color_object.get("color"));
                        if (j == 0) {
                            color = new int[json_db_color.length()+1];
                        }
                        color[j] =Integer.parseInt((String)color_object.get("color"));
                        Log.i("color_teki", String.valueOf(color[j]));
                    }
                    JSONObject last = (JSONObject)json_db_color.get(0);
                    color[json_db_color.length()] = Integer.parseInt((String)last.get("color"));
                    itemList2.add(color);
                }else{
                    itemList2.add(new int[]{Color.WHITE,Color.BLACK,Color.WHITE});
                    Log.i("custom_color", "null");
                }
                Log.i("colors", String.valueOf(itemList2));
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
        pie = new ArrayList<>();
        itemList.add(pie);
        itemList2.add(new int[]{Color.WHITE, R.color.aroma1, R.color.aroma2, R.color.aroma3, Color.WHITE});
        title.add("New Custom Add");


        adapter = new SliderAdapter(this, getContext(), itemList, itemList2, title);
        viewPager.setAdapter(adapter);
        viewPager.setPageTransformer(true, new CubeOutTransformer());
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