package com.example.da_android.fragment;

import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.GridView;
import android.widget.TextView;

import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;

import com.example.da_android.DB;
import com.example.da_android.R;
import com.example.da_android.adapter.ItemDetailAdapter;
import com.example.da_android.model.CategoryItem;
import com.example.da_android.model.Transaction;
import com.github.mikephil.charting.charts.PieChart;
import com.github.mikephil.charting.data.PieData;
import com.github.mikephil.charting.data.PieDataSet;
import com.github.mikephil.charting.data.PieEntry;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link ReportChartFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class ReportChartFragment extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;
    private String type;
    private String currentDate;

    public ReportChartFragment() {
        // Required empty public constructor
    }
    public ReportChartFragment(String type, String currentDate) {
        this.type = type;
        this.currentDate = currentDate;
    }
    public ReportChartFragment(String type) {
        this.type = type;
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment ReportChiFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static ReportChartFragment newInstance(String param1, String param2) {
        ReportChartFragment fragment = new ReportChartFragment();
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
    DB db = new DB();
    GridView gvReport;
    TextView txtMess;
    ArrayList<Transaction> transactions;
    ArrayList<Transaction> transactionByCategory;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_report_chart, container, false);
        gvReport = view.findViewById(R.id.gv_report_transaction);
        txtMess = view.findViewById(R.id.txt_mess);
        SharedPreferences sharedPref = getActivity().getSharedPreferences("my_prefs", Context.MODE_PRIVATE);
        String username = sharedPref.getString("username", null);
        PieChart pieChart = view.findViewById(R.id.pieChart);

        db.readDataTransaction(username, new DB.OnDataLoadedListener() {
            @Override
            public void onDataLoaded(ArrayList<Transaction> list) {
                ArrayList<PieEntry> visitors = new ArrayList<>();
                ArrayList<Integer> colors = new ArrayList<>();
                DateFormat dateFormat = new SimpleDateFormat("d/M/yyyy");
                DateFormat dateFormat1 = new SimpleDateFormat("M/yyyy");

                transactions = new ArrayList<>();
                transactionByCategory = new ArrayList<>();
                if(currentDate != null) {
                    for (Transaction item : list) {
                        Date date;
                        Date date1;
                        try {
                            date = dateFormat.parse(item.getTrxDate());
                            date1 = dateFormat1.parse(currentDate);
                            if (date.getMonth() == date1.getMonth()) {
                                if (item.getType().equals(type)) {
                                    transactions.add(item);
                                }
                            }
                        } catch (ParseException e) {
                            throw new RuntimeException(e);
                        }
                    }
                }
                else
                {
                    for (Transaction item : list) {
                        if (item.getType().equals(type)) {
                            transactions.add(item);
                        }
                    }
                }

                for(Transaction transaction : transactions)
                {
                    if (transactionByCategory.size() == 0)
                    {
                        transactionByCategory.add(new Transaction(transaction.getType(), transaction.getIdCtg(), transaction.getMoney(), transaction.getTrxDate()));
                    }
                    else
                    {
                        boolean flag = false;
                        for (Transaction transaction1 : transactionByCategory)
                        {
                            if (transaction.getIdCtg().equals(transaction1.getIdCtg()))
                            {
                                int money = transaction1.getMoney();
                                transaction1.setMoney(money + transaction.getMoney());
                                flag = true;
                                break;
                            }
                        }
                        if (!flag)
                        {
                            transactionByCategory.add(new Transaction(transaction.getType(), transaction.getIdCtg(), transaction.getMoney(), transaction.getTrxDate()));
                        }
                    }
                }
                if(transactionByCategory.size() == 0)
                {
                    txtMess.setText("Không có dữ liệu");
                    gvReport.setAdapter(null);
                    pieChart.setData(null);
                    pieChart.invalidate();
                    pieChart.setVisibility(View.INVISIBLE);
                }
                else {
                    txtMess.setText("");
                    setUpGridTransaction(transactionByCategory);
                    pieChart.setVisibility(View.VISIBLE);
                    db.readDataCategory(new DB.OnCategoryDataLoadedListener() {
                        @Override
                        public void onCategoryDataLoaded(ArrayList<CategoryItem> list) {
                            for (Transaction transaction : transactionByCategory)
                            {
                                for (CategoryItem categoryItem : list)
                                {
                                    if(transaction.getIdCtg().equals(categoryItem.getIdCtg()))
                                    {
                                        visitors.add(new PieEntry(transaction.getMoney(),categoryItem.getName()));
                                        int color = ContextCompat.getColor(getContext(), categoryItem.getColor());
                                        colors.add(color);
                                    }
                                }
                            }
                            PieDataSet pieDataSet = new PieDataSet(visitors,type);
                            pieDataSet.setColors(colors);
                            pieDataSet.setValueTextColor(Color.WHITE);
                            pieDataSet.setValueTextSize(15f);

                            PieData pieData = new PieData(pieDataSet);

                            pieChart.setData(pieData);
                            pieChart.animate();
                            pieChart.invalidate();
                        }

                        @Override
                        public void onCategoryDataError(String errorMessage) {

                        }
                    });
                }

            }

            @Override
            public void onDataError(String errorMessage) {
            }
        });

        return view;
    }
    private void setUpGridTransaction(ArrayList<Transaction> listTransaction){
        ItemDetailAdapter adapter = new ItemDetailAdapter(getActivity(), R.layout.layout_item_detail, listTransaction);
        gvReport.setAdapter(adapter);
    }
}