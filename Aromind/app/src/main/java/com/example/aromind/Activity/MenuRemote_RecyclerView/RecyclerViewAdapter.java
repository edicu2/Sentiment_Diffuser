package com.example.aromind.Activity.MenuRemote_RecyclerView;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.SweepGradient;
import android.os.Build;
import android.support.annotation.RequiresApi;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;
import android.view.animation.DecelerateInterpolator;
import android.widget.TextView;

import com.example.aromind.Activity.CustomAddActivity;
import com.example.aromind.CustomView.CustomGradientButton;
import com.example.aromind.R;
import com.github.mikephil.charting.animation.Easing;
import com.github.mikephil.charting.charts.PieChart;
import com.github.mikephil.charting.data.PieData;
import com.github.mikephil.charting.data.PieDataSet;
import com.github.mikephil.charting.data.PieEntry;

import org.w3c.dom.Text;

import java.util.ArrayList;


public class RecyclerViewAdapter extends RecyclerView.Adapter<RecyclerViewAdapter.ViewHolder> {

    private ArrayList<ArrayList> itemList;
    private ArrayList<int[]> itemList2;
    private ArrayList<String> title;
    private Context context;
    private View.OnClickListener onClickItem;

    public RecyclerViewAdapter(Context context, ArrayList<ArrayList> itemList, ArrayList<int[]> itemList2, View.OnClickListener onClickItem,ArrayList<String> title) {
        this.context = context;
        this.itemList = itemList;
        this.itemList2 = itemList2;
        this.title = title;
        this.onClickItem = onClickItem;
    }


    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        // context 와 parent.getContext() 는 같다.
        View view = LayoutInflater.from(context)
                .inflate(R.layout.item_graph, parent, false);

        return new ViewHolder(view);
    }

    @RequiresApi(api = Build.VERSION_CODES.M)
    @SuppressLint("ResourceType")
    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        ArrayList<PieEntry> item = itemList.get(position);
        int[] item2 = itemList2.get(position);

        String custom_name = title.get(position);
        if(!custom_name.equals("New Custom Add")) {
            holder.moveAdd.setVisibility(View.GONE);
        }
        holder.tvTitle.setText(custom_name);
        holder.pieChart.getLegend().setEnabled(false); // 하단바 없앰
        holder.pieChart.setDrawSliceText(false);
        holder.pieChart.getDescription().setEnabled(false);
        holder.pieChart.setExtraOffsets(5,5,5,5);
        holder.pieChart.setDrawHoleEnabled(false);
        holder.pieChart.setHoleColor( context.getResources().getColor(R.color.trans));
        holder.pieChart.setTransparentCircleRadius(61f);
        holder.pieChart.setTouchEnabled(false);
        holder.pieChart.animateY(1000, Easing.EasingOption.EaseInOutCubic); //애니메이션

        PieDataSet dataSet = new PieDataSetCustom(item ," ");
        dataSet.setColors( context.getResources().getColor(R.color.aroma1),
                           context.getResources().getColor(R.color.aroma2),
                           context.getResources().getColor(R.color.aroma3),
                            context.getResources().getColor(R.color.trans)
        );

        Animation fadeIn = new AlphaAnimation(0, 1);
        fadeIn.setInterpolator(new DecelerateInterpolator()); // add this
        fadeIn.setDuration(3000);
        holder.gradient.setAnimation(fadeIn);
        holder.gradient.setCircleColors(item2);

        dataSet.setSelectionShift(0);
        dataSet.setValueTextSize(0);
        PieData data = new PieData((dataSet));
        data.setDrawValues(false);

        holder.pieChart.setData(data);
        holder.pieChart.setTag(item);
        holder.pieChart.setOnClickListener(onClickItem);



    }

    @Override
    public int getItemCount() {

        return (null != itemList ? itemList.size() : 0);
    }


    public class ViewHolder extends RecyclerView.ViewHolder {

        public PieChart pieChart;
        public CustomGradientButton gradient;
        public TextView tvTitle;
        public TextView moveAdd;
        public ViewHolder(View itemView) {
            super(itemView);
            pieChart = itemView.findViewById(R.id.piechart);
            gradient = (CustomGradientButton)itemView.findViewById(R.id.gradient);
            tvTitle = (TextView)itemView.findViewById(R.id.custom_title);
            moveAdd = (TextView)itemView.findViewById(R.id.add_move);
            moveAdd.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(context, CustomAddActivity.class);
                    context.startActivity(intent);
                }
            });
        }
    }
}