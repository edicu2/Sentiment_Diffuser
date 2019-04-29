package com.example.aromind.Activity.MainActivity_fragment;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.example.aromind.Activity.MenuAlarm_Activity.Setting_Alarm;
import com.example.aromind.R;
import com.example.aromind.facebook.Facebook;

public class MenuSetting extends Fragment {

    Button button;
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.menu_setting, container, false);

        button = view.findViewById(R.id.faceBook_Login);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), Facebook.class);
                startActivity(intent);
            }
        });

        return view;
    }
}
