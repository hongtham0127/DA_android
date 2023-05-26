package com.example.da_android.fragment;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import androidx.fragment.app.Fragment;

import com.example.da_android.DB;
import com.example.da_android.Login;
import com.example.da_android.R;
import com.example.da_android.activity.AllTimeReportActivity;
import com.example.da_android.activity.AnnualReportActivity;
import com.example.da_android.activity.CategoryAllTimeReportActivity;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link OtherFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class OtherFragment extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public OtherFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment OtherFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static OtherFragment newInstance(String param1, String param2) {
        OtherFragment fragment = new OtherFragment();
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
    Button btnAnnualReport, btnAllTimeAnnualReport, btnCategoryAllTime, btnClear, btnLogout;
    DB db = new DB();
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_other, container, false);
        btnAnnualReport = view.findViewById(R.id.btn_annual_report);
        btnAllTimeAnnualReport = view.findViewById(R.id.btn_all_time_annual_report);
        btnCategoryAllTime = view.findViewById(R.id.btn_category_all_time_annual_report);
        btnClear = view.findViewById(R.id.btn_clear);
        btnLogout = view.findViewById(R.id.btn_logout);
        btnAnnualReport.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), AnnualReportActivity.class);
                getActivity().startActivity(intent);
            }
        });

        btnAllTimeAnnualReport.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), AllTimeReportActivity.class);
                getActivity().startActivity(intent);
            }
        });
        btnCategoryAllTime.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), CategoryAllTimeReportActivity.class);
                getActivity().startActivity(intent);
            }
        });
        btnLogout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SharedPreferences sharedPref = getActivity().getSharedPreferences("my_prefs", Context.MODE_PRIVATE);
                SharedPreferences.Editor editor = sharedPref.edit();
                editor.putString("username", null);
                editor.apply();
                Intent intent = new Intent(getActivity(), Login.class);
                getActivity().startActivity(intent);
                getActivity().finish();
            }
        });
        btnClear.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                db.deleteAllTransaction();
            }
        });
        return view;
    }
}