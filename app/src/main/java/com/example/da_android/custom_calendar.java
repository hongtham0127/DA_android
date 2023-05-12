package com.example.da_android;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.GridView;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.Nullable;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;

public class custom_calendar extends LinearLayout {
    ImageButton netButton,backButton;
    TextView textView;
    GridView gridView;
    private static final int MAX_CALENDAR = 42;
     Calendar calendar = Calendar.getInstance(Locale.ENGLISH);
     Context context;

     SimpleDateFormat dateFormat = new SimpleDateFormat("MMM yyy",Locale.ENGLISH);
     SimpleDateFormat monthFormat = new SimpleDateFormat("MMM",Locale.ENGLISH);
     SimpleDateFormat yearFormat = new SimpleDateFormat("yyy",Locale.ENGLISH);

     List<Date> dateList = new ArrayList<>();
     List<Events> events =new ArrayList<>();



    public custom_calendar(Context context) {
        super(context);
    }

    public custom_calendar(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        this.context =context;
    }
}
