package com.example.aromind.Activity.MainActivity_fragment;

import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.constraint.ConstraintLayout;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.eftimoff.viewpagertransformers.CubeOutTransformer;
import com.example.aromind.Activity.MenuEmotion_ImageSlider.SliderAdapter;
import com.example.aromind.Activity.MenuRemote_RecyclerView.PieDataSetCustom;
import com.example.aromind.Activity.MenuRemote_RecyclerView.RecyclerViewAdapter;
import com.example.aromind.CustomView.CustomGradientButton;
import com.example.aromind.Model.Custom_gradient_DBHelper;
import com.example.aromind.Model.Custom_power_DBHelper;
import com.example.aromind.Model.Mqtt;
import com.example.aromind.R;
import com.github.mikephil.charting.animation.Easing;
import com.github.mikephil.charting.charts.PieChart;
import com.github.mikephil.charting.data.PieData;
import com.github.mikephil.charting.data.PieDataSet;
import com.github.mikephil.charting.data.PieEntry;

import org.eclipse.paho.client.mqttv3.IMqttDeliveryToken;
import org.eclipse.paho.client.mqttv3.MqttCallback;
import org.eclipse.paho.client.mqttv3.MqttMessage;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.w3c.dom.Text;


import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;

import static android.view.View.GONE;
import static android.view.View.VISIBLE;

public class MenuEmotion extends Fragment implements View.OnClickListener{

