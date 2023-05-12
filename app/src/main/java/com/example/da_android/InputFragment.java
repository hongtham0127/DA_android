package com.example.da_android;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.google.android.material.bottomnavigation.BottomNavigationView;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link InputFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class InputFragment extends Fragment{

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public InputFragment() {
        // Required empty public constructor

    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment InputFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static InputFragment newInstance(String param1, String param2) {
        InputFragment fragment = new InputFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    BottomNavigationView bottomNavigationView;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }
    int selectedTab = 1;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_input, container, false);
//        bottomNavigationView = view.findViewById(R.id.top_nav_type);
//        bottomNavigationView.setOnNavigationItemSelectedListener(this);
//        bottomNavigationView.setSelectedItemId(R.id.btn_type_chi);
        final LinearLayout typeChiLayout = view.findViewById(R.id.type_chi_layout);
        final LinearLayout typeThuLayout = view.findViewById(R.id.type_thu_layout);

        final TextView txtChi = view.findViewById(R.id.txt_type_chi);
        final TextView txtThu = view.findViewById(R.id.txt_type_thu);
        if(selectedTab == 1)
        {
            getActivity()
                    .getSupportFragmentManager()
                    .beginTransaction()
                    .setReorderingAllowed(true)
                    .replace(R.id.frame_container_input, inputTypeChiFragment)
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
                    .replace(R.id.frame_container_input, inputTypeThuFragment)
                    .commit();

            typeThuLayout.setBackgroundResource(R.drawable.select_type_round);
            txtThu.setTextColor(getResources().getColor(android.R.color.white));
        }

        typeChiLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(selectedTab != 1)
                {
                    getActivity()
                            .getSupportFragmentManager()
                            .beginTransaction()
                            .setReorderingAllowed(true)
                            .replace(R.id.frame_container_input, inputTypeChiFragment)
                            .commit();

                    typeChiLayout.setBackgroundResource(R.drawable.select_type_round);
                    txtChi.setTextColor(getResources().getColor(android.R.color.white));

                    typeThuLayout.setBackgroundResource(R.drawable.header_type);
                    txtThu.setTextColor(getResources().getColor(android.R.color.holo_orange_dark));
                    selectedTab = 1;
                }
            }
        });

        typeThuLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(selectedTab != 2)
                {
                    getActivity()
                            .getSupportFragmentManager()
                            .beginTransaction()
                            .setReorderingAllowed(true)
                            .replace(R.id.frame_container_input, inputTypeThuFragment)
                            .commit();

                    typeChiLayout.setBackgroundResource(R.drawable.header_type);
                    txtChi.setTextColor(getResources().getColor(android.R.color.holo_orange_dark));

                    typeThuLayout.setBackgroundResource(R.drawable.select_type_round);
                    txtThu.setTextColor(getResources().getColor(android.R.color.white));
                    selectedTab = 2;
                }
            }
        });
        return view;
    }

    InputTypeChiFragment inputTypeChiFragment = new InputTypeChiFragment();
    InputTypeThuFragment inputTypeThuFragment = new InputTypeThuFragment();

//    @Override
//    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
//        switch (item.getItemId())
//        {
//            case R.id.btn_type_chi:
//                getActivity()
//                        .getSupportFragmentManager()
//                        .beginTransaction()
//                        .replace(R.id.frame_container_input, inputTypeChiFragment)
//                        .commit();
//                return true;
//            case R.id.btn_type_thu:
//                getActivity()
//                        .getSupportFragmentManager()
//                        .beginTransaction()
//                        .replace(R.id.frame_container_input, inputTypeThuFragment)
//                        .commit();
//                return true;
//        }
//        return false;
//    }

}