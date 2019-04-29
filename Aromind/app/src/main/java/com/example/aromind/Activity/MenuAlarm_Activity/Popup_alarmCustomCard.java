package com.example.aromind.Activity.MenuAlarm_Activity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import com.eftimoff.viewpagertransformers.CubeOutTransformer;
import com.example.aromind.Activity.MainActivity;
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

public class Popup_alarmCustomCard extends AppCompatActivity implements View.OnClickListener{
    private Button btn_right, btn_left, cancle, submit;
    private SliderAdapterAlarm adapter;
    private ViewPager viewPager;
    private Custom_power_DBHelper custom_powerDB;
    private Custom_gradient_DBHelper custom_gradient_DB;
    private SharedPreferences pref;
    private ArrayList<String> title ;
    private String nowCustom_title;
    private ArrayList<int[]> itemList2 ;
    private ArrayList<ArrayList> itemList ;
    private Context context;

    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_popup_alarm_custom_card);

        pref = this.getSharedPreferences("Sentiment", 0);
        viewPager = (ViewPager) findViewById(R.id.view);
        btn_left = (Button) findViewById(R.id.customCardLeft);
        btn_left.setOnClickListener(this);
        btn_right = (Button) findViewById(R.id.customCardRight);
        btn_right.setOnClickListener(this);
        cancle = findViewById(R.id.cancle);
        cancle.setOnClickListener(this);
        submit = findViewById(R.id.submit);
        submit.setOnClickListener(this);

        custom_powerDB = new Custom_power_DBHelper(this, "custom_power", null, 1);
        custom_gradient_DB = new Custom_gradient_DBHelper(this,"custom_color", null, 1);

        context = this;

        setResult(Setting_Alarm.RESULT_NG);
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

    public void onResume() {
        super.onResume();
        itemList = new ArrayList<ArrayList>();
        itemList2 = new ArrayList<int[]>();
        title = new ArrayList<>();
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
        itemList2.add(new int[]{Color.WHITE, R.color.aroma1, R.color.aroma2, R.color.aroma3, Color.WHITE});
        title.add("New Custom Add");
        adapter = new SliderAdapterAlarm(this, context, itemList, itemList2, title);
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

    public void onClick(View v) {
//        SharedPreferences.Editor editor = pref.edit();

        if (v.getId() == R.id.customCardRight) {
            viewPager.setCurrentItem(viewPager.getCurrentItem() + 1, true);
        } else if (v.getId() == R.id.customCardLeft) {
            viewPager.setCurrentItem(viewPager.getCurrentItem() - 1, true);
        } else if(v.getId() == R.id.submit){
            Intent data = new Intent();
            nowCustom_title = adapter.getPositionCustom_name(viewPager.getCurrentItem());
            data.putExtra("card_title", nowCustom_title);
            Log.i("cardsssss", nowCustom_title);
            setResult(Setting_Alarm.RESULT_OK, data);
            finish();
        }else if(v.getId() == R.id.cancle){
            finish();
        }
    }
}
