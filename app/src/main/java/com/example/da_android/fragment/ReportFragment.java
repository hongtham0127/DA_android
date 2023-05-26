package com.example.da_android.fragment;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.fragment.app.Fragment;

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
import java.util.Locale;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link ReportFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class ReportFragment extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public ReportFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment ReportFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static ReportFragment newInstance(String param1, String param2) {
        ReportFragment fragment = new ReportFragment();
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
    int selectedTab = 1;
    LinearLayout typeChiLayout, typeThuLayout;
    TextView currentDate, rangeDate, sumChi, sumThu, sumAll;
    Calendar calendar = Calendar.getInstance(Locale.ENGLISH);
    SimpleDateFormat dateFormat = new SimpleDateFormat("MM/yyyy", Locale.ENGLISH);
    ImageButton nextButton,backButton;
    TextView txtChi,txtThu;
    List<Date> dateList = new ArrayList<>();
    DB db = new DB();
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_report, container, false);
        typeChiLayout = view.findViewById(R.id.type_report_chi_layout);
        typeThuLayout = view.findViewById(R.id.type_report_thu_layout);

        txtChi = view.findViewById(R.id.txt_report_type_chi);
        txtThu = view.findViewById(R.id.txt_report_type_thu);
        nextButton = view.findViewById(R.id.btn_report_date_next);
        backButton = view.findViewById(R.id.btn_report_date_back);
        currentDate = view.findViewById(R.id.txt_report_currentDate);
        rangeDate = view.findViewById(R.id.txt_report_rangeDate);
        sumChi = view.findViewById(R.id.txt_report_tien_chi);
        sumThu = view.findViewById(R.id.txt_report_tien_thu);
        sumAll = view.findViewById(R.id.txt_report_tien_tong);

        setUpCalendar();
        setUpStatisticsBar();
        setUpReport();
        setUpFrame();

        typeChiLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                setUpReport();
                if(selectedTab != 1)
                {
                    getActivity()
                            .getSupportFragmentManager()
                            .beginTransaction()
                            .setReorderingAllowed(true)
                            .replace(R.id.frame_container_report, new ReportChartFragment("Chi", dateFormat.format(calendar.getTime())))
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
                setUpReport();
                if(selectedTab != 2)
                {
                    getActivity()
                            .getSupportFragmentManager()
                            .beginTransaction()
                            .setReorderingAllowed(true)
                            .replace(R.id.frame_container_report, new ReportChartFragment("Thu", dateFormat.format(calendar.getTime())))
                            .commit();

                    typeChiLayout.setBackgroundResource(R.drawable.header_report_type);
                    txtChi.setTextColor(getResources().getColor(R.color.holo_orange_dark));

                    typeThuLayout.setBackgroundResource(R.drawable.select_type_round);
                    txtThu.setTextColor(getResources().getColor(android.R.color.white));
                    selectedTab = 2;
                }
            }
        });
        backButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                calendar.add(Calendar.MONTH, -1);
                setUpCalendar();
                setUpStatisticsBar();
                setUpFrame();
            }
        });

        nextButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                calendar.add(Calendar.MONTH, 1);
                setUpCalendar();
                setUpStatisticsBar();
                setUpFrame();
            }
        });

        return view;
    }
    private void setUpFrame()
    {
        if(selectedTab == 1)
        {
            getActivity()
                    .getSupportFragmentManager()
                    .beginTransaction()
                    .setReorderingAllowed(true)
                    .replace(R.id.frame_container_report, new ReportChartFragment("Chi", dateFormat.format(calendar.getTime())))
                    .commit();

            typeChiLayout.setBackgroundResource(R.drawable.select_type_round);
            txtChi.setTextColor(getResources().getColor(android.R.color.white));
        }
        if(selectedTab == 2)
        {
            getActivity()
                    .getSupportFragmentManager()
                    .beginTransaction()
                    .setReorderingAllowed(true)
                    .replace(R.id.frame_container_report, new ReportChartFragment("Thu", dateFormat.format(calendar.getTime())))
                    .commit();

            typeThuLayout.setBackgroundResource(R.drawable.select_type_round);
            txtThu.setTextColor(getResources().getColor(android.R.color.white));
        }
    }
    private void setUpCalendar() {
        String currDate = dateFormat.format(calendar.getTime());

        dateList.clear();
        Calendar monthCalendar = (Calendar) calendar.clone();
        monthCalendar.set(Calendar.DAY_OF_MONTH, 1);

        Calendar calendarCrr = (Calendar) calendar.clone();
        calendarCrr.set(calendarCrr.DAY_OF_MONTH, calendar.getActualMaximum(calendarCrr.DAY_OF_MONTH));
        Date lastDayOfMonth = calendarCrr.getTime();
        SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM");
        String dateRange = " (01/" + lastDayOfMonth.getMonth() + " - " + dateFormat.format(lastDayOfMonth) + ")";

        currentDate.setText(currDate);
        rangeDate.setText(dateRange);
    }
    private void setUpStatisticsBar() {
        SharedPreferences sharedPref = getActivity().getSharedPreferences("my_prefs", Context.MODE_PRIVATE);
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
            }

            @Override
            public void onDataError(String errorMessage) {
            }
        });

    }
    private void setUpReport()
    {
        SharedPreferences sharedPref = getActivity().getSharedPreferences("my_prefs", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPref.edit();
        editor.putString("viewCategory", "report");
        editor.apply();
    }
}