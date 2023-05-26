package com.example.da_android.activity;

import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.GridView;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.example.da_android.DB;
import com.example.da_android.R;
import com.example.da_android.adapter.AnnualReportAdapter;
import com.example.da_android.model.Transaction;
import com.github.mikephil.charting.charts.BarChart;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.data.BarData;
import com.github.mikephil.charting.data.BarDataSet;
import com.github.mikephil.charting.data.BarEntry;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

public class AnnualReportActivity extends AppCompatActivity {
    Context context;
    int selectedTab = 1;
    LinearLayout typeChiLayout, typeThuLayout, typeTongLayout, totalLayout;
    TextView currentDate;
    Calendar calendar = Calendar.getInstance(Locale.ENGLISH);
    SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy",Locale.ENGLISH);
    AnnualReportAdapter annualReportAdapter;
    ImageButton nextButton,backButton, backActivityButton;
    TextView txtChi,txtThu, txtTong, txtAll, txtMess;
    BarChart barChart;
    GridView gv;
    String type = "Chi";
    String username;
    DB db = new DB();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_annual_report);
        context = this;
        SharedPreferences sharedPref = context.getSharedPreferences("my_prefs", Context.MODE_PRIVATE);
        username = sharedPref.getString("username", null);
        typeChiLayout = findViewById(R.id.type_annual_report_chi_layout);
        typeThuLayout = findViewById(R.id.type_annual_report_thu_layout);
        typeTongLayout = findViewById(R.id.type_annual_report_tong_layout);
        totalLayout = findViewById(R.id.annual_report_total_layout);

        txtChi = findViewById(R.id.txt_annual_report_type_chi);
        txtThu = findViewById(R.id.txt_annual_report_type_thu);
        txtTong = findViewById(R.id.txt_annual_report_type_tong);
        txtAll = findViewById(R.id.txt_annual_report_tong);
        txtMess = findViewById(R.id.txt_annual_report_mess);

        nextButton = findViewById(R.id.btn_annual_report_date_next);
        backButton = findViewById(R.id.btn_annual_report_date_back);
        backActivityButton = findViewById(R.id.btn_annual_report_back);
        currentDate = findViewById(R.id.txt_annual_report_currentDate);

        barChart = findViewById(R.id.barChart_annual_report);
        gv = findViewById(R.id.gv_annual_report);

        setUpCalendar();
        setUpFrame();
        setUpReport(type);

        typeChiLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(selectedTab != 1)
                {
                    typeChiLayout.setBackgroundResource(R.drawable.select_type_round);
                    txtChi.setTextColor(getResources().getColor(android.R.color.white));

                    typeThuLayout.setBackgroundResource(R.drawable.header_report_type);
                    txtThu.setTextColor(getResources().getColor(R.color.holo_orange_dark));
                    typeTongLayout.setBackgroundResource(R.drawable.header_report_type);
                    txtTong.setTextColor(getResources().getColor(R.color.holo_orange_dark));
                    selectedTab = 1;
                    type = "Chi";
                    setUpCalendar();
                    setUpFrame();
                    setUpReport(type);
                }
            }
        });

        typeThuLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(selectedTab != 2)
                {
                    typeChiLayout.setBackgroundResource(R.drawable.header_report_type);
                    txtChi.setTextColor(getResources().getColor(R.color.holo_orange_dark));
                    typeTongLayout.setBackgroundResource(R.drawable.header_report_type);
                    txtTong.setTextColor(getResources().getColor(R.color.holo_orange_dark));

                    typeThuLayout.setBackgroundResource(R.drawable.select_type_round);
                    txtThu.setTextColor(getResources().getColor(android.R.color.white));
                    selectedTab = 2;
                    type = "Thu";
                    setUpCalendar();
                    setUpFrame();
                    setUpReport(type);
                }
            }
        });

        typeTongLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(selectedTab != 3)
                {
                    typeTongLayout.setBackgroundResource(R.drawable.select_type_round);
                    txtTong.setTextColor(getResources().getColor(R.color.white));

                    typeChiLayout.setBackgroundResource(R.drawable.header_report_type);
                    txtChi.setTextColor(getResources().getColor(R.color.holo_orange_dark));
                    typeThuLayout.setBackgroundResource(R.drawable.header_report_type);
                    txtThu.setTextColor(getResources().getColor(R.color.holo_orange_dark));
                    selectedTab = 3;
                    type = "All";
                    setUpCalendar();
                    setUpFrame();
                    setUpReport(type);
                }
            }
        });

        backButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                calendar.add(Calendar.YEAR, -1);
                setUpCalendar();
                setUpFrame();
                setUpReport(type);
            }
        });

        nextButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                calendar.add(Calendar.YEAR, 1);
                setUpCalendar();
                setUpFrame();
                setUpReport(type);
            }
        });
        backActivityButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }

    private void setUpCalendar() {
        String currDate = dateFormat.format(calendar.getTime());
        currentDate.setText(currDate);
    }

    private void setUpFrame()
    {
        if(selectedTab == 1)
        {
            typeChiLayout.setBackgroundResource(R.drawable.select_type_round);
            txtChi.setTextColor(getResources().getColor(android.R.color.white));
        }
        if(selectedTab == 2)
        {
            typeThuLayout.setBackgroundResource(R.drawable.select_type_round);
            txtThu.setTextColor(getResources().getColor(android.R.color.white));
        }
        if(selectedTab == 3)
        {
            typeTongLayout.setBackgroundResource(R.drawable.select_type_round);
            txtTong.setTextColor(getResources().getColor(android.R.color.white));
        }
    }

    private void setUpReport(String type)
    {
        db.readDataTransaction(username, new DB.OnDataLoadedListener() {
            @Override
            public void onDataLoaded(ArrayList<Transaction> list) {
                DateFormat dateFormat = new SimpleDateFormat("d/M/yyyy");
                DateFormat dateFormat1 = new SimpleDateFormat("yyyy");
                boolean flag = false;
                final int[] sum = new int[12];
                Arrays.fill(sum, 0);
                for (Transaction item : list)
                {
                    Date date;
                    Date date1;
                    try {
                        date = dateFormat.parse(item.getTrxDate());
                        date1 = dateFormat1.parse(currentDate.getText().toString());
                        if(date.getYear() == date1.getYear())
                        {
                            flag = true;
                            if(type.equals("All"))
                            {
                                if(item.getType().equals("Thu"))
                                {
                                    sum[date.getMonth()+1] += item.getMoney();
                                }
                                else
                                {
                                    sum[date.getMonth()+1] -= item.getMoney();
                                }
                            }
                            else
                            {
                                if(item.getType().equals(type))
                                {
                                    sum[date.getMonth()+1] += item.getMoney();
                                }
                            }

                        }
                    } catch (ParseException e) {
                        throw new RuntimeException(e);
                    }
                }
                if (!flag)
                {
                    txtMess.setText("Không có dữ liệu");
                    totalLayout.setVisibility(View.INVISIBLE);
                    gv.setAdapter(null);
                    barChart.setData(null);
                    barChart.invalidate();
                    barChart.setVisibility(View.INVISIBLE);
                }
                else {
                    int total = 0;
                    for (int i = 1; i < sum.length; i++) {
                        total += sum[i];
                    }
                    if(total != 0)
                    {
                        txtAll.setText(String.valueOf(total)+"đ");
                    }
                    else
                    {
                        txtAll.setText("0đ");
                    }
                    txtMess.setText("");
                    barChart.setVisibility(View.VISIBLE);
                    totalLayout.setVisibility(View.VISIBLE);
                    setUpGridTransaction(sum);
                    setBarChart(sum);
                }

            }

            @Override
            public void onDataError(String errorMessage) {

            }
        });
    }

    private void setUpGridTransaction(int[] moneyList){
        annualReportAdapter = new AnnualReportAdapter(context, R.layout.month_annual_report, moneyList);
        gv.setAdapter(annualReportAdapter);

    }
    private void setBarChart(int[] moneyList)
    {
        ArrayList<BarEntry> barEntries = new ArrayList<>();
        for (int i = 0; i<moneyList.length; i++)
        {
            barEntries.add(new BarEntry(i+1, moneyList[i]));
        }
        BarDataSet barDataSet = new BarDataSet(barEntries,"");
        barDataSet.setColors(R.color.holo_orange_dark);
        barDataSet.setValueTextColor(Color.BLACK);
        barDataSet.setValueTextSize(12f);

        BarData barData = new BarData(barDataSet);

        XAxis xAxis = barChart.getXAxis();
        xAxis.setPosition(XAxis.XAxisPosition.BOTTOM);

        barChart.setFitBars(true);
        barChart.getDescription().setText("");
        barChart.setData(barData);
        barChart.invalidate();
    }
}