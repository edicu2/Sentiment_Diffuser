package com.example.aromind.Activity;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.helper.ItemTouchHelper;
import android.view.KeyEvent;
import android.view.MotionEvent;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.SeekBar;
import android.widget.TextView;
import android.widget.Toast;


import com.example.aromind.Activity.Gradient.ColorAdapter;
import com.example.aromind.Activity.Gradient.ColorItem;
import com.example.aromind.Activity.Gradient.ColorItemTouchHelperCallback;
import com.example.aromind.Activity.MenuRemote_RecyclerView.PieDataSetCustom;
import com.example.aromind.CustomView.CustomGradientCardButton;
import com.example.aromind.Model.Custom_gradient_DBHelper;
import com.example.aromind.Model.Custom_power_DBHelper;
import com.example.aromind.R;
import com.github.mikephil.charting.charts.PieChart;
import com.github.mikephil.charting.data.PieData;
import com.github.mikephil.charting.data.PieDataSet;
import com.github.mikephil.charting.data.PieEntry;
import java.util.ArrayList;


public class CustomAddActivity extends AppCompatActivity implements View.OnClickListener, SeekBar.OnSeekBarChangeListener {

    private ColorAdapter mAdapter;
    private RecyclerView mRecyclerView;
    private LinearLayoutManager mLayoutManager;


    private final int REQUEST_CODE_ALPHA = 100;

