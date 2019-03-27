package com.example.aromind.Activity.Gradient;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Color;
import android.os.Build;
import android.support.annotation.NonNull;
import android.support.annotation.RequiresApi;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;


import com.example.aromind.CustomView.CustomGradientCardButton;
import com.example.aromind.R;
import java.util.ArrayList;
import java.util.Collections;

public class ColorAdapter extends RecyclerView.Adapter<ColorAdapter.ViewHolder> implements ColorItemTouchHelperCallback.OnItemMoveListener {
    private ArrayList<ColorItem> items = new ArrayList<>();
    private Context context;
    private CustomGradientCardButton gradient;


    public ColorAdapter(Context context, CustomGradientCardButton gradient) {
        this.context = context;
        this.gradient = gradient;

    }


    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int i) {
        View view = LayoutInflater.from(context)
                .inflate(R.layout.item_color, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder viewHolder, int position) {
        ColorItem item = items.get(position);
        viewHolder.color.setText("Color RGB (" + item.getR() + "," + item.getG() + "," + item.getB() + ")");
        viewHolder.color.setBackgroundColor(Color.rgb(item.getR(),item.getG(),item.getB()));

    }

    @Override
    public int getItemCount() {
        return (items == null) ? 0 : items.size();
    }

    public void add(ColorItem item){
        items.add(item);
        notifyDataSetChanged();
    }

    @Override
    public boolean OnItemMove(int fromPosition, int toPosition) {
        Collections.swap(items,fromPosition,toPosition);
        notifyItemMoved(fromPosition,toPosition);
        getGradient();
        return false;
    }

    @Override
    public void onItemDismiss(int position) {
        items.remove(position);
        notifyItemRemoved(position);
        getGradient();
    }

    public void getGradient(){
        if(items.isEmpty() == false) {
            gradient.setCircleColors(getColors());
        }else{
            gradient.setCircleColors(new int[]{Color.WHITE,Color.GRAY,Color.BLACK,Color.WHITE,});
        }
    }


    // int array로 변환시키면 가능 r,g,b
    public int[] getColors(){
        int[] colorArray = new int[items.size()+1];
        for(int i =0 ; i<items.size() ; i++){
            colorArray[i] = Color.rgb(items.get(i).getR(),items.get(i).getG(),items.get(i).getB());
        }
        if(items.isEmpty() == false)
            colorArray[items.size()] = Color.rgb(items.get(0).getR(),items.get(0).getG(),items.get(0).getB());
        return colorArray;
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        public TextView color;

        public ViewHolder(View itemView) {
            super(itemView);
            color = (TextView) itemView.findViewById(R.id.color_item);

        }
    }


}
