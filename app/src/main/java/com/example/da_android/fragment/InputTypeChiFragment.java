package com.example.da_android.fragment;

import android.annotation.SuppressLint;
import android.app.DatePickerDialog;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.GridView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.fragment.app.Fragment;

import com.example.da_android.DB;
import com.example.da_android.R;
import com.example.da_android.adapter.ItemCategoryAdapter;
import com.example.da_android.model.CategoryItem;
import com.example.da_android.model.Transaction;

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
    Button btn_input_chi;
    GridView gvDanhMuc;
    EditText edit_ghichu, edit_tienchi;
    Calendar lich = Calendar.getInstance();
    DB db = new DB();
    CategoryItem ctg_selected;
    @SuppressLint("MissingInflatedId")
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_input_type_chi, container, false);

        btn_lich = view.findViewById(R.id.btn_thoigian_chi);
        btn_input_chi = view.findViewById(R.id.btn_input_chi);
        gvDanhMuc = view.findViewById(R.id.gv_danhmuc_chi);
        edit_ghichu = view.findViewById(R.id.edit_ghichu_chi);
        edit_tienchi = view.findViewById(R.id.edit_tienchi);

        btn_lich.setText(lich.get(Calendar.DAY_OF_MONTH) + "/" +(lich.get(Calendar.MONTH)+1) + "/" +lich.get(Calendar.YEAR));

        btn_lich.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DatePickerDialog dialog = new DatePickerDialog(view.getContext(),
                        new DatePickerDialog.OnDateSetListener() {
                            @Override
                            public void onDateSet(DatePicker datePicker, int year, int month, int day) {
                                Toast.makeText(getActivity(),day +"/"+(month+1)+"/"+year,Toast.LENGTH_SHORT).show();
                                btn_lich.setText(day + "/" +(month+1) + "/" +year);
                            }
                        },
                        lich.get(Calendar.YEAR),
                        lich.get(Calendar.MONTH),
                        lich.get(Calendar.DAY_OF_MONTH)
                );
                dialog.show();;
            }
        }
        );
        db.readDataCategoryChi(new DB.OnCategoryDataLoadedListener() {
            @Override
            public void onCategoryDataLoaded(ArrayList<CategoryItem> list) {
                ItemCategoryAdapter adapter = new ItemCategoryAdapter(getActivity(), R.layout.layout_item_danhmuc, list, "Chi", null);
                gvDanhMuc.setAdapter(adapter);
            }

            @Override
            public void onCategoryDataError(String errorMessage) {
            }
        });

        gvDanhMuc.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View v, int i, long l) {
                for (int j = 0; j < adapterView.getChildCount(); j++) {
                    View item = adapterView.getChildAt(j);
                    item.setBackgroundResource(R.drawable.border_normal);
                }

                v.setBackgroundResource(R.drawable.selected_item);
                TextView tv = v.findViewById(R.id.txt_tenDM);
                db.readDataCategoryChi(new DB.OnCategoryDataLoadedListener() {
                    @Override
                    public void onCategoryDataLoaded(ArrayList<CategoryItem> list) {
                        ctg_selected = db.searchCategorybyName(list, tv.getText().toString());
                    }

                    @Override
                    public void onCategoryDataError(String errorMessage) {
                    }
                });
            }
        });

        btn_input_chi.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String idTrx = "TrxCHI"+db.getIdTrx();

                final String idCtg = ctg_selected.getIdCtg();
                final String ghiChu = edit_ghichu.getText().toString();
                final int tienChi = Integer.parseInt(edit_tienchi.getText().toString());
                SharedPreferences sharedPref = getActivity().getSharedPreferences("my_prefs", Context.MODE_PRIVATE);
                final String username = sharedPref.getString("username", null);
                String trxDate = btn_lich.getText().toString();

                Transaction trxDetail = new Transaction(
                        idTrx, ghiChu, username, tienChi, ctg_selected.getType(), idCtg, trxDate);
                if(tienChi != 0)
                {
                    db.addTransaction(trxDetail);
                    edit_ghichu.setText("");
                    edit_tienchi.setText("");
                    Toast.makeText(getActivity(),"THÀNH CÔNG",Toast.LENGTH_SHORT).show();
                }
                else
                {
                    Toast.makeText(getActivity(),"Vui lòng nhập số tiền",Toast.LENGTH_SHORT).show();
                }
            }
        });
        return view;
    }

}