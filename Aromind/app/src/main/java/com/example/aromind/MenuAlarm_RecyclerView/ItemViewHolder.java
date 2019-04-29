package com.example.aromind.MenuAlarm_RecyclerView;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Switch;
import android.widget.TextView;

import com.example.aromind.R;

public class ItemViewHolder extends RecyclerView.ViewHolder {
     TextView set_am_pm, set_time, set_alarm_name, set_day, set_alarm_card_title;
     Switch on_off;

    public ItemViewHolder(@NonNull View itemView) {
        super(itemView);

        set_am_pm = itemView.findViewById(R.id.set_am_pm);
        set_time = itemView.findViewById(R.id.set_time);
        set_alarm_name = itemView.findViewById(R.id.set_alarm_name);
        set_day = itemView.findViewById(R.id.set_day);
        on_off = itemView.findViewById(R.id.on_off);
        set_alarm_card_title = itemView.findViewById(R.id.set_alarm_card_title);

    }
}
