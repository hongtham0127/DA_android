package com.example.da_android.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.example.da_android.R;

public class AnnualReportAdapter extends BaseAdapter {
    private Context context;
    private int layout;
    private int[] moneyList;

    public AnnualReportAdapter(Context context, int layout, int[] moneyList) {
        this.context = context;
        this.layout = layout;
        this.moneyList = moneyList;
    }

    @Override
    public int getCount() {
        return moneyList.length;
    }

    @Override
    public Object getItem(int position) {
        return null;
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }
    private class viewholder{
        TextView txtMonth;
        TextView txtMoney;
    }
    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        viewholder holder;
        if(view == null)
        {
            holder = new viewholder();
            LayoutInflater inflater  = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            view = inflater.inflate(layout,null);
            holder.txtMonth = view.findViewById(R.id.txt_annual_report_thang);
            holder.txtMoney = view.findViewById(R.id.txt_annual_report_tien);
            view.setTag(holder);
        }
        else
        {
            holder = (AnnualReportAdapter.viewholder) view.getTag();
        }
        holder.txtMonth.setText("Tháng "+String.valueOf(i + 1));
        holder.txtMoney.setText(String.valueOf(moneyList[i])+"đ");
        return view;
    }
}
