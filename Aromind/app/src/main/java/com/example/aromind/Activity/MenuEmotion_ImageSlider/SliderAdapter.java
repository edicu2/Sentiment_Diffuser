package com.example.aromind.Activity.MenuEmotion_ImageSlider;


import android.content.Context;
import android.support.constraint.ConstraintLayout;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;
import android.view.animation.DecelerateInterpolator;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.aromind.Activity.MenuRemote_RecyclerView.PieDataSetCustom;
import com.example.aromind.CustomView.CustomGradientCardButton;
import com.example.aromind.R;
import com.github.mikephil.charting.animation.Easing;
import com.github.mikephil.charting.charts.PieChart;
import com.github.mikephil.charting.data.PieData;
import com.github.mikephil.charting.data.PieDataSet;
import com.github.mikephil.charting.data.PieEntry;

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
    private View.OnClickListener onClickItem;

    public SliderAdapter(Context context, ArrayList<ArrayList> itemList, ArrayList<int[]> itemList2, ArrayList<String> title){
        this.context = context;
        this.itemList = itemList;
        this.itemList2 = itemList2;
        this.title = title;
        this.onClickItem = onClickItem;
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
    public Object instantiateItem(ViewGroup container, int position) {
        inflater = (LayoutInflater) context.getSystemService
                (Context.LAYOUT_INFLATER_SERVICE);
        View v = inflater.inflate(R.layout.item_slider, container, false);
        pieChart = v.findViewById(R.id.piechart);
        gradient = (CustomGradientCardButton)v.findViewById(R.id.gradientCard);
        tvTitle = (TextView)v.findViewById(R.id.custom_title);

        ArrayList<PieEntry> item = itemList.get(position);
        int[] item2 = itemList2.get(position);
        String custom_name = title.get(position);
        tvTitle.setText(custom_name);
        pieChart.getLegend().setEnabled(false); // 하단바 없앰
        pieChart.setDrawSliceText(false);
        pieChart.getDescription().setEnabled(false);
        pieChart.setExtraOffsets(5,5,5,5);
        //holder.pieChart.setDragDecelerationFrictionCoef(0.95f);
        pieChart.setDrawHoleEnabled(false);
        pieChart.setHoleColor( context.getResources().getColor(R.color.trans));
        //holder.pieChart.setDrawCenterText(false);
        pieChart.setTransparentCircleRadius(61f);
        //pieChart.animateY(1000, Easing.EasingOption.EaseInOutCubic); //애니메이션


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
    public void destroyItem(ViewGroup container, int position, Object object) {
        //container.invalidate();
    }


}


