package com.example.aromind.Activity.MenuEmotion_ImageSlider;


import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.support.constraint.ConstraintLayout;
import android.support.v4.view.PagerAdapter;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.example.aromind.Activity.CustomAddActivity;
import com.example.aromind.Activity.LoadingActivity;
import com.example.aromind.Activity.MainActivity;
import com.example.aromind.Activity.MainActivity_fragment.MenuEmotion;
import com.example.aromind.Activity.MenuRemote_RecyclerView.PieDataSetCustom;
import com.example.aromind.CustomView.CustomGradientCardButton;
import com.example.aromind.Model.Custom_gradient_DBHelper;
import com.example.aromind.Model.Custom_power_DBHelper;
import com.example.aromind.R;
import com.github.mikephil.charting.charts.PieChart;
import com.github.mikephil.charting.data.PieData;
import com.github.mikephil.charting.data.PieDataSet;
import com.github.mikephil.charting.data.PieEntry;

import org.json.JSONException;
import org.w3c.dom.Text;

import java.util.ArrayList;

public class SliderAdapter extends PagerAdapter{


    private int[] images = {R.drawable.total_power,R.drawable.total_power,R.drawable.total_power};
    private ArrayList<ArrayList> itemList ;
    private ArrayList<int[]> itemList2;
    private ArrayList<String> title;
    private LayoutInflater inflater;
    private Context context;
    private PieChart pieChart;
    private CustomGradientCardButton gradient;
    private TextView tvTitle;
    private TextView a_btn1, a_btn2, a_btn3;
    private TextView moveAdd;
    private TextView garbage;
    private ArrayList<PieEntry> item ;
    private int[] item2 ;
    private String custom_name ;
    private View v;
    private MenuEmotion activity;

    private Custom_power_DBHelper custom_powerDB;
    private Custom_gradient_DBHelper custom_gradient_DB;

    public SliderAdapter(MenuEmotion activity, Context context, ArrayList<ArrayList> itemList, ArrayList<int[]> itemList2, ArrayList<String> title){
        this.activity = activity;
        this.context = context;
        this.itemList = itemList;
        this.itemList2 = itemList2;
        this.title = title;
        custom_powerDB = new Custom_power_DBHelper(context, "custom_power", null, 1);
        custom_gradient_DB = new Custom_gradient_DBHelper(context,"custom_color", null, 1);
    }

    @Override
    public int getCount() {
        return itemList.size();
    }

    @Override
    public boolean isViewFromObject(View view, Object object) {
        return view == ((ConstraintLayout) object);
    }

    @Override
    public Object instantiateItem(final ViewGroup container, final int position) {
        inflater = (LayoutInflater) context.getSystemService
                (Context.LAYOUT_INFLATER_SERVICE);
        v = inflater.inflate(R.layout.item_slider, container, false);
        pieChart = v.findViewById(R.id.piechart);
        gradient = (CustomGradientCardButton)v.findViewById(R.id.gradientCard);
        tvTitle = (TextView)v.findViewById(R.id.custom_title);
        a_btn1 = (TextView)v.findViewById(R.id.as_btn1);
        a_btn2 = (TextView)v.findViewById(R.id.as_btn2);
        a_btn3 = (TextView)v.findViewById(R.id.as_btn3);

        moveAdd = (TextView)v.findViewById(R.id.add_move);
        moveAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context, CustomAddActivity.class);
                context.startActivity(intent);
            }
        });
        item = itemList.get(position);
        item2 = itemList2.get(position);
        custom_name = title.get(position);
        garbage = (TextView)v.findViewById(R.id.trash);
        garbage.setVisibility(View.GONE);
        garbage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                title.get(position);
                Log.i("onClick",  title.get(position));

                try {
                    custom_gradient_DB.delete(title.get(position));
                    custom_powerDB.delete(title.get(position));
                } catch (JSONException e) {
                    e.printStackTrace();
                }
                notifyDataSetChanged();
                v.invalidate();
                activity.onResume();
            }
        });



        if(!custom_name.equals("New Custom Add")) {
            moveAdd.setVisibility(View.GONE);
            garbage.setVisibility(View.VISIBLE);
        }
        tvTitle.setText(custom_name);
        pieChart.getLegend().setEnabled(false); // 하단바 없앰
        pieChart.setDrawSliceText(false);
        pieChart.getDescription().setEnabled(false);
        pieChart.setExtraOffsets(5,5,5,5);
        pieChart.setDrawHoleEnabled(false);
        pieChart.setHoleColor( context.getResources().getColor(R.color.trans));
        pieChart.setTransparentCircleRadius(61f);
        pieChart.setTouchEnabled(false);
        PieDataSet dataSet = new PieDataSetCustom(item ," ");


        dataSet.setColors( context.getResources().getColor(R.color.aroma1),
                context.getResources().getColor(R.color.aroma2),
                context.getResources().getColor(R.color.aroma3),
                context.getResources().getColor(R.color.trans)
        );

        dataSet.setSelectionShift(0);
        dataSet.setValueTextSize(0);
        PieData data = new PieData((dataSet));
        data.setDrawValues(false);

        pieChart.setData(data);
        pieChart.setTag(item);
        gradient.setCircleColors(item2);


        container.addView(v);
        return v;
    }

    @Override
    public int getItemPosition(Object object) { return POSITION_NONE; }


    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {

    }


}


