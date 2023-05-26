package com.example.da_android.adapter;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentStatePagerAdapter;

import com.example.da_android.fragment.OtherFragment;
import com.example.da_android.fragment.InputFragment;
import com.example.da_android.fragment.ReportFragment;
import com.example.da_android.fragment.StatisticalCalendarFragment;

public class ViewPagerAdapter extends FragmentStatePagerAdapter {
    public ViewPagerAdapter(@NonNull FragmentManager fm, int behavior) {
        super(fm, behavior);
    }

    @NonNull
    @Override
    public Fragment getItem(int position) {
        switch (position) {
            case 0:
                return new InputFragment();
            case 1:
                return new StatisticalCalendarFragment();
            case 2:
                return new ReportFragment();
            case 3:
                return new OtherFragment();
            default:
                return new InputFragment();
        }
    }
    @Override
    public int getCount() {
        return 4;
    }
}
