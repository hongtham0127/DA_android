package com.example.da_android.activity;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.GridView;
import android.widget.ImageButton;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

import com.example.da_android.DB;
import com.example.da_android.R;
import com.example.da_android.adapter.TransactionGridAdapter;
import com.example.da_android.model.CategoryItem;
import com.example.da_android.model.Transaction;
import com.github.mikephil.charting.charts.BarChart;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.data.BarData;
import com.github.mikephil.charting.data.BarDataSet;
import com.github.mikephil.charting.data.BarEntry;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.highlight.Highlight;
import com.github.mikephil.charting.listener.OnChartValueSelectedListener;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;

public class ReportDetailActivity extends AppCompatActivity {
    Context context;
    ImageButton btnBack;
    TextView tvName, tvMonth, tvMoney;
    BarChart barChart;
    GridView gv;
    Calendar calendar = Calendar.getInstance();
    Date date;
    DB db = new DB();
    Transaction transaction;
    TransactionGridAdapter transactionGridAdapter;
    CategoryItem categoryItem;
    String username;
    DateFormat dateFormat = new SimpleDateFormat("d/M/yyyy");
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_report_detail);
        context = this;
        transaction = (Transaction) getIntent().getSerializableExtra("itemReportDetail");
        SharedPreferences sharedPref = context.getSharedPreferences("my_prefs", Context.MODE_PRIVATE);
        username = sharedPref.getString("username", null);
        btnBack = findViewById(R.id.btn_report_detail_back);
        tvName = findViewById(R.id.txt_report_detail_name);
        tvMonth = findViewById(R.id.txt_report_detail_month);
        tvMoney = findViewById(R.id.txt_report_detail_money);
        barChart = findViewById(R.id.barChart);
        gv = findViewById(R.id.gv_report_detail_transaction);

        try {
            date = dateFormat.parse(transaction.getTrxDate());
        } catch (ParseException e) {
            throw new RuntimeException(e);
        }
        calendar.setTime(date);
        setUpHeader();
        btnBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        barChart.setOnChartValueSelectedListener(new OnChartValueSelectedListener() {
            @Override
            public void onValueSelected(Entry e, Highlight h) {
                float value = e.getY();
                int month = (int) e.getX();
                transaction.setMoney((int) value);
                calendar.set(Calendar.MONTH, month-1);
                transaction.setTrxDate(dateFormat.format(calendar.getTime()));
                try {
                    date = dateFormat.parse(transaction.getTrxDate());
                } catch (ParseException ex) {
                    throw new RuntimeException(ex);
                }
                setUpHeader();
            }

            @Override
            public void onNothingSelected() {

            }
        });
    }

    private void setUpHeader()
    {
        db.readDataCategory(new DB.OnCategoryDataLoadedListener() {
            @Override
            public void onCategoryDataLoaded(ArrayList<CategoryItem> list) {
                categoryItem = db.searchCategorybyId(list, transaction.getIdCtg());
                tvName.setText(categoryItem.getName());

                int color = ContextCompat.getColor(context, categoryItem.getColor());
                setUp(color);
            }

            @Override
            public void onCategoryDataError(String errorMessage) {

            }
        });

        int month = calendar.get(Calendar.MONTH)+1;
        tvMonth.setText(" (T"+month+") ");
        tvMoney.setText(String.valueOf(transaction.getMoney())+"đ");
    }
    @SuppressLint("ResourceAsColor")
    private void setUp(int color)
    {
        db.readDataTransaction(username, new DB.OnDataLoadedListener() {
            @Override
            public void onDataLoaded(ArrayList<Transaction> list) {
                ArrayList<BarEntry> visitors = new ArrayList<>();
                ArrayList<Transaction> transactions = new ArrayList<>();
                ArrayList<Transaction> transactionsGV = new ArrayList<>();
                ArrayList<Transaction> transactionByMonth = new ArrayList<>();
                ArrayList<Integer> months = new ArrayList<>();
                for (Transaction item : list) {
                    Date date1;
                    try {
                        date1 = dateFormat.parse(item.getTrxDate());
                        if (date1.getYear() == date.getYear() && item.getIdCtg().equals(transaction.getIdCtg())) {
                            transactions.add(item);
                        }
                        if(date1.getYear() == date.getYear() && date1.getMonth() == date.getMonth() && item.getIdCtg().equals(transaction.getIdCtg()))
                        {
                            transactionsGV.add(item);
                        }
                    } catch (ParseException e) {
                        throw new RuntimeException(e);
                    }
                }
                setUpGridTransaction(transactionsGV);
                for (Transaction item : transactions) {
                    Date date1;
                    try {
                        date1 = dateFormat.parse(item.getTrxDate());
                    } catch (ParseException e) {
                        throw new RuntimeException(e);
                    }
                    if (transactionByMonth.size() == 0) {
                        transactionByMonth.add(new Transaction(item.getType(), item.getIdCtg(), item.getMoney(), item.getTrxDate()));
                        months.add(date1.getMonth() + 1);
                    } else {
                        boolean flag = false;
                        for (Transaction transaction1 : transactionByMonth) {
                            Date dateTransaction1;
                            try {
                                dateTransaction1 = dateFormat.parse(transaction1.getTrxDate());
                            } catch (ParseException e) {
                                throw new RuntimeException(e);
                            }
                            if (date1.getMonth() == dateTransaction1.getMonth()) {
                                int money = transaction1.getMoney();
                                transaction1.setMoney(money + item.getMoney());
                                flag = true;
                                break;
                            }
                        }
                        if (!flag) {
                            transactionByMonth.add(new Transaction(item.getType(), item.getIdCtg(), item.getMoney(), item.getTrxDate()));
                            months.add(date1.getMonth() + 1);
                        }
                    }
                }
                transactionByMonth = sortDateByMonth(transactionByMonth);
                int i=1;
                for (Transaction item : transactionByMonth) {
                    Date date1;
                    try {
                        date1 = dateFormat.parse(item.getTrxDate());
                    } catch (ParseException e) {
                        throw new RuntimeException(e);
                    }
                    while (i<(date1.getMonth() + 1) && months.contains(i))
                    {
                        visitors.add(new BarEntry(i, 0));
                        i++;
                    }
                    visitors.add(new BarEntry(date1.getMonth() + 1, item.getMoney()));
                }
                setBarChart(visitors, color);
            }
            @Override
            public void onDataError(String errorMessage) {
            }
        });


    }
    private void setBarChart(ArrayList<BarEntry> barEntries,Integer colors)
    {
        BarDataSet barDataSet = new BarDataSet(barEntries,"");
        barDataSet.setColors(colors);
        barDataSet.setValueTextColor(Color.BLACK);
        barDataSet.setValueTextSize(12f);

        BarData barData = new BarData(barDataSet);

        XAxis xAxis = barChart.getXAxis();
        xAxis.setPosition(XAxis.XAxisPosition.BOTTOM);
        xAxis.setGranularity(1);
        xAxis.setGranularityEnabled(true);
        barChart.setDragEnabled(true);
        barChart.setTouchEnabled(true);

        barChart.setVisibleXRangeMaximum(5);

        barChart.getXAxis().setAxisMinimum(0);

        barChart.getAxisLeft().setAxisMinimum(0);

        barChart.setFitBars(true);
        barChart.getDescription().setText("");
        barChart.setData(barData);
        barChart.invalidate();
    }
    private ArrayList<Transaction> sortDateByMonth(ArrayList<Transaction> transactionSort)
    {
        Collections.sort(transactionSort, new Comparator<Transaction>() {
            @Override
            public int compare(Transaction t1, Transaction t2) {
                Date date1, date2;
                try {
                    date1 = dateFormat.parse(t1.getTrxDate());
                    date2 = dateFormat.parse(t2.getTrxDate());
                } catch (ParseException e) {
                    throw new RuntimeException(e);
                }
                return date1.compareTo(date2);
            }
        });
        return transactionSort;
    }
    private void setUpGridTransaction(ArrayList<Transaction> listTransaction){
        if (listTransaction == null || listTransaction.size() == 0)
        {
            gv.setAdapter(null);
        }
        else
        {
            ArrayList<Date> dateArrayList;
            dateArrayList = db.listDateTransaction(listTransaction);

            Collections.sort(dateArrayList, new Comparator<Date>() {
                @Override
                public int compare(Date date1, Date date2) {
                    return date1.compareTo(date2);
                }
            });

            transactionGridAdapter = new TransactionGridAdapter(context, dateArrayList, listTransaction);
            gv.setAdapter(transactionGridAdapter);
        }

    }
}
