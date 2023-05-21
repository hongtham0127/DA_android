package com.example.da_android.fragment;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.GridView;
import android.widget.ImageButton;
import android.widget.TextView;

import androidx.fragment.app.Fragment;

import com.example.da_android.DB;
import com.example.da_android.R;
import com.example.da_android.activity.SearchTransactionActivity;
import com.example.da_android.adapter.CustomGridAdapter;
import com.example.da_android.adapter.TransactionGridAdapter;
import com.example.da_android.model.Transaction;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.List;
import java.util.Locale;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link StatisticalCalendarFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class StatisticalCalendarFragment extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public StatisticalCalendarFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment StatisticalCalendarFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static StatisticalCalendarFragment newInstance(String param1, String param2) {
        StatisticalCalendarFragment fragment = new StatisticalCalendarFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }
    ImageButton nextButton,backButton, searchButton;
    TextView currentDate, rangeDate, sumChi, sumThu, sumAll;
    GridView gv, gv_display_transaction;
    ArrayList<Transaction> transactions;
    private static final int MAX_CALENDAR = 35;
    Calendar calendar = Calendar.getInstance(Locale.ENGLISH);
    CustomGridAdapter customGridAdapter;
    TransactionGridAdapter transactionGridAdapter;

    SimpleDateFormat dateFormat = new SimpleDateFormat("MM/yyyy",Locale.ENGLISH);
    DB db = new DB();

    List<Date> dateList = new ArrayList<>();
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_statistical_calendar, container, false);
        nextButton = view.findViewById(R.id.btn_next);
        backButton = view.findViewById(R.id.btn_back);
        searchButton = view.findViewById(R.id.btn_search);
        currentDate = view.findViewById(R.id.txt_currentDate);
        rangeDate = view.findViewById(R.id.txt_rangeDate);
        sumChi = view.findViewById(R.id.txt_tong_chi);
        sumThu = view.findViewById(R.id.txt_tong_thu);
        sumAll = view.findViewById(R.id.txt_tong_tien);
        gv = view.findViewById(R.id.gv_lich);
        gv_display_transaction = view.findViewById(R.id.gv_display_transaction);
        SharedPreferences sharedPref = getActivity().getSharedPreferences("my_prefs", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPref.edit();
        editor.putString("viewCategory", null);
        setUpCalendar();
        setUpStatisticsBar();
        setUpStation(null);
        backButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                calendar.add(Calendar.MONTH, -1);
                setUpCalendar();
                setUpStatisticsBar();
            }
        });

        nextButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                calendar.add(Calendar.MONTH, 1);
                setUpCalendar();
                setUpStatisticsBar();
            }
        });
        searchButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                setUpStation("search");
                Intent intent = new Intent(getActivity(), SearchTransactionActivity.class);
                startActivity(intent);
            }
        });

        return view;
    }

    private void setUpCalendar() {
        String currDate = dateFormat.format(calendar.getTime());

        dateList.clear();
        Calendar monthCalendar = (Calendar) calendar.clone();
        monthCalendar.set(Calendar.DAY_OF_MONTH, 1);
        int fisrtDayofMonth = monthCalendar.get(Calendar.DAY_OF_WEEK)-1;

        Calendar calendarCrr = (Calendar) calendar.clone();
        calendarCrr.set(calendarCrr.DAY_OF_MONTH, calendar.getActualMaximum(calendarCrr.DAY_OF_MONTH));
        Date lastDayOfMonth = calendarCrr.getTime();
        SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM");
        String dateRange = " (" + dateFormat.format(fisrtDayofMonth) + " - " + dateFormat.format(lastDayOfMonth) + ")";

        currentDate.setText(currDate);
        rangeDate.setText(dateRange);

        monthCalendar.add(Calendar.DAY_OF_MONTH, -fisrtDayofMonth);
        while (dateList.size() < MAX_CALENDAR)
        {
            dateList.add(monthCalendar.getTime());
            monthCalendar.add(Calendar.DAY_OF_MONTH, 1);
        }

        customGridAdapter = new CustomGridAdapter(getContext(), dateList, calendar);
        gv.setAdapter(customGridAdapter);
    }
    private void setUpGridTransaction(ArrayList<Transaction> listTransaction){
        DateFormat dateFormat1 = new SimpleDateFormat("M/yyyy");

        ArrayList<Date> dateArrayList;
        dateArrayList = db.listDateTransaction(listTransaction);
        ArrayList<Date> listFinal = new ArrayList<>();
        try {
            Date DcurrentDate = dateFormat1.parse(currentDate.getText().toString());
            for (Date i : dateArrayList)
            {
                if(i.getMonth() == DcurrentDate.getMonth())
                {
                    listFinal.add(i);
                }
            }
        } catch (ParseException e) {
            throw new RuntimeException(e);
        }
        Collections.sort(listFinal, new Comparator<Date>() {
            @Override
            public int compare(Date date1, Date date2) {
                return date1.compareTo(date2);
            }
        });

        transactionGridAdapter = new TransactionGridAdapter(getContext(), listFinal);
        gv_display_transaction.setAdapter(transactionGridAdapter);

    }
    private void setUpStatisticsBar() {
        SharedPreferences sharedPref = getContext().getSharedPreferences("my_prefs", Context.MODE_PRIVATE);
        String username = sharedPref.getString("username", null);

        db.readDataTransaction(username, new DB.OnDataLoadedListener() {
            @Override
            public void onDataLoaded(ArrayList<Transaction> list) {
                DateFormat dateFormat = new SimpleDateFormat("d/M/yyyy");
                DateFormat dateFormat1 = new SimpleDateFormat("M/yyyy");
                final int[] sum = new int[3];
                sum[0] = 0;
                sum[1] = 0;
                sum[2] = 0;
                transactions = list;
                for(Transaction item : list)
                {
                    Date date;
                    Date date1;
                    try {
                        date = dateFormat.parse(item.getTrxDate());
                        date1 = dateFormat1.parse(currentDate.getText().toString());
                        if(date.getMonth() == date1.getMonth())
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
                setUpGridTransaction(transactions);
            }

            @Override
            public void onDataError(String errorMessage) {
            }
        });

    }
    private void setUpStation(String s)
    {
        SharedPreferences sharedPref = getContext().getSharedPreferences("my_prefs", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPref.edit();
        editor.putString("station", s);
        editor.apply();
    }

}