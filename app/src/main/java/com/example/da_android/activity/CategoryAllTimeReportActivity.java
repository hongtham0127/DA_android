package com.example.da_android.activity;

import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.example.da_android.R;
import com.example.da_android.fragment.ReportChartFragment;

public class CategoryAllTimeReportActivity extends AppCompatActivity {
    int selectedTab = 1;
    LinearLayout typeChiLayout, typeThuLayout;
    TextView txtChi,txtThu;
    ImageButton btnBack;

    Context context;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_category_all_time_report);
        context = this;

        typeChiLayout = findViewById(R.id.type_category_all_time_report_chi_layout);
        typeThuLayout = findViewById(R.id.type_category_all_time_report_thu_layout);

        txtChi = findViewById(R.id.txt_category_all_time_report_type_chi);
        txtThu = findViewById(R.id.txt_category_all_time_report_type_thu);

        btnBack = findViewById(R.id.btn_category_all_time_annual_report_back);
        setUpFrame();

        typeChiLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if(selectedTab != 1)
                {
                    getSupportFragmentManager()
                    .beginTransaction()
                    .setReorderingAllowed(true)
                    .replace(R.id.frame_container_category_all_time_report, new ReportChartFragment("Chi"))
                    .commit();

                    typeChiLayout.setBackgroundResource(R.drawable.select_type_round);
                    txtChi.setTextColor(getResources().getColor(android.R.color.white));

                    typeThuLayout.setBackgroundResource(R.drawable.header_report_type);
                    txtThu.setTextColor(getResources().getColor(R.color.holo_orange_dark));
                    selectedTab = 1;
                }
            }
        });

        typeThuLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(selectedTab != 2)
                {
                    getSupportFragmentManager()
                    .beginTransaction()
                    .setReorderingAllowed(true)
                    .replace(R.id.frame_container_category_all_time_report, new ReportChartFragment("Thu"))
                    .commit();

                    typeChiLayout.setBackgroundResource(R.drawable.header_report_type);
                    txtChi.setTextColor(getResources().getColor(R.color.holo_orange_dark));

                    typeThuLayout.setBackgroundResource(R.drawable.select_type_round);
                    txtThu.setTextColor(getResources().getColor(android.R.color.white));
                    selectedTab = 2;
                }
            }
        });
        btnBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
    }
    private void setUpFrame()
    {
        if(selectedTab != 1)
        {
            getSupportFragmentManager()
                    .beginTransaction()
                    .setReorderingAllowed(true)
                    .replace(R.id.frame_container_category_all_time_report, new ReportChartFragment("Chi"))
                    .commit();

            typeChiLayout.setBackgroundResource(R.drawable.select_type_round);
            txtChi.setTextColor(getResources().getColor(android.R.color.white));

            typeThuLayout.setBackgroundResource(R.drawable.header_report_type);
            txtThu.setTextColor(getResources().getColor(R.color.holo_orange_dark));
            selectedTab = 1;
        }

        if(selectedTab != 2)
        {
            getSupportFragmentManager()
                    .beginTransaction()
                    .setReorderingAllowed(true)
                    .replace(R.id.frame_container_category_all_time_report, new ReportChartFragment("Thu"))
                    .commit();

            typeChiLayout.setBackgroundResource(R.drawable.header_report_type);
            txtChi.setTextColor(getResources().getColor(R.color.holo_orange_dark));

            typeThuLayout.setBackgroundResource(R.drawable.select_type_round);
            txtThu.setTextColor(getResources().getColor(android.R.color.white));
            selectedTab = 2;
        }
    }
}