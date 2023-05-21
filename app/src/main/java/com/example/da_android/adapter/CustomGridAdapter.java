package com.example.da_android.adapter;

import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.graphics.Typeface;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.example.da_android.DB;
import com.example.da_android.R;
import com.example.da_android.model.Transaction;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

public class CustomGridAdapter extends ArrayAdapter {
    List<Date> dateList;
    Calendar currentDate;

    Context context;
    LayoutInflater inflater;
    DB db = new DB();

    public CustomGridAdapter(@NonNull Context context, List<Date> dateList, Calendar currentDate) {
        super(context, R.layout.fragment_statistical_calendar);
        this.context = context;
        this.dateList = dateList;
        this.currentDate = currentDate;
        inflater = LayoutInflater.from(context);
    }

    @Override
    public int getCount() {
        return dateList.size();
    }

    @Override
    public int getPosition(@Nullable Object item) {
        return dateList.indexOf(item);
    }

    @Nullable
    @Override
    public Object getItem(int position) {
        return dateList.get(position);
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        Date monthDate = dateList.get(position);
        Calendar dateCalendar = Calendar.getInstance();
        dateCalendar.setTime(monthDate);
        int dayNo = dateCalendar.get(Calendar.DAY_OF_MONTH);
        int displayMonth = dateCalendar.get(Calendar.MONTH)+1;
        int displayYear = dateCalendar.get(Calendar.YEAR);

        int currentMonth = currentDate.get(Calendar.MONTH)+1;
        int currentYear = currentDate.get(Calendar.YEAR);
        View view = convertView;
        if (view == null)
        {
            view = inflater.inflate(R.layout.single_cell_calendar_layout, parent, false);
        }

        if(displayMonth == currentMonth && displayYear == currentYear)
        {
            view.setBackgroundColor(getContext().getResources().getColor(R.color.white));
        }
        else
        {
            view.setBackgroundColor(Color.parseColor("#E8E8E8"));
        }
        Calendar now = Calendar.getInstance();
        int year_now = now.get(Calendar.YEAR);
        int month_now = now.get(Calendar.MONTH) + 1;
        int day_now = now.get(Calendar.DAY_OF_MONTH);


        TextView dayNumber = view.findViewById(R.id.txt_calendar_day);
        TextView sumChi = view.findViewById(R.id.txt_calendar_tienchi);
        TextView sumThu = view.findViewById(R.id.txt_calendar_tienthu);
        dayNumber.setText(String.valueOf(dayNo));
        if(displayMonth == month_now && displayYear == year_now && dayNo == day_now)
        {
            view.setBackgroundColor(Color.parseColor("#FFF9C7"));
            dayNumber.setTypeface(Typeface.defaultFromStyle(Typeface.BOLD));
        }
        SharedPreferences sharedPref = getContext().getSharedPreferences("my_prefs", Context.MODE_PRIVATE);
        String username = sharedPref.getString("username", null);
        db.readDataTransaction(username, new DB.OnDataLoadedListener() {
            @Override
            public void onDataLoaded(ArrayList<Transaction> list) {
                DateFormat dateFormat = new SimpleDateFormat("d/M/yyyy");
                final int[] sum = new int[2];
                sum[0] = 0;
                sum[1] = 0;
                for(Transaction item : list)
                {
                    Date date;
                    try {
                        date = dateFormat.parse(item.getTrxDate());
                        int d = date.getDate();
                        int m = date.getMonth() + 1;
                        int y = date.getYear() + 1900;
                        if(dayNo == d && displayMonth == m && displayYear == y)
                        {
                            if(item.getType().equals("Chi"))
                            {
                                sum[0] += item.getMoney();
                            }
                            else
                            {
                                sum[1] += item.getMoney();
                            }
                        }
                    } catch (ParseException e) {
                        throw new RuntimeException(e);
                    }
                }
                if(sum[0] != 0)
                {
                    sumChi.setText(String.valueOf(sum[0]));
                }
                else
                {
                    sumChi.setText("");
                }
                if(sum[1] != 0)
                {
                    sumThu.setText(String.valueOf(sum[1]));
                }
                else
                {
                    sumThu.setText("");
                }
            }

            @Override
            public void onDataError(String errorMessage) {

            }
        });

        return view;
    }
}
