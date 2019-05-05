package com.example.aromind.MenuAlarm_RecyclerView;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.example.aromind.Activity.MainActivity;
import com.example.aromind.Activity.MainActivity_fragment.MenuAlarm;
import com.example.aromind.Activity.MunuAlarm_AlarmManager.AlarmReceiver;
import com.example.aromind.Model.Custom_gradient_DBHelper;
import com.example.aromind.Model.Custom_power_DBHelper;
import com.example.aromind.R;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.Locale;

public class HistoryAdapter extends RecyclerView.Adapter<ItemViewHolder> {

    //알람부분
    private boolean[] week = new boolean[8];
    private int hour, min;
    private Context context;
    private boolean isRepeat = false;
    private String card_title;

    //자동 분사 제어
    private int interval, repeat;

//    //향/무드라이트 가져오는 부분
//    private Custom_power_DBHelper custom_powerDB;
//    private Custom_gradient_DBHelper custom_gradient_DB;


    //recycle부분
    private ArrayList<HistoryBean> data;

    public HistoryAdapter(Context context, ArrayList<HistoryBean> data){
        this.context = context;
        this.data = data;
    }


    @NonNull
    @Override
    public ItemViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {

        View itemView = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.item_alarm, viewGroup, false);
        return new ItemViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull final ItemViewHolder itemViewHolder, final int i) {

        //데이터 불러오기/데이터 셋팅
        HistoryBean bean = data.get(i);
        itemViewHolder.set_am_pm.setText(bean.getSet_am_pm());
        itemViewHolder.set_time.setText(bean.getSet_Tiem());
        itemViewHolder.set_day.setText(bean.getSet_day());
        itemViewHolder.set_alarm_name.setText(bean.getSet_alarm_name());
        itemViewHolder.set_alarm_card_title.setText(bean.getCard_title());

        interval = bean.getInterval();
        repeat = bean.getRepeat();

        //카드이름 찾기
        card_title = bean.getCard_title();

        //버튼클릭시 동작
        itemViewHolder.on_off.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.i("chk1", "On");
                if (itemViewHolder.on_off.isChecked() == true) {

                    //몇번째 버튼을 클릭했고, 그 버튼에 맞는 값 가져오기기
                    week = data.get(i).getSet_week();
                    Log.i("chk1", String.valueOf(week[0] + " " + week[1] + " " + week[2] + " " + week[3] + " " + week[4] + " " + week[5] + " " + week[6] + " " + week[7]));
                    hour = data.get(i).getSet_hour();
                    Log.i("chk1", String.valueOf(hour));
                    min = data.get(i).getSet_min();
                    Log.i("chk1", String.valueOf(min));

                    Calendar calendar = Calendar.getInstance();
                    calendar.set(Calendar.HOUR_OF_DAY, hour);
                    calendar.set(Calendar.MINUTE, min);
                    calendar.set(Calendar.SECOND, 0);
                    calendar.set(Calendar.MILLISECOND, 0);

                    for (int i=0; i<week.length; i++){
                        if (week[i]){
                            isRepeat =true;
                            break;
                        }
                    }
                    startAlarm(calendar);
                }else{
                    cancleAlarm();
                }
            }


        });
    }

    @Override
    public int getItemCount() {
        return data.size();
    }

    private void startAlarm(Calendar calendar){
        AlarmManager alarmManager = (AlarmManager) context.getSystemService(Context.ALARM_SERVICE);
        Intent intent = new Intent(context, AlarmReceiver.class);



        if (isRepeat){
            intent.putExtra("week", week);
            intent.putExtra("one_time", false);
            intent.putExtra("card_title", card_title);
            intent.putExtra("interval", interval);
            intent.putExtra("repeat", repeat);
        }else {
            intent.putExtra("one_time", true);
            intent.putExtra("card_title", card_title);
            intent.putExtra("interval", interval);
            intent.putExtra("repeat", repeat);
        }
        Log.i("인터벌과 리핏adapter", String.valueOf(interval)+" / "+ String.valueOf(repeat));
        PendingIntent pendingIntent = PendingIntent.getBroadcast(context, 1, intent, PendingIntent.FLAG_UPDATE_CURRENT);
        alarmManager.setExact(AlarmManager.RTC_WAKEUP, calendar.getTimeInMillis(), pendingIntent);
    }

    private void cancleAlarm(){
        AlarmManager alarmManager = (AlarmManager) context.getSystemService(Context.ALARM_SERVICE);
        Intent intent = new Intent(context, AlarmReceiver.class);
        PendingIntent pendingIntent = PendingIntent.getBroadcast(context, 1, intent, PendingIntent.FLAG_UPDATE_CURRENT);
        alarmManager.cancel(pendingIntent);
    }

}
