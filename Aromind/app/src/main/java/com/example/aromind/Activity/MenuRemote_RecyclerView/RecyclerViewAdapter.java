package com.example.aromind.Activity.MenuRemote_RecyclerView;

import android.annotation.SuppressLint;
import android.content.Context;
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

import com.example.aromind.CustomView.CustomGradientButton;
import com.example.aromind.R;
import com.github.mikephil.charting.animation.Easing;
import com.github.mikephil.charting.charts.PieChart;
import com.github.mikephil.charting.data.PieData;
import com.github.mikephil.charting.data.PieDataSet;
import com.github.mikephil.charting.data.PieEntry;

import java.util.ArrayList;

public class RecyclerViewAdapter extends RecyclerView.Adapter<RecyclerViewAdapter.ViewHolder> {

    private ArrayList<ArrayList> itemList;
    private ArrayList<int[]> itemList2;
    private Context context;
    private View.OnClickListener onClickItem;

    public RecyclerViewAdapter(Context context, ArrayList<ArrayList> itemList, ArrayList<int[]> itemList2, View.OnClickListener onClickItem) {
        this.context = context;
        this.itemList = itemList;
        this.itemList2 = itemList2;
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
        holder.pieChart.getLegend().setEnabled(false); // 하단바 없앰
        holder.pieChart.setDrawSliceText(false);
        holder.pieChart.getDescription().setEnabled(false);
        holder.pieChart.setExtraOffsets(5,5,5,5);
        //holder.pieChart.setDragDecelerationFrictionCoef(0.95f);
        holder.pieChart.setDrawHoleEnabled(false);
        holder.pieChart.setHoleColor( context.getResources().getColor(R.color.trans));
        //holder.pieChart.setDrawCenterText(false);
        holder.pieChart.setTransparentCircleRadius(61f);
        holder.pieChart.animateY(1000, Easing.EasingOption.EaseInOutCubic); //애니메이션
        //Shader shader = new RadialGradient(80, 80, 80, new int[]{ContextCompat.getColor(context, R.color.colorPrimaryDark), Color.parseColor("#FFFFFF")}, null, Shader.TileMode.MIRROR);
        //holder.pieChart.getRenderer().getPaintRender().setShader(shader);

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
        holder.gradient.setCircleColors(new SweepGradient(170,170,item2,null));
        //holder.gradient.setCircleColor(Color.RED);

        //dataSet.setSliceSpace(0.5f);
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

        public ViewHolder(View itemView) {
            super(itemView);
            pieChart = itemView.findViewById(R.id.piechart);
            gradient = (CustomGradientButton)itemView.findViewById(R.id.gradient);

        }
    }
}