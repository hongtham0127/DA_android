package com.example.da_android.fragment;

import android.app.DatePickerDialog;
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

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link EditInputFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class EditInputFragment extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;
    private Transaction transaction;

    public EditInputFragment() {
        // Required empty public constructor
    }
    public EditInputFragment(Transaction transaction) {
        this.transaction = transaction;
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment EditInputFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static EditInputFragment newInstance(String param1, String param2) {
        EditInputFragment fragment = new EditInputFragment();
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
    Button btn_lich;
    Button btn_edit, btn_delete;
    GridView gvDanhMuc;
    EditText edit_ghichu, edit_tien;
    Calendar lich = Calendar.getInstance();
    DB db = new DB();
    CategoryItem ctg_selected;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_edit_input, container, false);
        DateFormat dateFormat = new SimpleDateFormat("d/M/yyyy");
        btn_lich = view.findViewById(R.id.btn_thoigian_edit);
        btn_edit = view.findViewById(R.id.btn_edit_input);
        btn_delete = view.findViewById(R.id.btn_delete);
        gvDanhMuc = view.findViewById(R.id.gv_danhmuc_eidt);
        edit_ghichu = view.findViewById(R.id.edit_ghichu);
        edit_tien = view.findViewById(R.id.edit_tien);
        Date date;
        try {
            date = dateFormat.parse(transaction.getTrxDate());
        } catch (ParseException e) {
            throw new RuntimeException(e);
        }
        lich.setTime(date);

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
        });
        edit_ghichu.setText(transaction.getNote());
        edit_tien.setText(String.valueOf(transaction.getMoney()));
        if(transaction.getType().equals("Chi"))
        {
            db.readDataCategoryChi(new DB.OnCategoryDataLoadedListener() {
                @Override
                public void onCategoryDataLoaded(ArrayList<CategoryItem> list) {
                    ItemCategoryAdapter adapter = new ItemCategoryAdapter(getActivity(), R.layout.layout_item_danhmuc, list, "Edit", transaction);
                    gvDanhMuc.setAdapter(adapter);
                    ctg_selected = db.searchCategorybyId(list, transaction.getIdCtg());
                }

                @Override
                public void onCategoryDataError(String errorMessage) {
                }
            });

        }
        else
        {
            db.readDataCategoryThu(new DB.OnCategoryDataLoadedListener() {
                @Override
                public void onCategoryDataLoaded(ArrayList<CategoryItem> list) {
                    ItemCategoryAdapter adapter = new ItemCategoryAdapter(getActivity(), R.layout.layout_item_danhmuc, list, "Edit", transaction);
                    gvDanhMuc.setAdapter(adapter);
                    ctg_selected = db.searchCategorybyId(list, transaction.getIdCtg());
                }

                @Override
                public void onCategoryDataError(String errorMessage) {
                }
            });
        }

        gvDanhMuc.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View v, int i, long l) {
                for (int j = 0; j < adapterView.getChildCount(); j++) {
                    View item = adapterView.getChildAt(j);
                    item.setBackgroundResource(R.drawable.border_normal);
                }

                v.setBackgroundResource(R.drawable.selected_item);
                TextView tv = v.findViewById(R.id.txt_tenDM);
                if(transaction.getType().equals("Chi"))
                {
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
                else
                {
                    db.readDataCategoryThu(new DB.OnCategoryDataLoadedListener() {
                        @Override
                        public void onCategoryDataLoaded(ArrayList<CategoryItem> list) {
                            ctg_selected = db.searchCategorybyName(list, tv.getText().toString());
                        }

                        @Override
                        public void onCategoryDataError(String errorMessage) {
                        }
                    });
                }
            }
        });
        btn_edit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                transaction.setIdCtg(ctg_selected.getIdCtg());
                transaction.setMoney(Integer.valueOf(edit_tien.getText().toString()));
                transaction.setNote(edit_ghichu.getText().toString());
                transaction.setTrxDate(btn_lich.getText().toString());
                db.updateTransaction(transaction);
                Toast.makeText(getActivity(), "Chỉnh sửa thành công", Toast.LENGTH_SHORT).show();
                Toast.makeText(getActivity(), "Vui lòng load lại", Toast.LENGTH_SHORT).show();
                getActivity().finish();
            }
        });

        btn_delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                db.deleteTransaction(transaction);
                Toast.makeText(getActivity(), "Xóa thành công", Toast.LENGTH_SHORT).show();
                Toast.makeText(getActivity(), "Vui lòng load lại", Toast.LENGTH_SHORT).show();
                getActivity().finish();
            }
        });
        return view;
    }
}