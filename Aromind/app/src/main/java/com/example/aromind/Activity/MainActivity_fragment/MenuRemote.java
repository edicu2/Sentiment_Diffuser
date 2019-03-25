package com.example.aromind.Activity.MainActivity_fragment;

import android.graphics.Bitmap;
import android.graphics.Color;
import android.graphics.ColorMatrix;
import android.graphics.ColorMatrixColorFilter;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.SeekBar;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.ToggleButton;


import com.example.aromind.Activity.MenuRemote_RecyclerView.ListDecoration;
import com.example.aromind.Activity.MenuRemote_RecyclerView.RecyclerViewAdapter;
import com.example.aromind.CustomView.CustomButton;
import com.example.aromind.Model.Mqtt;
import com.example.aromind.R;
import com.github.mikephil.charting.data.PieEntry;

import org.eclipse.paho.client.mqttv3.IMqttDeliveryToken;
import org.eclipse.paho.client.mqttv3.MqttCallback;
import org.eclipse.paho.client.mqttv3.MqttMessage;

import java.util.ArrayList;


public class MenuRemote extends Fragment implements View.OnClickListener, CompoundButton.OnCheckedChangeListener, SeekBar.OnSeekBarChangeListener, View.OnTouchListener {
    View view;
    // temp, humidity
    private TextView temp, humidity;

    //color Pick
    private int r=255,g=255,b=255,bright=0;
    private ImageView color;
    private ToggleButton colorPower;
    private Bitmap bitmap;
    private SeekBar brightness;
    private CustomButton colorCheck;
    private ColorMatrix matrix;
    private ColorMatrixColorFilter filter;
    // temp, humidity

    // aroma power
    private ToggleButton aromaBtn1, aromaBtn2, aromaBtn3;

    //total Power
    private ToggleButton totalPower;

    //Custom
    private RecyclerView listview;
    private RecyclerViewAdapter adapter;

