package com.example.aromind.Activity.MenuRemote_RecyclerView;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.graphics.ColorMatrix;
import android.graphics.ColorMatrixColorFilter;
import android.graphics.SweepGradient;
import android.os.Build;
import android.preference.Preference;
import android.support.annotation.RequiresApi;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;
import android.view.animation.DecelerateInterpolator;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.ToggleButton;

import com.example.aromind.Activity.CustomAddActivity;
import com.example.aromind.Activity.MainActivity_fragment.MenuRemote;
import com.example.aromind.CustomView.CustomButton;
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

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.w3c.dom.Text;

import java.util.ArrayList;


public class RecyclerViewAdapter extends RecyclerView.Adapter<RecyclerViewAdapter.ViewHolder> implements View.OnClickListener {

    private ArrayList<ArrayList> itemList;
    private ArrayList<int[]> itemList2;
    private ArrayList<String> title;
    private Context context;
    private Activity activity;
    private ToggleButton btn_positive,btn_neutral, btn_negative;
    private Custom_power_DBHelper custom_powerDB;
    private Custom_gradient_DBHelper custom_gradient_DB;
    private ImageView color;
    private ToggleButton colorPower;
    private ToggleButton totalButton;
    private int focus_p;
    private SharedPreferences pref;
    private SharedPreferences.Editor editor;
    private CustomButton colorCheck;

    public RecyclerViewAdapter(Context context, Activity activity, ToggleButton btn_positive, ToggleButton btn_neutral, ToggleButton btn_negative, ImageView color, ToggleButton colorPower , CustomButton colorCheck, ToggleButton totalButton,
                               ArrayList<ArrayList> itemList, ArrayList<int[]> itemList2, ArrayList<String> title) {

        pref = context.getSharedPreferences("Sentiment", 0);
        editor = pref.edit();
        this.context = context;
        this.activity = activity;
        this.itemList = itemList;
        this.itemList2 = itemList2;
        this.title = title;
        this.btn_positive = btn_positive;
        this.btn_neutral = btn_neutral;
        this.btn_negative = btn_negative;
        this.totalButton = totalButton;
        this.color = color;
        this.colorPower = colorPower;
        this.colorCheck = colorCheck;
        custom_powerDB = new Custom_power_DBHelper(context, "custom_power", null, 1);
        custom_gradient_DB = new Custom_gradient_DBHelper(context,"custom_color", null, 1);
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
    public void onBindViewHolder(final ViewHolder holder, int position) {
        ArrayList<PieEntry> item = itemList.get(position);
        int[] item2 = itemList2.get(position);

        String custom_name = title.get(position);
        if(!custom_name.equals("New Custom Add")) {
            holder.moveAdd.setVisibility(View.GONE);
        }else{
            holder.focus.setVisibility(View.GONE);
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
        holder.pieChart.setOnClickListener(this);


        Log.i("pref", pref.getString("remote_set", ""));
        if(pref.getString("remote_set", "").equals(custom_name)){
            holder.focus.setChecked(true);
        }
    }

    @Override
    public int getItemCount() {
        return (null != itemList ? itemList.size() : 0);
    }

    @Override
    public void onClick(View v) {

    }


    public class ViewHolder extends RecyclerView.ViewHolder {

        public ToggleButton focus;
        public PieChart pieChart;
        public CustomGradientButton gradient;
        public TextView tvTitle;
        public TextView moveAdd;

        public ViewHolder(View itemView) {
            super(itemView);
            focus = itemView.findViewById(R.id.focus);
            focus.setFocusableInTouchMode(true);
            focus.setFocusable(true);



            focus.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    focus.setChecked(true);
                    editor.putString("remote_set",(String)tvTitle.getText());
                    editor.apply();
                    MenuRemote.listToggleSetFalse();

                    JSONObject powers = custom_powerDB.getData((String) tvTitle.getText());
                    JSONArray colors = custom_gradient_DB.getData((String) tvTitle.getText());

                    try {
                        if(totalButton.isChecked() == true) {
                            if (Integer.parseInt((String) powers.get("positive"))!=  0) {
                                btn_positive.setChecked(true);
                                Mqtt.clientPub(activity, "aroma1_" + (String) powers.get("positive"));
                            }else{
                                btn_positive.setChecked(false);
                            }
                            if (Integer.parseInt((String) powers.get("neutral")) != 0) {
                                btn_neutral.setChecked(true);
                                Mqtt.clientPub(activity, "aroma2_" + (String) powers.get("neutral"));
                            }else{
                                btn_neutral.setChecked(false);
                            }
                            if (Integer.parseInt((String) powers.get("negative")) != 0) {
                                btn_negative.setChecked(true);
                                Mqtt.clientPub(activity, "aroma3_" + (String) powers.get("negative"));
                            }else{
                                btn_negative.setChecked(false);
                            }
                            int[] color_array = null;
                            String gradient_payload = null;
                            if ((int)colors.length() != 0) {
                                for (int j = 0; j < colors.length(); j++) {
                                    JSONObject color_object = (JSONObject) colors.get(j);
                                    int int_c= Integer.parseInt((String)color_object.get("color"));
                                    int r = Color.red(int_c);
                                    int g = Color.green(int_c);
                                    int b = Color.blue(int_c);
                                    if (j == 0) {
                                        color_array = new int[colors.length() + 1];
                                        gradient_payload = "gradient_";
                                        gradient_payload += r+"."+g+"."+b;
                                    }else{
                                        gradient_payload +=","+r+"."+g+"."+b;
                                    }
                                    color_array[j] = Integer.parseInt((String) color_object.get("color"));
                                }
                                JSONObject last = (JSONObject) colors.get(0);
                                int r = Color.red(Integer.parseInt((String)last.get("color")));
                                int g = Color.green(Integer.parseInt((String)last.get("color")));
                                int b = Color.blue(Integer.parseInt((String)last.get("color")));
                                gradient_payload +=","+r+"."+g+"."+b;;
                                color_array[colors.length()] = Integer.parseInt((String) last.get("color"));
                                colorPower.setChecked(true);
                                colorCheck.setCircleColors(color_array);
                                Mqtt.clientPub(activity,gradient_payload);
                                //mqtt color gradient 전송하기
                            }else{
                                colorPower.setChecked(false);
                            }
                        }

                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }
            });

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