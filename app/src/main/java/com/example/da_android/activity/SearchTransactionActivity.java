package com.example.da_android.activity;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.GridView;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.da_android.DB;
import com.example.da_android.R;
import com.example.da_android.adapter.TransactionGridAdapter;
import com.example.da_android.model.CategoryItem;
import com.example.da_android.model.Transaction;

import java.text.Normalizer;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;

public class SearchTransactionActivity extends AppCompatActivity {

    ImageButton btnBack, btnCancel;
    GridView gvSearch;
    EditText txtSearch;
    TextView sumThu, sumChi, sumAll;
    Context context;
    TransactionGridAdapter transactionGridAdapter;
    DB db = new DB();
    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search_transaction);
        context = this;
        btnBack = findViewById(R.id.btn_search_transaction_back);
        btnCancel = findViewById(R.id.btn_cancel_search);
        txtSearch = findViewById(R.id.edit_search_transaction);
        gvSearch = findViewById(R.id.gv_search_transaction);
        sumThu = findViewById(R.id.txt_tong_thu_search);
        sumChi = findViewById(R.id.txt_tong_chi_search);
        sumAll = findViewById(R.id.txt_tong_tien_search);
        SharedPreferences sharedPref = getSharedPreferences("my_prefs", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPref.edit();
        editor.putString("viewCategory", null);

        btnBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        btnCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(txtSearch.getText().toString().equals(""))
                {
                    Toast.makeText(context, "Hãy nhập danh mục cần tìm", Toast.LENGTH_SHORT).show();
                }
                else {
                performSearch(txtSearch.getText().toString());
                }
            }
        });

    }
    private void performSearch(String keyword) {
        SharedPreferences sharedPref = context.getSharedPreferences("my_prefs", Context.MODE_PRIVATE);
        String username = sharedPref.getString("username", null);

        db.readDataCategory(new DB.OnCategoryDataLoadedListener() {
            @Override
            public void onCategoryDataLoaded(ArrayList<CategoryItem> categoryList) {
                ArrayList<String> desiredCategoryId = new ArrayList<>();
                for (CategoryItem categoryItem : categoryList) {
                    String normalName = normalizeString(categoryItem.getName());
                    String normalKeyword = normalizeString(keyword);
                    if (normalName.contains(normalKeyword)) {
                        desiredCategoryId.add(categoryItem.getIdCtg());
                    }
                }

                if (desiredCategoryId.size() != 0) {
                    ArrayList<String> finalDesiredCategoryId = desiredCategoryId;

                    db.readDataTransaction(username, new DB.OnDataLoadedListener() {
                        @Override
                        public void onDataLoaded(ArrayList<Transaction> transactionList) {
                            final int[] sum = new int[3];
                            sum[0] = 0;
                            sum[1] = 0;
                            sum[2] = 0;
                            ArrayList<Transaction> filteredTransactionList = new ArrayList<>();
                            for (Transaction transaction : transactionList) {
                                for (String s : finalDesiredCategoryId)
                                {
                                    if (transaction.getIdCtg().equals(s)) {
                                        filteredTransactionList.add(transaction);
                                        if(transaction.getType().equals("Chi"))
                                        {
                                            sum[0] += transaction.getMoney();
                                        }
                                        else
                                        {
                                            sum[1] += transaction.getMoney();
                                        }
                                    }
                                }
                            }
                            if(sum[0] != 0)
                            {
                                sumChi.setText(String.valueOf(sum[0])+"đ");
                            }
                            else
                            {
                                sumChi.setText("0đ");
                            }
                            if(sum[1] != 0)
                            {
                                sumThu.setText(String.valueOf(sum[1])+"đ");
                            }
                            else
                            {
                                sumThu.setText("0đ");
                            }
                            sum[2] = sum[1]-sum[0];
                            sumAll.setText(String.valueOf(sum[2])+"đ");
                            if(sum[2] > 0 )
                            {
                                sumAll.setTextColor(Color.parseColor("#002BFF"));
                            }
                            else
                            {
                                sumAll.setTextColor(Color.parseColor("#FF0000"));
                            }
                            setUpGridTransaction(filteredTransactionList);
                        }
                        @Override
                        public void onDataError(String errorMessage) {
                        }
                    });
                }
                else
                {
                    sumChi.setText("0đ");
                    sumThu.setText("0đ");
                    sumAll.setText("0đ");
                    setUpGridTransaction(null);
                }
            }

            @Override
            public void onCategoryDataError(String errorMessage) {
            }
        });

    }
    private void setUpGridTransaction(ArrayList<Transaction> transactionArrayList){

        if (transactionArrayList == null || transactionArrayList.size() == 0)
        {
            gvSearch.setAdapter(null);
        }
        else
        {
            ArrayList<Date> dateArrayList;
            dateArrayList = db.listDateTransaction(transactionArrayList);

            Collections.sort(dateArrayList, new Comparator<Date>() {
                @Override
                public int compare(Date date1, Date date2) {
                    return date1.compareTo(date2);
                }
            });

            transactionGridAdapter = new TransactionGridAdapter(context, dateArrayList, transactionArrayList);
            gvSearch.setAdapter(transactionGridAdapter);
        }

    }
    private static String normalizeString(String input) {
        String lowerCase = input.toLowerCase();

        String normalized = Normalizer.normalize(lowerCase, Normalizer.Form.NFD)
                .replaceAll("\\p{M}", "");

        return normalized;
    }
}