    private PieChart pieChart;
    private CustomGradientCardButton gradient;
    private int[] colors;
    private EditText custom_name;
    private InputMethodManager imm;
    private Button saveBtn, backBtn;
    private View blur;
    private TextView gradientAdd;
    private SeekBar positive,neutral,negative,bright;
    private TextView positive_value, neutral_value, negative_value,bright_value;
    private Custom_power_DBHelper custom_powerDB;
    private Custom_gradient_DBHelper custom_gradient_DB;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_custom_add);

        pieChart = (PieChart)findViewById(R.id.piechart);
        gradient = (CustomGradientCardButton)findViewById(R.id.gradientCard);
        saveBtn = (Button)findViewById(R.id.save_btn);
        saveBtn.setOnClickListener(this);
        backBtn = (Button)findViewById(R.id.back_btn);
        backBtn.setOnClickListener(this);
        blur = (View)findViewById(R.id.blur);
        blur.setVisibility(View.GONE);

        positive = (SeekBar)findViewById(R.id.positive);
        positive.setOnSeekBarChangeListener(this);
        neutral = (SeekBar)findViewById(R.id.neutral);
        neutral.setOnSeekBarChangeListener(this);
        negative = (SeekBar)findViewById(R.id.negative);
        negative.setOnSeekBarChangeListener(this);
        bright = (SeekBar)findViewById(R.id.bright);
        bright.setOnSeekBarChangeListener(this);

        positive_value =(TextView)findViewById(R.id.positive_value);
        positive_value.setText(""+positive.getProgress());
        neutral_value =(TextView)findViewById(R.id.neutral_value);
        neutral_value.setText(""+neutral.getProgress());
        negative_value =(TextView)findViewById(R.id.negative_value);
        negative_value.setText(""+negative.getProgress());
        bright_value = (TextView)findViewById(R.id.bright_value);
        bright_value.setText(""+bright.getProgress());

        gradientAdd = (TextView)findViewById(R.id.gradientAdd);
        gradientAdd.setOnClickListener(this);

        custom_powerDB = new Custom_power_DBHelper(CustomAddActivity.this, "custom_power", null, 1);
        custom_gradient_DB = new Custom_gradient_DBHelper(CustomAddActivity.this,"custom_color", null, 1);


        ArrayList<String> title = new ArrayList<>();
        ArrayList<PieEntry> pie = new ArrayList<PieEntry>();

        pie.add(new PieEntry(positive.getProgress(), "aroma3"));
        pie.add(new PieEntry(neutral.getProgress(), "aroma2"));
        pie.add(new PieEntry(negative.getProgress(), "aroma1"));;
        colors = new int[]{Color.WHITE, R.color.aroma1, R.color.aroma2, R.color.aroma3, Color.WHITE};
        title.add("Rose Temple");

        pieChart.getLegend().setEnabled(false); // 하단바 없앰
        pieChart.setDrawSliceText(false);
        pieChart.getDescription().setEnabled(false);
        pieChart.setExtraOffsets(5,5,5,5);
        pieChart.setDrawHoleEnabled(false);
        pieChart.setHoleColor( getResources().getColor(R.color.trans));
        pieChart.setTransparentCircleRadius(61f);
        pieChart.setTouchEnabled(false);

        PieDataSet dataSet = new PieDataSetCustom(pie ," ");


        dataSet.setColors( getResources().getColor(R.color.aroma1),
                getResources().getColor(R.color.aroma2),
                getResources().getColor(R.color.aroma3),
                getResources().getColor(R.color.trans)
        );

        dataSet.setSelectionShift(0);
        dataSet.setValueTextSize(0);
        PieData data = new PieData((dataSet));
        data.setDrawValues(false);

        pieChart.setData(data);
        pieChart.setTag(pie);
        gradient.setCircleColors(colors);

        custom_name = (EditText)findViewById(R.id.title);
        imm = (InputMethodManager) getSystemService(INPUT_METHOD_SERVICE);
        custom_name.setOnTouchListener(new View.OnTouchListener() {
            public boolean onTouch(View arg0, MotionEvent arg1)
            {
                saveBtn.setVisibility(View.INVISIBLE);
                backBtn.setVisibility(View.INVISIBLE);
                blur.setVisibility(View.VISIBLE);
                return false;
            }
        });
        custom_name.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView textView, int i, KeyEvent keyEvent) {
                saveBtn.setVisibility(View.VISIBLE);
                backBtn.setVisibility(View.VISIBLE);
                blur.setVisibility(View.GONE);
                String inText = textView.getText().toString();
                custom_name.clearFocus();
                imm.hideSoftInputFromWindow(custom_name.getWindowToken(), 0);
                // Do Something...

                return true;
            }
        });

        ColorItem item1 = new ColorItem();
        item1.setR(0);
        item1.setG(0);
        item1.setB(0);
        mAdapter = new ColorAdapter(this, gradient);
        mRecyclerView = (RecyclerView)findViewById(R.id.recycler_color);
        mLayoutManager = new LinearLayoutManager(this);
        ColorItemTouchHelperCallback mCallback = new ColorItemTouchHelperCallback(mAdapter);
        ItemTouchHelper mItemTouchHelper = new ItemTouchHelper(mCallback);
        mItemTouchHelper.attachToRecyclerView(mRecyclerView);
        mRecyclerView.setLayoutManager(mLayoutManager);
        mRecyclerView.setAdapter(mAdapter);
    }

    /* intent 보낸 것 다시 수신하는 부분 */
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode != RESULT_OK)
            return;

        if (requestCode == REQUEST_CODE_ALPHA) {
            if(bright.getProgress() == 0) {
                bright.setProgress(255);
                bright_value.setText("255");
            }
            int r = data.getIntExtra("r", 0);
            int g = data.getIntExtra("g", 0);
            int b = data.getIntExtra("b", 0);
            ColorItem item6 = new ColorItem();
            item6.setR(r);
            item6.setG(g);
            item6.setB(b);
            mAdapter.add(item6);
            gradient.setCircleColors(mAdapter.getColors());
            gradient.setAlpha(bright.getProgress());

        }
    }
    @Override
    public void onClick(View v) {
        if(v.getId() == R.id.gradientAdd){
            Intent intent = new Intent(this, GradientAddActivity.class);
            intent.putExtra("key", "value");
            startActivityForResult(intent, REQUEST_CODE_ALPHA);
        } else if(v.getId() == R.id.save_btn){
            if(custom_name.getText().toString().equals("")) {
                Toast.makeText(CustomAddActivity.this," Custom 카드 이름을 설정해주세요.",
                        Toast.LENGTH_LONG).show();
            }else{
                int[] colors = mAdapter.getColors();
                for (int i = 0; i < mAdapter.getColors().length; i++) {
                    custom_gradient_DB.insert(custom_name.getText().toString(), colors[i]);
                }
                custom_powerDB.insert(custom_name.getText().toString(), positive.getProgress(), neutral.getProgress(), negative.getProgress(), bright.getProgress());
                finish();
            }
        }else if(v.getId() == R.id.back_btn){
            finish();
        }

    }


    @Override
    public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {

        if(seekBar.getId() == R.id.bright) {
            bright_value.setText(""+bright.getProgress());
            if(mAdapter.getItemCount()!=0)
                gradient.setAlpha((bright.getProgress()));
        }else {
            ArrayList<String> title = new ArrayList<>();
            ArrayList<PieEntry> pie = new ArrayList<PieEntry>();

            pie.add(new PieEntry(positive.getProgress(), "aroma3"));
            pie.add(new PieEntry(neutral.getProgress(), "aroma2"));
            pie.add(new PieEntry(negative.getProgress(), "aroma1"));
            ;
            PieDataSet dataSet = new PieDataSetCustom(pie, " ");

            dataSet.setColors(getResources().getColor(R.color.aroma1),
                    getResources().getColor(R.color.aroma2),
                    getResources().getColor(R.color.aroma3),
                    getResources().getColor(R.color.trans)
            );

            dataSet.setSelectionShift(0);
            dataSet.setValueTextSize(0);
            PieData data = new PieData((dataSet));
            data.setDrawValues(false);
            pieChart.setData(data);
            pieChart.setTag(pie);
            pieChart.invalidate();
            positive_value.setText("" + positive.getProgress());
            neutral_value.setText("" + neutral.getProgress());
            negative_value.setText("" + negative.getProgress());
        }
    }
    @Override
    public void onStartTrackingTouch(SeekBar seekBar) {

    }
    @Override
    public void onStopTrackingTouch(SeekBar seekBar) {
    }
}
