package com.example.aromind.Activity.MenuAlarm_Activity;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.example.aromind.R;

import java.util.ArrayList;

public class Setting_Alarm_Adapter extends BaseAdapter {

    private ArrayList<Setting_Alarm_item> data = new ArrayList<Setting_Alarm_item>();

    public Setting_Alarm_Adapter(){
    }

    @Override
    public int getCount() {
        return data.size();
    }

    @Override
    public Object getItem(int position) {
        return data.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        final int POS = position;
        final Context CONTEXT =  parent.getContext();

        if (convertView == null){
            LayoutInflater inflater = (LayoutInflater)CONTEXT.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = inflater.inflate(R.layout.setting_alarm_list, parent, false);
        }

        TextView menu_name = convertView.findViewById(R.id.menu_name);
        TextView menu_child = convertView.findViewById(R.id.menu_child);

        Setting_Alarm_item item = data.get(position);

        menu_name.setText(item.getList_menu());
        menu_child.setText(item.getList_child());

        return convertView;
    }

    public void addItem(String menu_name, String menu_child){

        Setting_Alarm_item item = new Setting_Alarm_item();

        item.setList_menu(menu_name);
        item.setList_child(menu_child);

        data.add(item);
    }

}