    // Mqtt String
    private String payload;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.menu_remote, container, false);
        // temp,humidity

        // Total Power
        totalPower = (ToggleButton)view.findViewById(R.id.totalPower);
        totalPower.setOnCheckedChangeListener(this);

        // Color Pick Part - color, color_power, brightness
        colorPower = (ToggleButton)view.findViewById(R.id.colorPower);
        colorPower.setOnCheckedChangeListener(this);
        color = (ImageView)view.findViewById(R.id.color);
        color.setDrawingCacheEnabled(true);
        color.buildDrawingCache(true);
        color.setOnTouchListener(this);
        brightness = (SeekBar)view.findViewById(R.id.brightness);
        brightness.setOnSeekBarChangeListener(this);
        colorCheck = (CustomButton)view.findViewById(R.id.color_check);
        colorCheck.setCircleColor(Color.argb(bright,r, g, b));
        colorCheck.setOnClickListener(this);

        // Aroma power Btn
        aromaBtn1 = (ToggleButton)view.findViewById(R.id.aroma1);
        aromaBtn1.setOnCheckedChangeListener(this);
        aromaBtn2 = (ToggleButton)view.findViewById(R.id.aroma2);
        aromaBtn2.setOnCheckedChangeListener(this);
        aromaBtn3 = (ToggleButton)view.findViewById(R.id.aroma3);
        aromaBtn3.setOnCheckedChangeListener(this);

        // first set
        matrix = new ColorMatrix();
        matrix.setSaturation(0);
        filter = new ColorMatrixColorFilter(matrix);
        color.setColorFilter(filter);

        return view;
    }

    @Override
    public void onStart() {
        super.onStart();
    }
    public void onResume() {
        super.onResume();
        listview = (RecyclerView)view.findViewById(R.id.main_listview);
        LinearLayoutManager layoutManager = new LinearLayoutManager(getContext(), LinearLayoutManager.HORIZONTAL, false);
        listview.setLayoutManager(layoutManager);
        ArrayList<ArrayList> itemList = new ArrayList<ArrayList>();
        ArrayList<int[]> itemList2 = new ArrayList<int[]>();
        ArrayList<String> title = new ArrayList<>();
        ArrayList<PieEntry> pie1 = new ArrayList<PieEntry>();
        ArrayList<PieEntry> pie2 = new ArrayList<PieEntry>();
        ArrayList<PieEntry> pie3 = new ArrayList<PieEntry>();
        ArrayList<PieEntry> pie4 = new ArrayList<PieEntry>();
        ArrayList<PieEntry> pie5 = new ArrayList<PieEntry>();
        ArrayList<PieEntry> pie6 = new ArrayList<PieEntry>();


        pie1.add(new PieEntry(40f,"aroma1"));
        pie1.add(new PieEntry(40f,"aroma2"));
        pie1.add(new PieEntry(40f,"aroma3"));

        itemList.add(pie1);
        itemList2.add(new int[]{Color.WHITE,R.color.aroma1,R.color.aroma2,R.color.aroma3,Color.WHITE});
        title.add("blue Ocean");

        pie2.add(new PieEntry(40f,"aroma1"));
        pie2.add(new PieEntry(40f,"aroma3"));
        itemList.add(pie2);
        itemList2.add(new int[]{Color.WHITE,Color.RED, Color.CYAN, Color.LTGRAY,Color.WHITE});
        title.add("empty");

        pie3.add(new PieEntry(40f,"aroma1"));
        pie3.add(new PieEntry(40f,"aroma2"));
        pie3.add(new PieEntry(40f,"aroma3"));
        itemList.add(pie3);
        itemList2.add(new int[]{Color.WHITE,Color.BLUE, Color.GREEN, Color.RED,Color.WHITE});
        title.add("Rose Temple");

        pie4.add(new PieEntry(40f,"aroma1"));
        pie4.add(new PieEntry(40f,"aroma2"));
        pie4.add(new PieEntry(40f,"aroma3"));
        itemList.add(pie4);
        itemList2.add(new int[]{Color.WHITE,Color.BLUE, Color.GREEN, Color.RED,Color.WHITE});
        title.add("complex");

        adapter = new RecyclerViewAdapter(getContext(), itemList, itemList2, onClickItem, title);
        listview.setAdapter(adapter);
        ListDecoration decoration = new ListDecoration();
        listview.addItemDecoration(decoration);
    }

    private View.OnClickListener onClickItem = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            String str = (String) v.getTag();
            Toast.makeText(getActivity(), str, Toast.LENGTH_SHORT).show();
        }
    };


    public void mqttSubCallback(){
        Mqtt.client.setCallback(new MqttCallback() {
            @Override
            public void connectionLost(Throwable cause) {
            }
            @Override
            public void messageArrived(String topic, MqttMessage message) throws Exception {
                Log.i("mqttCollback",new String(message.getPayload()));

                if(new String(message.getPayload()).split("_")[1].equals("low") ||
                        new String(message.getPayload()).split("_")[1].equals("enough"));
            }
            @Override
            public void deliveryComplete(IMqttDeliveryToken token) {
            }
        });
    }


    @Override
    public void onClick(View v) {

    }

    @Override
    public boolean onTouch(View v, MotionEvent event) {
        if(totalPower.isChecked() == false){
            setAllStop();
        }else{
            if(event.getAction() == MotionEvent.ACTION_DOWN || event.getAction() == MotionEvent.ACTION_MOVE){
                setColorOn();
                colorPower.setChecked(true);
                bitmap = color.getDrawingCache();
                int px = bitmap.getPixel((int)event.getX(),(int)event.getY());
                if (!(Color.red(px) == 0 && Color.green(px) == 0 && Color.blue(px) == 0)) {
                    r = Color.red(px);
                    g = Color.green(px);
                    b = Color.blue(px);
                    bright = brightness.getProgress();
                    colorPower.setChecked(true);
                    if(brightness.getProgress() == 0)
                        brightness.setProgress(20);
                    colorCheck.setCircleColor(Color.argb(bright,r, g, b));
                    payload = "colorPicker_"+r+","+g+","+b+","+(float)bright/255;

                    Log.i("mqtt",payload);
                    Mqtt.clientPub(getActivity(), payload);
                }
            }

        }
        return false;
    }


    @Override
    public void onCheckedChanged(CompoundButton v, boolean isChecked) {
        Log.i("msg",getView().getResources().getResourceEntryName(v.getId()));
        if (totalPower.isChecked() == true) {
                if (v.getId() == R.id.colorPower) {
                    if (isChecked == true) {
                        setColorOn();
                        brightness.setProgress(20);
                    } else {
                        setColorOff();
                        brightness.setProgress(0);
                    }
                }
                if (isChecked == true) {
                    payload = v.getResources().getResourceEntryName(v.getId()) + "_ON";
                } else {
                    setColorOff();
                    payload = v.getResources().getResourceEntryName(v.getId()) + "_OFF";
                }
        }else {
            setAllStop();
            payload = v.getResources().getResourceEntryName(v.getId()) + "_OFF";
        }
        Log.i("mqtt", payload);
        Mqtt.clientPub(getActivity(), payload);

    }

    @Override
    public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
        if(totalPower.isChecked() == false){
            setAllStop();
        }else {
            if (progress == 0) {
                colorPower.setChecked(false);
                setColorOff();
            }
            else {
                colorPower.setChecked(true);
                setColorOn();
            }
            bright = progress;
            payload = "colorPicker_"+r+","+g+","+b+","+(float)bright/255;
            colorCheck.setCircleColor(Color.argb(bright,r, g, b));
            Log.i("mqtt",payload);
            Mqtt.clientPub(getActivity(), payload);
        }
    }

    @Override
    public void onStartTrackingTouch(SeekBar seekBar) {

    }

    @Override
    public void onStopTrackingTouch(SeekBar seekBar) {

    }

    public void setAllStop(){
        aromaBtn1.setChecked(false);
        aromaBtn2.setChecked(false);
        aromaBtn3.setChecked(false);
        colorPower.setChecked(false);
        brightness.setProgress(0);
        setColorOff();
    }

    public void setColorOn(){
        matrix.setSaturation(1);
        filter = new ColorMatrixColorFilter(matrix);
        color.setColorFilter(filter);
    }

    public void setColorOff(){
        ColorMatrix matrix = new ColorMatrix();
        matrix.setSaturation(0);
        filter = new ColorMatrixColorFilter(matrix);
        color.setColorFilter(filter);
        colorCheck.setCircleColor(Color.argb(0,0, 0, 0));
    }
}