    private SliderAdapter adapter;
    private ViewPager viewPager;
    private Button btn_right, btn_left;
    private Button btn_positive, btn_neutral, btn_negative;
    private Custom_power_DBHelper custom_powerDB;
    private Custom_gradient_DBHelper custom_gradient_DB;
    private ArrayList<int[]> itemList2 ;
    private ArrayList<ArrayList> itemList ;
    private ArrayList<String> title;
    private ArrayList<Integer> bright;
    private String nowCustom_title;
    private CustomGradientButton gradient_positive,gradient_neutral, gradient_negative;
    private PieChart pieChart_positive, pieChart_neutral, pieChart_negative;
    private SharedPreferences pref;
    private TextView custom_name_positive, custom_name_neutral, custom_name_negative;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.menu_emotion, container, false);
        pref = getContext().getSharedPreferences("Sentiment", 0);
        viewPager = (ViewPager) view.findViewById(R.id.view);
        btn_left = (Button) view.findViewById(R.id.customCardLeft);
        btn_left.setOnClickListener(this);
        btn_right = (Button) view.findViewById(R.id.customCardRight);
        btn_right.setOnClickListener(this);
        btn_positive = (Button)view.findViewById(R.id.positive_add);
        btn_positive.setOnClickListener(this);
        btn_neutral = (Button)view.findViewById(R.id.neutral_add);
        btn_neutral.setOnClickListener(this);
        btn_neutral.setOnClickListener(this);
        btn_negative = (Button)view.findViewById(R.id.negative_add);
        btn_negative.setOnClickListener(this);
        pieChart_positive = (PieChart)view.findViewById(R.id.piechart_positive);
        pieChart_neutral = (PieChart)view.findViewById(R.id.piechart_neutral);
        pieChart_negative = (PieChart)view.findViewById(R.id.piechart_negative);
        gradient_positive = (CustomGradientButton) view.findViewById(R.id.gradient_positive);
        gradient_neutral = (CustomGradientButton) view.findViewById(R.id.gradient_neutral);
        gradient_negative = (CustomGradientButton) view.findViewById(R.id.gradient_negative);
        custom_name_positive = (TextView)view.findViewById(R.id.custom_name_positive);
        custom_name_neutral = (TextView)view.findViewById(R.id.custom_name_neutral);
        custom_name_negative = (TextView)view.findViewById(R.id.custom_name_negative);

        //db검색
        custom_powerDB = new Custom_power_DBHelper(getContext(), "custom_power", null, 1);
        custom_gradient_DB = new Custom_gradient_DBHelper(getContext(),"custom_color", null, 1);

        ArrayList<PieEntry> pie;
        if(pref.getString("positive",null) == null) {
            gradient_positive.setVisibility(GONE);
            pieChart_positive.setVisibility(GONE);
        }else{
            custom_name_positive.setText(pref.getString("positive",null));

            JSONObject power_object = (JSONObject)custom_powerDB.getData(pref.getString("positive",null));
            ArrayList pie1 = new ArrayList<PieEntry>();
            int[] color = null;
            try {
                pie1.add(new PieEntry(Integer.parseInt((String) power_object.get("positive")),"aroma1"));
                pie1.add(new PieEntry(Integer.parseInt((String) power_object.get("neutral")),"aroma2"));
                pie1.add(new PieEntry(Integer.parseInt((String) power_object.get("negative")),"aroma3"));
                pieChart_positive.getLegend().setEnabled(false); // 하단바 없앰
                pieChart_positive.setDrawSliceText(false);
                pieChart_positive.getDescription().setEnabled(false);
                pieChart_positive.setExtraOffsets(5,5,5,5);
                pieChart_positive.setDrawHoleEnabled(false);
                pieChart_positive.setHoleColor( getContext().getResources().getColor(R.color.trans));
                pieChart_positive.setTransparentCircleRadius(61f);
                pieChart_positive.setTouchEnabled(false);
                PieDataSet dataSet = new PieDataSetCustom(pie1 ," ");


                dataSet.setColors( getContext().getResources().getColor(R.color.aroma3),
                        getContext().getResources().getColor(R.color.aroma2),
                        getContext().getResources().getColor(R.color.aroma1),
                        getContext().getResources().getColor(R.color.trans)
                );

                dataSet.setSelectionShift(0);
                dataSet.setValueTextSize(0);
                PieData data = new PieData((dataSet));
                data.setDrawValues(false);
                pieChart_positive.setData(data);
                pieChart_positive.setTag(pie1);
                JSONArray json_db_color = custom_gradient_DB.getData((String) power_object.get("custom_name"));
                if((int)json_db_color.length() != 0) {
                    for (int j = 0; j < json_db_color.length(); j++) {
                        JSONObject color_object = (JSONObject)json_db_color.get(j);
                        if (j == 0) {
                            color = new int[json_db_color.length()+1];
                        }
                        color[j] =Integer.parseInt((String)color_object.get("color"));
                    }
                    JSONObject last = (JSONObject)json_db_color.get(0);
                    color[json_db_color.length()] = Integer.parseInt((String)last.get("color"));
                    gradient_positive.setCircleColors(color);
                }else {
                    color = null;
                    gradient_positive.setCircleColors(color);
                }
            } catch (JSONException e) {
                e.printStackTrace();
            }
            ConstraintLayout.LayoutParams params_positive = (ConstraintLayout.LayoutParams) btn_positive.getLayoutParams();
            params_positive.verticalBias= (float) 0.95;
            btn_positive.setText("change");
            btn_positive.setLayoutParams(params_positive);
            //btn_positive.setVisibility(GONE);
        }
        SharedPreferences pref = getContext().getSharedPreferences("Sentiment", 0);


        if(pref.getString("neutral",null) == null) {
            custom_powerDB.getData(pref.getString("neutral",null));
            custom_gradient_DB.getData(pref.getString("neutral",null));
            gradient_neutral.setVisibility(GONE);
            pieChart_neutral.setVisibility(GONE);
        }else{
            custom_name_neutral.setText(pref.getString("neutral",null));
            JSONObject power_object = (JSONObject)custom_powerDB.getData(pref.getString("neutral",null));
            ArrayList pie2 = new ArrayList<PieEntry>();
            int[] color = null;
            try {
                pie2.add(new PieEntry(Integer.parseInt((String) power_object.get("positive")),"aroma1"));
                pie2.add(new PieEntry(Integer.parseInt((String) power_object.get("neutral")),"aroma2"));
                pie2.add(new PieEntry(Integer.parseInt((String) power_object.get("negative")),"aroma3"));


                pieChart_neutral.getLegend().setEnabled(false); // 하단바 없앰
                pieChart_neutral.setDrawSliceText(false);
                pieChart_neutral.getDescription().setEnabled(false);
                pieChart_neutral.setExtraOffsets(5,5,5,5);
                pieChart_neutral.setDrawHoleEnabled(false);
                pieChart_neutral.setHoleColor( getContext().getResources().getColor(R.color.trans));
                pieChart_neutral.setTransparentCircleRadius(61f);
                pieChart_neutral.setTouchEnabled(false);
                PieDataSet dataSet = new PieDataSetCustom(pie2 ," ");


                dataSet.setColors( getContext().getResources().getColor(R.color.aroma3),
                        getContext().getResources().getColor(R.color.aroma2),
                        getContext().getResources().getColor(R.color.aroma1),
                        getContext().getResources().getColor(R.color.trans)
                );

                dataSet.setSelectionShift(0);
                dataSet.setValueTextSize(0);
                PieData data = new PieData((dataSet));
                data.setDrawValues(false);

                pieChart_neutral.setData(data);
                pieChart_neutral.setTag(pie2);
                JSONArray json_db_color = custom_gradient_DB.getData((String) power_object.get("custom_name"));
                if((int)json_db_color.length() != 0) {
                    for (int j = 0; j < json_db_color.length(); j++) {
                        JSONObject color_object = (JSONObject)json_db_color.get(j);
                        if (j == 0) {
                            color = new int[json_db_color.length()+1];
                        }
                        color[j] =Integer.parseInt((String)color_object.get("color"));
                    }
                    JSONObject last = (JSONObject)json_db_color.get(0);
                    color[json_db_color.length()] = Integer.parseInt((String)last.get("color"));
                    gradient_neutral.setCircleColors(color);
                }else {
                    color = null;
                    gradient_neutral.setCircleColors(color);
                }
            } catch (JSONException e) {
                e.printStackTrace();
            }
            ConstraintLayout.LayoutParams params_neutral = (ConstraintLayout.LayoutParams) btn_neutral.getLayoutParams();
            params_neutral.verticalBias= (float) 0.95 ;
            btn_neutral.setText("change");
            btn_neutral.setLayoutParams(params_neutral);

        }
        if(pref.getString("negative",null) == null) {
            gradient_negative.setVisibility(GONE);
            pieChart_negative.setVisibility(GONE);
        }else{
            custom_name_negative.setText(pref.getString("negative",null));
            JSONObject power_object = (JSONObject)custom_powerDB.getData(pref.getString("negative",null));
            ArrayList pie3 = new ArrayList<PieEntry>();
            int[] color = null;
            try {
                pie3.add(new PieEntry(Integer.parseInt((String) power_object.get("positive")),"aroma1"));
                pie3.add(new PieEntry(Integer.parseInt((String) power_object.get("neutral")),"aroma2"));
                pie3.add(new PieEntry(Integer.parseInt((String) power_object.get("negative")),"aroma3"));
                pieChart_negative.getLegend().setEnabled(false); // 하단바 없앰
                pieChart_negative.setDrawSliceText(false);
                pieChart_negative.getDescription().setEnabled(false);
                pieChart_negative.setExtraOffsets(5,5,5,5);
                pieChart_negative.setDrawHoleEnabled(false);
                pieChart_negative.setHoleColor( getContext().getResources().getColor(R.color.trans));
                pieChart_negative.setTransparentCircleRadius(61f);
                pieChart_negative.setTouchEnabled(false);
                PieDataSet dataSet = new PieDataSetCustom(pie3 ," ");


                dataSet.setColors( getContext().getResources().getColor(R.color.aroma3),
                        getContext().getResources().getColor(R.color.aroma2),
                        getContext().getResources().getColor(R.color.aroma1),
                        getContext().getResources().getColor(R.color.trans)
                );

                dataSet.setSelectionShift(0);
                dataSet.setValueTextSize(0);
                PieData data = new PieData((dataSet));
                data.setDrawValues(false);

                pieChart_negative.setData(data);
                pieChart_negative.setTag(pie3);

                JSONArray json_db_color = custom_gradient_DB.getData((String) power_object.get("custom_name"));
                if((int)json_db_color.length() != 0) {
                    for (int j = 0; j < json_db_color.length(); j++) {
                        JSONObject color_object = (JSONObject)json_db_color.get(j);
                        if (j == 0) {
                            color = new int[json_db_color.length()+1];
                        }
                        color[j] =Integer.parseInt((String)color_object.get("color"));
                    }
                    JSONObject last = (JSONObject)json_db_color.get(0);
                    color[json_db_color.length()] = Integer.parseInt((String)last.get("color"));
                    gradient_negative.setCircleColors(color);
                }else {
                    color = null;
                    gradient_negative.setCircleColors(color);
                }
            } catch (JSONException e) {
                e.printStackTrace();
            }
            ConstraintLayout.LayoutParams params_negative = (ConstraintLayout.LayoutParams) btn_negative.getLayoutParams();
            params_negative.verticalBias = (float) 0.95;
            btn_negative.setText("change");
            btn_negative.setLayoutParams(params_negative);
        }

        return view;
    }

    public void onResume() {
        super.onResume();
        itemList = new ArrayList<ArrayList>();
        itemList2 = new ArrayList<int[]>();
        title = new ArrayList<>();
        bright = new ArrayList<>();
        ArrayList<PieEntry> pie;
        int custom_power_DB_size = (int)custom_powerDB.getDataSize();
        JSONArray json_db = custom_powerDB.getRecentData(custom_power_DB_size);
        int[] color = null;
        for(int i=0 ; i < custom_power_DB_size  ; i++){
            try {
                JSONObject power_object = (JSONObject) json_db.get(i);
                pie = new ArrayList<PieEntry>();
                pie.add(new PieEntry(Integer.parseInt((String) power_object.get("positive")),"aroma1"));
                pie.add(new PieEntry(Integer.parseInt((String) power_object.get("neutral")),"aroma2"));
                pie.add(new PieEntry(Integer.parseInt((String) power_object.get("negative")),"aroma3"));
                itemList.add(pie);
                title.add((String) power_object.get("custom_name"));
                bright.add(Integer.parseInt((String)power_object.get("bright")));
                JSONArray json_db_color = custom_gradient_DB.getData((String) power_object.get("custom_name"));
                if((int)json_db_color.length() != 0) {
                    for (int j = 0; j < json_db_color.length(); j++) {
                        JSONObject color_object = (JSONObject)json_db_color.get(j);
                        if (j == 0) {
                            color = new int[json_db_color.length()+1];
                        }
                        color[j] =Integer.parseInt((String)color_object.get("color"));
                    }
                    JSONObject last = (JSONObject)json_db_color.get(0);
                    color[json_db_color.length()] = Integer.parseInt((String)last.get("color"));
                    itemList2.add(color);
                }else {
                    color = null;
                    itemList2.add(color);
                }
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
        pie = new ArrayList<>();
        itemList.add(pie);
        itemList2.add(new int[]{Color.WHITE,
                R.color.aroma3,
                R.color.aroma2,
                R.color.aroma1,
                Color.WHITE});
        title.add("New Custom Add");
        bright.add(0);

        adapter = new SliderAdapter(this, getContext(), itemList, itemList2, title, bright);
        viewPager.setAdapter(adapter);
        viewPager.setPageTransformer(true, new CubeOutTransformer());
        sliderBtnLeftRight();
        viewPager.setOffscreenPageLimit(6);
        viewPager.setOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int i, float v, int i1) { }

            @Override
            public void onPageSelected(int i) {

            }
            @Override
            public void onPageScrollStateChanged(int i) {
                Log.i("nowPage", String.valueOf(adapter.getPositionCustom_name(viewPager.getCurrentItem())));
                sliderBtnLeftRight();
            }
        });
        nowCustom_title = adapter.getPositionCustom_name(viewPager.getCurrentItem());
    }


    @Override
    public void onClick(View v) {
        SharedPreferences.Editor editor = pref.edit();
        nowCustom_title = adapter.getPositionCustom_name(viewPager.getCurrentItem());
        if (v.getId() == R.id.customCardRight) {
            viewPager.setCurrentItem(viewPager.getCurrentItem() + 1, true);
        }else if(v.getId() == R.id.customCardLeft) {
            viewPager.setCurrentItem(viewPager.getCurrentItem() - 1, true);
        }else if(v.getId() == R.id.positive_add){
            if(!nowCustom_title.equals("New Custom Add")) {
                editor.putString("positive", nowCustom_title);
                editor.apply();
                ArrayList<PieEntry> pie;
                if (pref.getString("positive", null) == null) {
                    gradient_positive.setVisibility(GONE);
                    pieChart_positive.setVisibility(GONE);
                } else {
                    custom_name_positive.setText(pref.getString("positive", null));
                    JSONObject power_object = (JSONObject) custom_powerDB.getData(pref.getString("positive", null));
                    ArrayList pie1 = new ArrayList<PieEntry>();
                    int[] color = null;
                    try {
                        pie1.add(new PieEntry(Integer.parseInt((String) power_object.get("positive")), "aroma1"));
                        pie1.add(new PieEntry(Integer.parseInt((String) power_object.get("neutral")), "aroma2"));
                        pie1.add(new PieEntry(Integer.parseInt((String) power_object.get("negative")), "aroma3"));
                        pieChart_positive.getLegend().setEnabled(false); // 하단바 없앰
                        pieChart_positive.setDrawSliceText(false);
                        pieChart_positive.getDescription().setEnabled(false);
                        pieChart_positive.setExtraOffsets(5, 5, 5, 5);
                        pieChart_positive.setDrawHoleEnabled(false);
                        pieChart_positive.setHoleColor(getContext().getResources().getColor(R.color.trans));
                        pieChart_positive.setTransparentCircleRadius(61f);
                        pieChart_positive.setTouchEnabled(false);
                        PieDataSet dataSet = new PieDataSetCustom(pie1, " ");


                        dataSet.setColors(getContext().getResources().getColor(R.color.aroma3),
                                getContext().getResources().getColor(R.color.aroma2),
                                getContext().getResources().getColor(R.color.aroma1),
                                getContext().getResources().getColor(R.color.trans)
                        );

                        dataSet.setSelectionShift(0);
                        dataSet.setValueTextSize(0);
                        PieData data = new PieData((dataSet));
                        data.setDrawValues(false);
                        pieChart_positive.setData(data);
                        pieChart_positive.setTag(pie1);
                        pieChart_positive.animateY(1000, Easing.EasingOption.EaseInOutCubic);
                        pieChart_positive.invalidate();
                        JSONArray json_db_color = custom_gradient_DB.getData((String) power_object.get("custom_name"));
                        if ((int) json_db_color.length() != 0) {
                            for (int j = 0; j < json_db_color.length(); j++) {
                                JSONObject color_object = (JSONObject) json_db_color.get(j);
                                if (j == 0) {
                                    color = new int[json_db_color.length() + 1];
                                }
                                color[j] = Integer.parseInt((String) color_object.get("color"));
                            }
                            JSONObject last = (JSONObject) json_db_color.get(0);
                            color[json_db_color.length()] = Integer.parseInt((String) last.get("color"));
                            gradient_positive.setCircleColors(color);
                        } else {
                            color = null;
                            gradient_positive.setCircleColors(color);
                        }
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                    ConstraintLayout.LayoutParams params_positive = (ConstraintLayout.LayoutParams) btn_positive.getLayoutParams();
                    params_positive.verticalBias = (float) 0.95;
                    btn_positive.setText("change");
                    btn_positive.setLayoutParams(params_positive);
                    //btn_positive.setVisibility(GONE);
                }
            }
        }else if(v.getId() == R.id.neutral_add){
            if(!nowCustom_title.equals("New Custom Add")) {
                editor.putString("neutral", nowCustom_title);
                editor.apply();
                ArrayList<PieEntry> pie;
                if (pref.getString("neutral", null) == null) {
                    gradient_neutral.setVisibility(GONE);
                    pieChart_neutral.setVisibility(GONE);
                } else {
                    custom_name_neutral.setText(pref.getString("neutral", null));
                    JSONObject power_object = (JSONObject) custom_powerDB.getData(pref.getString("neutral", null));
                    ArrayList pie1 = new ArrayList<PieEntry>();
                    int[] color = null;
                    try {
                        pie1.add(new PieEntry(Integer.parseInt((String) power_object.get("positive")), "aroma1"));
                        pie1.add(new PieEntry(Integer.parseInt((String) power_object.get("neutral")), "aroma2"));
                        pie1.add(new PieEntry(Integer.parseInt((String) power_object.get("negative")), "aroma3"));
                        pieChart_neutral.getLegend().setEnabled(false); // 하단바 없앰
                        pieChart_neutral.setDrawSliceText(false);
                        pieChart_neutral.getDescription().setEnabled(false);
                        pieChart_neutral.setExtraOffsets(5, 5, 5, 5);
                        pieChart_neutral.setDrawHoleEnabled(false);
                        pieChart_neutral.setHoleColor(getContext().getResources().getColor(R.color.trans));
                        pieChart_neutral.setTransparentCircleRadius(61f);
                        pieChart_neutral.setTouchEnabled(false);
                        PieDataSet dataSet = new PieDataSetCustom(pie1, " ");


                        dataSet.setColors(getContext().getResources().getColor(R.color.aroma3),
                                getContext().getResources().getColor(R.color.aroma2),
                                getContext().getResources().getColor(R.color.aroma1),
                                getContext().getResources().getColor(R.color.trans)
                        );

                        dataSet.setSelectionShift(0);
                        dataSet.setValueTextSize(0);
                        PieData data = new PieData((dataSet));
                        data.setDrawValues(false);
                        pieChart_neutral.setData(data);
                        pieChart_neutral.setTag(pie1);
                        pieChart_neutral.animateY(1000, Easing.EasingOption.EaseInOutCubic);
                        pieChart_neutral.invalidate();
                        JSONArray json_db_color = custom_gradient_DB.getData((String) power_object.get("custom_name"));
                        if ((int) json_db_color.length() != 0) {
                            for (int j = 0; j < json_db_color.length(); j++) {
                                JSONObject color_object = (JSONObject) json_db_color.get(j);
                                if (j == 0) {
                                    color = new int[json_db_color.length() + 1];
                                }
                                color[j] = Integer.parseInt((String) color_object.get("color"));
                            }
                            JSONObject last = (JSONObject) json_db_color.get(0);
                            color[json_db_color.length()] = Integer.parseInt((String) last.get("color"));
                            gradient_neutral.setCircleColors(color);
                        } else {
                            color = null;
                            gradient_neutral.setCircleColors(color);
                        }
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                    ConstraintLayout.LayoutParams params_neutral = (ConstraintLayout.LayoutParams) btn_neutral.getLayoutParams();
                    params_neutral.verticalBias = (float) 0.95;
                    btn_neutral.setText("change");
                    btn_neutral.setLayoutParams(params_neutral);
                }
            }
        }else if(v.getId() == R.id.negative_add){
            if(!nowCustom_title.equals("New Custom Add")) {
                editor.putString("negative", nowCustom_title);
                editor.apply();
                ArrayList<PieEntry> pie;
                if (pref.getString("negative", null) == null) {
                    gradient_negative.setVisibility(GONE);
                    pieChart_negative.setVisibility(GONE);
                } else {
                    custom_name_negative.setText(pref.getString("negative", null));
                    JSONObject power_object = (JSONObject) custom_powerDB.getData(pref.getString("negative", null));
                    ArrayList pie1 = new ArrayList<PieEntry>();
                    int[] color = null;
                    try {
                        pie1.add(new PieEntry(Integer.parseInt((String) power_object.get("positive")), "aroma1"));
                        pie1.add(new PieEntry(Integer.parseInt((String) power_object.get("neutral")), "aroma2"));
                        pie1.add(new PieEntry(Integer.parseInt((String) power_object.get("negative")), "aroma3"));
                        pieChart_negative.getLegend().setEnabled(false); // 하단바 없앰
                        pieChart_negative.setDrawSliceText(false);
                        pieChart_negative.getDescription().setEnabled(false);
                        pieChart_negative.setExtraOffsets(5, 5, 5, 5);
                        pieChart_negative.setDrawHoleEnabled(false);
                        pieChart_negative.setHoleColor(getContext().getResources().getColor(R.color.trans));
                        pieChart_negative.setTransparentCircleRadius(61f);
                        pieChart_negative.setTouchEnabled(false);
                        PieDataSet dataSet = new PieDataSetCustom(pie1, " ");


                        dataSet.setColors(getContext().getResources().getColor(R.color.aroma3),
                                getContext().getResources().getColor(R.color.aroma2),
                                getContext().getResources().getColor(R.color.aroma1),
                                getContext().getResources().getColor(R.color.trans)
                        );

                        dataSet.setSelectionShift(0);
                        dataSet.setValueTextSize(0);
                        PieData data = new PieData((dataSet));
                        data.setDrawValues(false);
                        pieChart_negative.setData(data);
                        pieChart_negative.setTag(pie1);
                        pieChart_negative.animateY(1000, Easing.EasingOption.EaseInOutCubic);
                        pieChart_negative.invalidate();
                        JSONArray json_db_color = custom_gradient_DB.getData((String) power_object.get("custom_name"));
                        if ((int) json_db_color.length() != 0) {
                            for (int j = 0; j < json_db_color.length(); j++) {
                                JSONObject color_object = (JSONObject) json_db_color.get(j);
                                if (j == 0) {
                                    color = new int[json_db_color.length() + 1];
                                }
                                color[j] = Integer.parseInt((String) color_object.get("color"));
                            }
                            JSONObject last = (JSONObject) json_db_color.get(0);
                            color[json_db_color.length()] = Integer.parseInt((String) last.get("color"));
                            gradient_negative.setCircleColors(color);
                        } else {
                            color = null;
                            gradient_negative.setCircleColors(color);
                        }
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                    ConstraintLayout.LayoutParams params_negative = (ConstraintLayout.LayoutParams) btn_negative.getLayoutParams();
                    params_negative.verticalBias = (float) 0.95;
                    btn_negative.setText("change");
                    btn_negative.setLayoutParams(params_negative);
                }
            }
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

    boolean isFragmentLoaded = false;
    @Override
    public void setUserVisibleHint(boolean isVisibleToUser) {
        super.setUserVisibleHint(isVisibleToUser);
        if (isVisibleToUser && !isFragmentLoaded) {

            isFragmentLoaded = true;
        } else {
        }
    }

}