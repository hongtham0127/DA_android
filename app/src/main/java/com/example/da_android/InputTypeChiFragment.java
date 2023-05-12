package com.example.da_android;

import android.app.DatePickerDialog;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.GridView;
import android.widget.ListAdapter;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Calendar;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link InputTypeChiFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class InputTypeChiFragment extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public InputTypeChiFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment InputTypeChiFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static InputTypeChiFragment newInstance() {
        InputTypeChiFragment fragment = new InputTypeChiFragment();
        Bundle args = new Bundle();
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
    Button btn_lich;
    GridView gvDanhMuc;
    Calendar lich = Calendar.getInstance();

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_input_type_chi, container, false);
        btn_lich = view.findViewById(R.id.btn_thoigian);
        btn_lich.setText(lich.get(Calendar.DAY_OF_MONTH) + "/" +(lich.get(Calendar.MONTH)+1) + "/" +lich.get(Calendar.YEAR));
        btn_lich.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DatePickerDialog dialog = new DatePickerDialog(view.getContext(),
                        new DatePickerDialog.OnDateSetListener() {
                            @Override
                            public void onDateSet(DatePicker datePicker, int year, int month, int day) {
                                Toast.makeText(getActivity(),day +"___"+month+"___"+year,Toast.LENGTH_SHORT).show();
                            }
                        },
                        lich.get(Calendar.YEAR),
                        lich.get(Calendar.MONTH),
                        lich.get(Calendar.DAY_OF_MONTH)
                );
                dialog.show();;
            }
        });

        return view;
    }
}