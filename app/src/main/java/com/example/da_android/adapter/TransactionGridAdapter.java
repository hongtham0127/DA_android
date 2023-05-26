package com.example.da_android.adapter;

import android.content.Context;
import android.content.SharedPreferences;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.GridView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.example.da_android.DB;
import com.example.da_android.R;
import com.example.da_android.model.Transaction;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;

public class TransactionGridAdapter extends ArrayAdapter {
    Context context;
    LayoutInflater inflater;
    DB db = new DB();
    List<Date> dateList;
    ArrayList<Transaction> transactionArrayList;
    public TransactionGridAdapter(@NonNull Context context, List<Date> dateList) {
        super(context, R.layout.fragment_statistical_calendar);
        this.context = context;
        inflater = LayoutInflater.from(context);
        this.dateList = dateList;
        this.transactionArrayList = null;
    }
    public TransactionGridAdapter(@NonNull Context context, List<Date> dateList, ArrayList<Transaction> transactionArrayList) {
        super(context, R.layout.fragment_statistical_calendar);
        this.context = context;
        inflater = LayoutInflater.from(context);
        this.dateList = dateList;
        this.transactionArrayList = transactionArrayList;
    }

    @Override
    public int getCount() {
        return dateList.size();
    }

    @Override
    public int getPosition(@Nullable Object item) {
        return dateList.indexOf(item);
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        SimpleDateFormat dateFormat = new SimpleDateFormat("d/M/yyyy", Locale.ENGLISH);
        Date date = dateList.get(position);
        View view = convertView;
        if (view == null)
        {
            view = inflater.inflate(R.layout.fragment_transaction_detail, parent, false);
        }
        SharedPreferences sharedPref = getContext().getSharedPreferences("my_prefs", Context.MODE_PRIVATE);
        String username = sharedPref.getString("username", null);
        SharedPreferences.Editor editor = sharedPref.edit();
        editor.putString("viewCategory", null);
        editor.apply();

        TextView dateDetail = view.findViewById(R.id.txt_date_detail);
        TextView sumDetail = view.findViewById(R.id.txt_sum_detail);
        GridView gvDetailItem = view.findViewById(R.id.gv_detail_item);
        dateDetail.setText(dateFormat.format(date.getTime()));

        View finalView = view;
        if(transactionArrayList == null)
        {
            db.readDataTransaction(username, new DB.OnDataLoadedListener() {
                @Override
                public void onDataLoaded(ArrayList<Transaction> list) {
                    ArrayList<Transaction> arrayTrxItem = new ArrayList<>();
                    int sumThu = 0;
                    int sumChi = 0;
                    for (Transaction item : list) {
                        Date displayDate;
                        try {
                            displayDate = dateFormat.parse(item.getTrxDate());
                            if (date.compareTo(displayDate) == 0) {
                                arrayTrxItem.add(item);
                                if (item.getType().equals("Thu")) {
                                    sumThu += item.getMoney();
                                } else {
                                    sumChi += item.getMoney();
                                }
                            }
                        } catch (ParseException e) {
                            throw new RuntimeException(e);
                        }
                    }

                    int sum = sumThu - sumChi;
                    sumDetail.setText(String.valueOf(sum) + "đ");
                    ViewGroup.LayoutParams layoutParams = gvDetailItem.getLayoutParams();
                    layoutParams.height = 150 * arrayTrxItem.size();
                    gvDetailItem.setLayoutParams(layoutParams);
                    ItemDetailAdapter adapter = new ItemDetailAdapter(finalView.getContext(), R.layout.layout_item_detail, arrayTrxItem);
                    gvDetailItem.setAdapter(adapter);
                }

                @Override
                public void onDataError(String errorMessage) {
                }
            });
        }
        else
        {
            int sumThu = 0;
            int sumChi = 0;
            ArrayList<Transaction> arrayTrxItem = new ArrayList<>();
            for (Transaction item : transactionArrayList) {
                Date displayDate;
                try {
                    displayDate = dateFormat.parse(item.getTrxDate());
                    if (date.compareTo(displayDate) == 0) {
                        arrayTrxItem.add(item);
                        if (item.getType().equals("Thu")) {
                            sumThu += item.getMoney();
                        } else {
                            sumChi += item.getMoney();
                        }
                    }
                } catch (ParseException e) {
                    throw new RuntimeException(e);
                }
            }

            int sum = sumThu - sumChi;
            sumDetail.setText(String.valueOf(sum) + "đ");
            ViewGroup.LayoutParams layoutParams = gvDetailItem.getLayoutParams();
            layoutParams.height = 150 * arrayTrxItem.size();
            gvDetailItem.setLayoutParams(layoutParams);
            ItemDetailAdapter adapter = new ItemDetailAdapter(finalView.getContext(), R.layout.layout_item_detail, arrayTrxItem);
            gvDetailItem.setAdapter(adapter);
        }
        return view;
    }
}
