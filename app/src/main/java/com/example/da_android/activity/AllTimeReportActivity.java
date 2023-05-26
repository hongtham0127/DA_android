package com.example.da_android.activity;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.example.da_android.DB;
import com.example.da_android.R;
import com.example.da_android.model.Transaction;

import java.util.ArrayList;
import java.util.Arrays;

public class AllTimeReportActivity extends AppCompatActivity {
    Context context;
    TextView txtChi, txtThu, txtTotal;
    ImageButton btnBack;
    String username;
    DB db = new DB();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_all_time_report);
        context = this;
        SharedPreferences sharedPref = context.getSharedPreferences("my_prefs", Context.MODE_PRIVATE);
        username = sharedPref.getString("username", null);

        btnBack = findViewById(R.id.btn_all_time_annual_report_back);
        txtChi = findViewById(R.id.txt_all_time_annual_report_chi);
        txtThu = findViewById(R.id.txt_all_time_annual_report_thu);
        txtTotal = findViewById(R.id.txt_all_time_annual_report_tong);
        setUpReport();

        btnBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }

    private void setUpReport()
    {
        db.readDataTransaction(username, new DB.OnDataLoadedListener() {
            @Override
            public void onDataLoaded(ArrayList<Transaction> list) {
                final int[] sum = new int[3];
                Arrays.fill(sum, 0);

                for (Transaction transaction : list)
                {
                    if(transaction.getType().equals("Chi"))
                    {
                        sum[0] += transaction.getMoney();
                    }
                    else
                    {
                        sum[1] += transaction.getMoney();
                    }
                }
                sum[2] = sum[1] - sum[0];

                txtChi.setText(String.valueOf(sum[0]) + "đ");
                txtThu.setText(String.valueOf(sum[1]) + "đ");
                txtTotal.setText(String.valueOf(sum[2]) + "đ");
            }

            @Override
            public void onDataError(String errorMessage) {

            }
        });
    }
}